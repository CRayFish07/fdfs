package com.example.dao.impl;

import org.springframework.stereotype.Repository;

import com.example.dao.UserDao;
import com.example.entity.User;

/**
 * user用户相关的持久层接口的实现类
 * 通用方法无需再定义,从基类中继承可直接使用,业务相关的特殊方法需另定义
 * @author anonymous
 */
@Repository
public class UserDaoImpl extends BaseDaoImpl<User, Integer> implements UserDao {
	
}
