package com.example.dao.impl;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.dao.BaseDao;
import com.example.utils.Direction;
import com.example.utils.Page;
import com.example.utils.ReflectionUtil;


/**
 * 持久层基类，封装通用方法
 * @author anonymous
 * @param <T>
 * @param <PK>
 */
public abstract class BaseDaoImpl<T, PK extends Serializable> implements BaseDao<T, PK> {
	
	protected Class<T> clazz = null;
	{
		clazz = ReflectionUtil.getClassGenricType(this.getClass());
	}
	
	@Autowired
	protected SessionFactory sessionFactory;
	
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	/**
	 * QBC查询条件的添加
	 * @param criterion
	 * @return
	 */
	protected Criteria getCriteria(Criterion ... criterions) {
		Criteria criteria = getSession().createCriteria(clazz);
		if(criterions!=null && criterions.length>0) {
			for(Criterion criterion : criterions) {
				criteria.add(criterion);
			}
		}
		return criteria;
	}
	
	
	@Override
	public void save(T t) {
		getSession().save(t);
	}

	
	@Override
	public void saveOrUpdate(T t) {
		this.getSession().saveOrUpdate(t);
	}
	
	
	@Override
	public void delete(T t) {
		this.getSession().delete(t);
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public void delete(PK id) {
		T t = (T) this.getSession().get(clazz, id);
		if(t!=null) {
			this.getSession().delete(t);
		}
	}
	
	@Override
	public void update(T t) {
		this.getSession().merge(t);;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public T getById(PK id) {
		return (T) this.getSession().get(clazz, id);
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll() {
		String entityName = clazz.getSimpleName();
		return this.getSession().createQuery("from "+entityName).setCacheable(true).list();
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public T getByProperty(String propertyName, Object propertyValue) {
		Criterion criterion = Restrictions.eq(propertyName, propertyValue);
		return (T) this.getCriteria(criterion).uniqueResult();
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getsByProperty(String propertyName, Object propertyValue) {
		Criterion criterion = Restrictions.eq(propertyName, propertyValue);
		return this.getCriteria(criterion).list();
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> getsByProperty(String propertyName, Object propertyValue,
			LinkedHashMap<String, Direction> orders) {
		Criterion criterion = Restrictions.eq(propertyName, propertyValue);
		Criteria criteria = this.getCriteria(criterion);
		if(orders!=null && orders.size()>0) {
			for(String key : orders.keySet()) {
				if (orders.get(key).toString().equals("ASC")) {
					criteria.addOrder(Order.asc(key));
				} else {
					criteria.addOrder(Order.desc(key));
				}
			}
		}
		return criteria.list();
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public Page<T> getPage(Integer page, Integer pageSize,
			LinkedHashMap<String, Direction> orders, String propertyName, Object propertyValue) {
		
		String className = clazz.getSimpleName();
		Criteria criteria = getSession().createCriteria(clazz);
		String sql = "select count(o) from "+className +" o ";
		if(propertyName!=null && propertyValue!=null) {
			Criterion criterion = Restrictions.eq(propertyName, propertyValue);
			criteria.add(criterion);
			sql += "where "+propertyName+"=:value";
		}
		if(orders!=null && orders.size()>0) {
			for(String key : orders.keySet()) {
				if (orders.get(key).toString().equals("ASC")) {
					criteria.addOrder(Order.asc(key));
				} else {
					criteria.addOrder(Order.desc(key));
				}
			}
		}
		int index = (page-1)*pageSize;
		List<T> content = criteria.setFirstResult(index).setMaxResults(pageSize).list();
		
		Query query = getSession().createQuery(sql);
		if(propertyName!=null && propertyValue!=null) {
			query.setParameter("value", propertyValue);
		}
		Long totalNumber = (Long) query.uniqueResult();
		
		return new Page<T>(content, page, totalNumber, pageSize);
	}
	
	
	@Override
	public Page<T> getPage(Integer page, Integer pageSize) {
		return getPage(page, pageSize, null, null, null);
	}
	

	@Override
	public int updatePropertyById(PK id, String propertyName,
			Object propertyValue) {
		String className = clazz.getSimpleName();
		String sql = "update "+className +" set "+propertyName+"=:value "+" where id=:id";
		Query query = this.getSession().createQuery(sql);
		query.setParameter("value", propertyValue);
		query.setParameter("id", id);
		
		return query.executeUpdate();
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public <E> E getPropertyValueById(PK id, String propertyName) {
		String className = clazz.getSimpleName();
		String sql = "select o."+propertyName+" from "+className +" o where o.id=:id";
		Query query = this.getSession().createQuery(sql);
		query.setParameter("id", id);
		System.out.println(query.uniqueResult());
		return (E) query.uniqueResult();
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public List<T> like(String propertyName, String key) {
		Criterion criterion = Restrictions.like(propertyName, "%"+key+"%");
		return this.getCriteria(criterion).list();
	}
	
	
	@Override
	public Set<T> getRandomData(Integer count) {
		return null;
	}
	

	@Override
	public T getRandomEntity() {
		return null;
	}
	
	
	/**
	 * 批量插入数据
	 * @param list
	 */
	public void batchInsert(List<T> list) {
		Session session = getSession();
		for(int k=0; k<list.size(); k++){
			session.save(list.get(k));
			if((k+1)%50 == 0){
				session.flush();
				session.clear();
			}
		}
	}
	
}
