package com.zuoping.daoimpl;

import java.util.ArrayList;
import java.util.List;

import com.zuoping.dao.UserDao;
import com.zuoping.entity.User;
import com.zuoping.utils.BaseDao;

public class UserDaoImpl extends BaseDao implements UserDao {
	
	String sql = null;
	Object[] paramsValue = null;

	@Override
	public void addUser(User user) {
		sql = "insert into info(username, password, tel) "
				 + "values(?,?,?)";	
		List<String> list = new ArrayList<String>();
		
		list.add(user.getUsername());
		list.add(user.getPassword());
		list.add(user.getTel());

		
		paramsValue = list.toArray();
		
		super.update(sql, paramsValue);
	}

	@Override
	public User findPassword(String username) {
		sql = "select password from info where username = ?";
		paramsValue = new Object[] {username};
		List<User> list = super.query(sql, paramsValue, User.class);
		return (list != null && list.size() != 0) ? list.get(0) : null;
	}

	@Override
	public User findByusername(String username) {
		sql = "select * from info where username = ?";
		paramsValue = new Object[] {username};
		List<User> list = super.query(sql, paramsValue, User.class);
		return (list != null && list.size() != 0) ? list.get(0) : null;
	}

}