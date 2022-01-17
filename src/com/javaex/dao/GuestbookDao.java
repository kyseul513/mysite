package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.GuestbookVo;

public class GuestbookDao {

	// 필드
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;

	private String driver = "oracle.jdbc.driver.OracleDriver";
	private String url = "jdbc:oracle:thin:@localhost:1521:xe";
	private String id = "webdb";
	private String pw = "webdb";

	// 생성자 - 기본생성자 사용
	// 메소드 gs
	// 메소드 일반
	public void getConnection() {

		try {
			// 1. JDBC 드라이버 (Oracle) 로딩
			Class.forName(driver);

			// 2. Connection 얻어오기
			conn = DriverManager.getConnection(url, id, pw);

		} catch (ClassNotFoundException e) {
			System.out.println("error: 드라이버 로딩 실패 - " + e);
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
	}

	public void close() {

		try {
			if (rs != null) {
				rs.close();
			}
			if (pstmt != null) {
				pstmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

	}
	
	public int delete(GuestbookVo vo) {
		
		int count = 0;
		getConnection();
		
		try {
			String qry = "";
			qry += " delete from guestbook ";
			qry += " where no = ? ";
			qry += " and password = ? ";
			
			pstmt = conn.prepareStatement(qry);
			
			pstmt.setInt(1, vo.getNo());
			pstmt.setString(2, vo.getPw());
			
			count = pstmt.executeUpdate();
			
			System.out.println(count + "건 삭제");
			
		}catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		close();
		return count;
	}
	
	public int insert(GuestbookVo vo) {
		
		int count = 0;
		getConnection();
		
		try {
			String qry = "";
			qry += " insert into guestbook ";
			qry += " values(seq_guestbook_no.nextval, ?, ?, ?, sysdate) ";
			
			pstmt = conn.prepareStatement(qry);
			
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getPw());
			pstmt.setString(3, vo.getContent());
			
			count = pstmt.executeUpdate();
			
			System.out.println(count + "건이 등록되었습니다.");
			
		}catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		close();
		return count;
	}
	
	
	//전체 가져오기
	public List<GuestbookVo> getList() {
		
		List<GuestbookVo> guestbookList = new ArrayList<GuestbookVo>();
		
		getConnection();
			
			try {
				String qry = "";				
				qry += " select no, ";
				qry += "		name, ";
				qry += "		password, ";
				qry += "		content, ";
				qry += "		reg_date ";
				qry += " from guestbook ";
				qry += " order by reg_date desc ";

				
				pstmt = conn.prepareStatement(qry);
				
				rs = pstmt.executeQuery();
				
				
				while(rs.next()) {
					int no = rs.getInt("no");
					String name = rs.getString("name");
					String pw = rs.getString("password");
					String content = rs.getString("content");
					String regDate = rs.getString("reg_date");
					
					GuestbookVo guestbookVo = new GuestbookVo(no, name, pw, content, regDate);
					guestbookList.add(guestbookVo);
				}
				
			}catch (SQLException e) {
				System.out.println("error:" + e);
			}
			
		return guestbookList;
	}
	

}
