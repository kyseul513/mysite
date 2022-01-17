package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.javaex.vo.UserVo;

public class UserDao {

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

	// 저장 메소드(회원가입)
	public int insert(UserVo userVo) { // 실패하면 0값을 반환하기 위해 void가 아닌 int로 설정
		
		int count = 0;	//int랑 getConnection 순서 상관 없음.
		getConnection();
		
		try {
			
			//문자열
			String qry = "";			
			qry +=" insert into users ";
			qry +=" values(seq_users_no.nextval, ?, ?, ?, ?) ";
			
			//쿼리문
			pstmt = conn.prepareStatement(qry);
			
			//바인딩
			pstmt.setString(1, userVo.getId());
			pstmt.setString(2, userVo.getPassword());
			pstmt.setString(3, userVo.getName());
			pstmt.setString(4, userVo.getGender());
			
			//실행
			count = pstmt.executeUpdate();
			
			//결과처리
			System.out.println(count + "건이 등록되었습니다(UserDao)");
			
		}catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		close();		
		return count;	//이건 항상 마지막에
	};
	
	
	//1명의 회원정보 가져오기 (로그인용)
	public UserVo getUser(String id, String password) {	//UserVo값을 반환해야 해서 void가 아닌 UserVo 사용.
		
		UserVo userVo = null;	//아래 괄호 안에 선언하면 괄호 벗어날 경우 사라지기 때문에 변수는 위에 선언하는게 나음
					   //아래에서 오류발생시 초기값인 null 반환. 
		getConnection();	
		
		try {
			
			//문자열
			String query = "";
			query +=" Select no, ";
			query +="		 name ";
			query +=" from users ";
			query +=" where id = ? ";
			query +=" and password = ? ";
			
			//쿼리문
			pstmt = conn.prepareStatement(query);
			
			//바인딩
			pstmt.setString(1, id);
			pstmt.setString(2, password);
			
			//실행
			rs = pstmt.executeQuery();	//덩어리로 오기 대문에 rs로 묶어줌
			
			//결과처리
			while(rs.next()) {		//1명값 가져오는거지만 그냥 while로 돌려줌.
				
				int no = rs.getInt("no");
				String name = rs.getString("name");	//id, password가 일치하는 회원의 no, name값 가져오기
				
				userVo = new UserVo();	//id, password를 하나로 묶어 주소를 userVo에 담기. 
				userVo.setNo(no);		//생성자 만들기 귀찮아서.. setter 활용.
				userVo.setName(name);				
			}
		}catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		close();		
		return userVo;		//userVo에 담긴 no, name값을 반환하기.
	}
	
	
	//사용자 정보 가져오기(modify 위해)(메소드 오버로딩)
	public UserVo getUser(int no) {	//로그인시 세션에 no, name을 올림->modifyForm에서 
		
		UserVo userVo = null;
		getConnection();
		
		try {
			String qry = "";
			qry += " Select no, ";
			qry += "		id, ";
			qry += "		password, ";
			qry += "		name, ";
			qry += "		gender ";
			qry += "		from users ";
			qry += "		where no = ? ";
			
			pstmt = conn.prepareStatement(qry);
			
			pstmt.setInt(1, no);		//첫번째 물음표에는 int no로 받아온 값을 넣는다.
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				
				int num = rs.getInt("no");
				String id = rs.getString("id");
				String password = rs.getString("password");
				String name = rs.getString("name");
				String gender = rs.getString("gender");
				
				userVo = new UserVo(num, id, password, name, gender);
			}
		}catch (SQLException e) {
			System.out.println("error:" + e);
		}
		close();
		return userVo;
	}
	
	
	public int modify(UserVo userVo) {
		
		int count = 0;
		getConnection();
		
			try {
				String qry = "";
				qry += " update users ";
				qry += " set name = ?, ";
				qry += "     password = ?, ";
				qry += "     gender = ? ";
				qry += " where no = ? ";
				
				pstmt = conn.prepareStatement(qry);
				
				pstmt.setString(1, userVo.getName());
				pstmt.setString(2, userVo.getPassword());
				pstmt.setString(3, userVo.getGender());
				pstmt.setInt(4, userVo.getNo());

				count = pstmt.executeUpdate();
				
				System.out.println(count + "건 수정");
				
			}catch (SQLException e) {
				System.out.println("error:" + e);
			}
		
		close();
		return count;
		
	}
	

}
