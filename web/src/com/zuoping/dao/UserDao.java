package com.zuoping.dao;

import com.zuoping.entity.User;


public interface UserDao {
		/**
		 * 添加用户
		 * @param user 要添加的用户
		 */
		public void addUser(User user);
		
		/**
		 * 根据用户名匹配密码
		 * @param username
		 * @return 密码
		 */
		public User findPassword(String username);
		
		/**
		 * 寻找用户
		 * @param username
		 * @return 找到的用户
		 */
		public User findByusername(String username);
	}

