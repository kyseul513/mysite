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
			
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			
			System.out.println(authUser);
			
			if(authUser != null) {
				System.out.println("로그인했을때");
				WebUtil.forward(request, response, "/WEB-INF/views/board/writeForm.jsp");
				
			}else{
				System.out.println("로그인 안했을 때");
				WebUtil.redirect(request, response, "/mysite/main");
			}

			
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
		
		}else if("delete".equals(act)) {
			
			int no = Integer.parseInt(request.getParameter("no"));
			
			BoardDao boardDao = new BoardDao();
			boardDao.delete(no);	//Dao의 delete에 no 전달
			
			WebUtil.redirect(request, response, "/mysite/board");
			
		}else if("read".equals(act)){
			
			int no = Integer.parseInt(request.getParameter("no"));	//list->read넘어올 때 파라미터에서 no값 받아오기
			
			BoardDao boardDao = new BoardDao();
			BoardVo boardVo = boardDao.getContent(no);	//getContent에 no 넣고 값 받아와 boardVo에 넣기
			
			request.setAttribute("bVo", boardVo);
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/read.jsp");
	
		}else if("modifyForm".equals(act)) {
			
			int no = Integer.parseInt(request.getParameter("no"));
			
			BoardDao boardDao = new BoardDao();
			BoardVo boardVo = boardDao.getContent(no);
			
			request.setAttribute("bVo", boardVo);
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/modifyForm.jsp");
			
		}else if("modify".equals(act)) {
			
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			int no = Integer.parseInt(request.getParameter("no"));
			
			BoardVo boardVo = new BoardVo();
			boardVo.setTitle(title);
			boardVo.setContent(content);
			boardVo.setNo(no);
			
			BoardDao boardDao = new BoardDao();
			boardDao.modify(boardVo);
			
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
