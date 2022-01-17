package com.javaex.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.javaex.vo.BoardVo;

public class BoardDao {

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

	public int insert(BoardVo vo) {

		int count = 0;
		getConnection();

		try {
			String qry = "";
			qry += " insert into board ";
			qry += " values(seq_board_no.nextval, ?, ?, default, sysdate, ?) ";

			pstmt = conn.prepareStatement(qry);

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContent());
			pstmt.setInt(3, vo.getUser_no());

			count = pstmt.executeUpdate();

			System.out.println(count + "건이 입력되었습니다.");

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		return count;
	}

	public List<BoardVo> getList() {

		List<BoardVo> boardList = new ArrayList<BoardVo>();
		
		getConnection();

		try {
			String qry = "";
			qry += " select bo.no, ";
			qry += "		bo.title, ";
			qry += "		bo.content, ";
			qry += "		bo.hit, ";
			qry += "		to_char(reg_date, 'yyyy-mm-dd') reg_date, ";
			qry += "		bo.user_no, ";
			qry += "		us.name ";
			qry += "		from board bo, users us ";
			qry += "		where bo.user_no = us.no ";
			qry += "		order by reg_date desc ";
			
			pstmt = conn.prepareStatement(qry);
			
			rs = pstmt.executeQuery();		
			
			while(rs.next()) {
				int no = rs.getInt("no");	//""에 들어가는 내용은 vo상 이름 입력.
				String title = rs.getString("title");
				String content = rs.getString("content");
				int hit = rs.getInt("hit");
				String regDate = rs.getString("reg_date");
				int userNo = rs.getInt("user_no");
				String name = rs.getString("name");
				
				BoardVo boardVo = new BoardVo(no, title, content, hit, regDate, userNo, name);
				boardList.add(boardVo);
				
				//System.out.println(boardList);
				
			}

		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		close();
		return boardList;
	}
	
	//read.jsp
	public BoardVo getContent(int no) {
		
		BoardVo boardVo = null;
		getConnection();
		
		try {
			String qry = "";
			qry += " select bo.title title, ";
			qry += "		bo.content content, ";
			qry += "		bo.hit hit, ";
			qry += "		to_char(reg_date, 'yyyy-mm-dd') reg_date, ";
			qry += "		us.name name";
			qry += "		from board bo, users us ";
			qry += "		where bo.user_no = us.no ";
			qry += "		and bo.no = ? ";
		
			pstmt = conn.prepareStatement(qry);
			
			pstmt.setInt(1, no); 	//첫번째 물음표에는 int no로 받아온 값을 넣는다.
			
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				String title = rs.getString("title");
				String content = rs.getString("content");
				int hit = rs.getInt("hit");
				String rdate = rs.getString("reg_date");
				String name = rs.getString("name");

				boardVo = new BoardVo(title, content, hit, rdate, name);
				//System.out.println(boardVo);
			}	
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}
		
		close();
		return boardVo;
	}

}
