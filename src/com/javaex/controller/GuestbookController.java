package com.javaex.controller;

import java.io.IOException;

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
	
	if("board".equals(act)) {
		
		WebUtil.forward(request, response, "WEB-INF/views/guestbook/addList.jsp");
		
	}else if("delete".equals(act)) {
		
		int no = Integer.parseInt(request.getParameter("no"));
		
		GuestbookVo vo= new GuestbookVo();
		vo.setNo(no);
		
		GuestbookDao dao = new GuestbookDao();
		dao.delete(vo);
	}else if("insert".equals(act)) {
		
	}
	
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
