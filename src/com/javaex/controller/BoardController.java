package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.BoardDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

@WebServlet("/board")
public class BoardController extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String act = request.getParameter("action");
		
		if("writeForm".equals(act)) {
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/writeForm.jsp");
			
		}else if("insert".equals(act)){
			
			HttpSession session = request.getSession();
			int uno = ((UserVo)session.getAttribute("authUser")).getNo();
			
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			
			BoardVo boardVo = new BoardVo();
			boardVo.setTitle(title);
			boardVo.setContent(content);
			boardVo.setUser_no(uno);
			
			BoardDao boardDao = new BoardDao();
			boardDao.insert(boardVo);
			
			WebUtil.redirect(request, response, "/mysite/board");
		
		}else if("read".equals(act)){
			
			HttpSession session = request.getSession();
			int no = ((BoardVo)session.getAttribute("boardList")).getNo();
			
			//BoardDao에서 getList()로 해당 no의 게시글 정보 가져오기
			BoardDao boardDao = new BoardDao();
			BoardVo boardVo = boardDao.getContent(no);
			
			request.setAttribute("content", boardVo);
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/read.jsp");
	
		}else if("modifyForm".equals(act)) {
						
			WebUtil.forward(request, response, "/WEB-INF/views/board/modifyForm.jsp");
			
		}else if("modify".equals(act)) {
			
			WebUtil.redirect(request, response, "/mysite/board");
			
		}else{
		
			BoardDao boardDao = new BoardDao();
			List<BoardVo> boardList = boardDao.getList();
			
			request.setAttribute("boardList", boardList);
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp");
			
		}
	
	}	

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
