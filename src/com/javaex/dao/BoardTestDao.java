package com.javaex.dao;

import com.javaex.vo.BoardVo;

public class BoardTestDao {

	public static void main(String[] args) {
		
		
		BoardVo boardVo= new BoardVo();
		boardVo.setTitle("안녕");
		boardVo.setContent("안녕하세요");
		boardVo.setUser_no(2);
		
		BoardDao boardDao = new BoardDao();
		boardDao.insert(boardVo);
	}

}
