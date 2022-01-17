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
			
			//System.out.println(id);
			//System.out.println(password);
			
			UserDao userDao = new UserDao();
			UserVo authVo = userDao.getUser(id, password);	//UserDao의 getUser에서 id, password 주소나 null값이 옴.
			//System.out.println(authVo);
			
			if(authVo == null) { //로그인 실패
				System.out.println("로그인실패");
				
				WebUtil.redirect(request, response, "/mysite/user?action=loginForm&result=fail");
			
			}else{	//로그인 성공
				System.out.println("로그인성공");
				
				HttpSession session = request.getSession();	//지금접속한 놈의 세션영역(메모리영역)의 주소 내놔
				session.setAttribute("authUser", authVo);	// 세션에 값 저장(별명, 넣을 값의 주소가 들어있는 곳)
				
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
			
			//사용자 정보를 가져오기 위한 no값은 세션에서 가져온다.
			//세션의 정보 ==  로그인한 사용자 정보
			HttpSession session = request.getSession();
			int no = ((UserVo)session.getAttribute("authUser")).getNo();//authUser에 있는 no와 name 중 no불러옴
			
			//UserDao 의 getUser()로 사용자 정보 가져오기
			UserDao userDao = new UserDao();
			UserVo userVo = userDao.getUser(no);
			
			System.out.println(userVo);
			
			request.setAttribute("userVo", userVo);						
			
			WebUtil.forward(request, response, "/WEB-INF/views/user/modifyForm.jsp");
		
			
		}else if("modify".equals(act)) {
			System.out.println("user > modify");
			
			// 파라미터값 가져오기
			// 로그인한 사용자 정보는 세션에서 가져온다.
			HttpSession session = request.getSession();
			int no = ((UserVo)session.getAttribute("authUser")).getNo();
			
			String name = request.getParameter("name");
			String password = request.getParameter("pw");
			String gender = request.getParameter("gender");
			
			// 아래처럼 생성자 사용가능 --> 모양이 맞는 생성자 사용추천
			//UserVo vo = new UserVo(no, "", password, name, gender);
			
			UserVo vo = new UserVo();
			vo.setNo(no);
			vo.setName(name);
			vo.setPassword(password);
			vo.setGender(gender);
			
			UserDao userDao = new UserDao();
			userDao.modify(vo);
			
			UserVo sVo = (UserVo)session.getAttribute("authUser");	//세션에 변경된 이름 저장
			sVo.setName(name);
			
			WebUtil.redirect(request, response, "/mysite/main");
			
		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
