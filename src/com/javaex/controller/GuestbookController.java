package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.javaex.dao.GuestbookDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.GuestbookVo;

@WebServlet("/guest")
public class GuestbookController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String act = request.getParameter("action");
	
	if("deleteForm".equals(act)) {
		System.out.println("guest > deleteForm");
		
		WebUtil.forward(request, response, "/WEB-INF/views/guestbook/deleteForm.jsp");
		
		
	}else if("delete".equals(act)) {
		
		// 파라미터 값 추출 --> vo만들기
		int no = Integer.parseInt(request.getParameter("no"));
		String pw = request.getParameter("pass");
		
		GuestbookVo vo= new GuestbookVo();
		vo.setNo(no);
		vo.setPw(pw);
		
		GuestbookDao dao = new GuestbookDao();
		dao.delete(vo);
		
		WebUtil.redirect(request, response, "/mysite/guest");
		

	}else if("insert".equals(act)) {
		System.out.println("guest > insert");
		
		// 파라미터 값 추출 --> vo만들기
		String name = request.getParameter("name");
		String pw = request.getParameter("pass");
		String content = request.getParameter("content");
		
		GuestbookVo guestbookVo = new GuestbookVo(name, pw, content);		
		GuestbookDao guestbookDao = new GuestbookDao();
		
		guestbookDao.insert(guestbookVo);
		
		WebUtil.redirect(request, response, "/mysite/guest");

	
	}else{ //리스트
		System.out.println("guest > default:list");
		
		GuestbookDao guestbookDao = new GuestbookDao();
		List<GuestbookVo> gList = guestbookDao.getList();
		
		// 포워드 --> 데이터전달(요청문서의 바디(attributte))
		request.setAttribute("guestList", gList);
		WebUtil.forward(request, response, "/WEB-INF/views/guestbook/addList.jsp");
		
	}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
