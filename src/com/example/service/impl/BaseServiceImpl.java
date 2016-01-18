package com.example.service.impl;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.example.dao.BaseDao;
import com.example.service.BaseService;
import com.example.utils.Direction;
import com.example.utils.Page;

/**
 * 业务层基类的封装类,封装通用的方法
 * @author anonymous
 *
 * @param <T>
 * @param <PK>
 */
@Transactional
public abstract class BaseServiceImpl<T, PK extends Serializable> implements BaseService<T,PK> {
	
	@Autowired
	protected BaseDao<T, PK> baseDao ;
	
	
	@Override
	public void save(T t) {
		baseDao.save(t);
	}

	@Override
	public void delete(T t) {
		baseDao.delete(t);
	}

	@Override
	public void delete(PK id) {
		baseDao.delete(id);
	}
	
	@Override
	public void update(T t) {
		baseDao.update(t);
	}

	@Override
	public T getById(PK id) {
		return baseDao.getById(id);
	}

	@Override
	public List<T> getAll() {
		return baseDao.getAll();
	}

	@Override
	public T getByProperty(String propertyName, Object propertyValue) {
		return baseDao.getByProperty(propertyName, propertyValue);
	}

	@Override
	public List<T> getsByProperty(String propertyName, Object propertyValue) {
		return baseDao.getsByProperty(propertyName, propertyValue);
	}

	@Override
	public List<T> getsByProperty(String propertyName, Object propertyValue,
			LinkedHashMap<String, Direction> orders) {
		return baseDao.getsByProperty(propertyName, propertyValue, orders);
	}

	@Override
	public Page<T> getPage(Integer page, Integer pageSize,
			LinkedHashMap<String, Direction> orders, String propertyName,
			Object propertyValue) {
		return baseDao.getPage(page, pageSize, orders, propertyName, propertyValue);
	}
	
	@Override
	public Page<T> getPage(Integer page, Integer pageSize) {
		return baseDao.getPage(page, pageSize, null, null, null);
	}

	@Override
	public int updatePropertyById(PK id, String propertyName,
			Object propertyValue) {
		return baseDao.updatePropertyById(id, propertyName, propertyValue);
	}

	@Override
	public <E> E getPropertyValueById(PK id, String propertyName) {
		return baseDao.getPropertyValueById(id, propertyName);
	}

	@Override
	public List<T> like(String propertyName, String key) {
		return baseDao.like(propertyName, key);
	}

	@Override
	public Set<T> getRandomData(Integer count) {
		return baseDao.getRandomData(count);
	}

	@Override
	public T getRandomEntity() {
		return baseDao.getRandomEntity();
	}
	
}
