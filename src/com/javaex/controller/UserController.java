package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;

@WebServlet("/user")
public class UserController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("/user");
		
		String act = request.getParameter("action");

		if ("joinForm".equals(act)) {		
			System.out.println("user > signup");
			
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinForm.jsp");
		
			
		}else if("join".equals(act)) {
			System.out.println("user > join");
			
			String id = request.getParameter("id");
			String pw = request.getParameter("password");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			
			UserVo userVo = new UserVo(id, pw, name, gender); 
			//System.out.println(userVo);
			
			UserDao userDao = new UserDao();
			userDao.insert(userVo);
			
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinOk.jsp");
			
			
		}else if("loginForm".equals(act)) {
			System.out.println("user > loginForm");
			
			WebUtil.forward(request, response, "/WEB-INF/views/user/loginForm.jsp");
		
			
		}else if("login".equals(act)) {
			System.out.println("user > login");
			
			String id = request.getParameter("id");
			String password = request.getParameter("password");
			
			System.out.println(id);
			System.out.println(password);
			
			UserDao userDao = new UserDao();
			UserVo authVo = userDao.getUser(id, password);	//id, password 주소나 null값이 옴.
			//System.out.println(authVo);
			
			if(authVo == null) { //로그인 실패
				System.out.println("로그인실패");
				
				WebUtil.redirect(request, response, "/mysite/user?action=loginForm&result=fail");
			
			}else{	//로그인 성공
				System.out.println("로그인성공");
				
				HttpSession session = request.getSession();	//지금접속한 놈의 세션영역(메모리영역)의 주소 내놔
				session.setAttribute("authUser", authVo);	//(별명, 넣을 값의 주소가 들어있는 곳)
				
				WebUtil.redirect(request, response, "/mysite/main");
				
			}
			
			
		}else if("logout".equals(act)) {
			System.out.println("user > logout");
			
			HttpSession session = request.getSession();
			session.removeAttribute("authUser");
			session.invalidate();
			
			WebUtil.redirect(request, response, "/mysite/main");
		
			
		}else if("modifyForm".equals(act)) {
			System.out.println("user > modifyForm");
			
			WebUtil.forward(request, response, "/WEB-INF/views/user/modifyForm.jsp");
			
			
		}else if("modify".equals(act)) {
			
			String pw = request.getParameter("pw");
			String name = request.getParameter("name");
			String gender = request.getParameter("gender");
			
			UserVo userVo = new UserVo();
			userVo.setPassword(pw);
			userVo.setName(name);
			userVo.setGender(gender);
			
			UserDao userDao = new UserDao();
			userDao.modify(userVo);
			
			WebUtil.redirect(request, response, "/mysite/main");
			
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
