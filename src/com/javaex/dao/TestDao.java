package com.javaex.dao;

import com.javaex.vo.UserVo;

public class TestDao {

	public static void main(String[] args) {
		
		UserVo userVo = new UserVo("ccc", "1234", "강호동", "male");
		//setter 활용하거나 생성자 새로 생성하기
		
		UserDao userDao = new UserDao();
		
		userDao.insert(userVo);

	}

}
