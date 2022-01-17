package com.poscoict.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.poscoict.mysite.vo.UserVo;

public class UserDao {

	public boolean insert(UserVo vo) {
		
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			//3. SQL 준비
			String sql = "insert into user values(null, ?, ?, ?, ?, now());";
			pstmt = conn.prepareStatement(sql);

			//4. 바인딩(binding)	
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPassword());
			pstmt.setString(4, vo.getGender());
			
			//5. SQL 실행 , executeQuery는 rs, executeUpdate는 int로 반환한다. 
			result = (pstmt.executeUpdate() == 1);
			
			//boiler plate code => 상투적인 코드 => 비효율 
						
		} catch (SQLException e) {
			System.out.print("error : " + e); // e.getMessage()
		}
		
		finally {
			// 자원 정리 -> try OR catch 둘 다 실행 
			try {
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}

	public UserVo findByEmailAndPassword(String email, String password) {

		// 처음에는 null 이었지만 select에 있다면 제대로 된 객체를 반환하도록
		UserVo result = null;
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			//3. SQL 준비
			String sql = "select no, name from user where email = ? and password = ?";
			pstmt = conn.prepareStatement(sql);

			//4. 바인딩(binding)	
			pstmt.setString(1, email);
			pstmt.setString(2, password);
			
			//5. SQL 실행 , executeQuery는 rs, executeUpdate는 int로 반환한다. 
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				 Long no = rs.getLong(1);
				 String name = rs.getString(2);
				 
				 result = new UserVo();
				 result.setNo(no);
				 result.setName(name);
			}
			
			//boiler plate code => 상투적인 코드 => 비효율 
						
		} catch (SQLException e) {
			System.out.print("error : " + e); // e.getMessage()
		}
		
		finally {
			// 자원 정리 -> try OR catch 둘 다 실행 
			try {
				if(rs != null) {
					rs.close();
				}
				if(pstmt != null) {
					pstmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch(SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	private Connection getConnection() throws SQLException{
		Connection conn = null;
		
		try {
			//1. JDBC 드라이버 로딩
			Class.forName("com.mysql.cj.jdbc.Driver"); // Web에서 쓸 때는 필요하다. (JDBC만 할 때는 괜찮음)
			
			//2. 연결하기
			String url = "jdbc:mysql://localhost:3306/webdb?characterEncoding=UTF-8&serverTimezone=UTC";
			conn = DriverManager.getConnection(url, "webdb", "webdb");		
		} catch(ClassNotFoundException e) {
			System.out.print("드라이버 로딩 실패 : " + e); // e.getMessage()
		} 
		
		return conn;
	}
}
