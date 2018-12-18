package com.zuoping.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zuoping.daoimpl.UserDaoImpl;

public class UserLoginServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		UserDaoImpl dao = new UserDaoImpl();
		resp.setContentType("text/html;charset=utf-8");
		resp.setCharacterEncoding("utf-8");
		
		//提取数据
		String username = req.getParameter("username");
		String password = req.getParameter("password");	
		
		if(dao.findByusername(username) != null){
			if(dao.findByusername(username).getUsername().equals("Angelic") && dao.findByusername(username).getPassword().equals(password)){
				resp.sendRedirect(req.getContextPath() + "/wxl-zy.html");
			}else if(dao.findByusername(username).getUsername().equals("暖Yang呐") && dao.findByusername(username).getPassword().equals(password)){
				resp.sendRedirect(req.getContextPath() + "/yly-zy.html");
			}else if(dao.findByusername(username).getUsername().equals("Asmolia") && dao.findByusername(username).getPassword().equals(password)){
				resp.sendRedirect(req.getContextPath() + "/yyl-zy.html");
			}else{
				resp.sendRedirect(req.getContextPath() + "/error.html");
			}
		}else{
			resp.sendRedirect(req.getContextPath() + "/error.html");
		}
	}


	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		doGet(req, resp);
	}

}
