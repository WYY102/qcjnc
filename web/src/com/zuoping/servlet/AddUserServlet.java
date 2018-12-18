package com.zuoping.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zuoping.dao.UserDao;
import com.zuoping.daoimpl.UserDaoImpl;
import com.zuoping.entity.User;

public class AddUserServlet extends HttpServlet {


	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		//提取数据
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String tel = req.getParameter("tel");
		
		UserDao dao = new UserDaoImpl();
		if(dao.findByusername(username) == null) {
			User u = new User(username, password, tel);
			
			dao.addUser(u);
			resp.sendRedirect(req.getContextPath() + "/index.html");
		}else {
			resp.sendRedirect(req.getContextPath() + "/error.html");
		}

	}


	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}

}
