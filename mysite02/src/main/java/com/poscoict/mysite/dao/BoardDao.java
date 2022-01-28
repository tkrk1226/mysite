package com.poscoict.mysite.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.poscoict.mysite.vo.BoardVo;
import com.poscoict.mysite.vo.GuestbookVo;

public class BoardDao {

	private Connection getConnection() throws SQLException{
		Connection conn = null;
		
		try {
			//1. JDBC 드라이버 로딩
			Class.forName("org.mariadb.jdbc.Driver"); // Web에서 쓸 때는 필요하다. (JDBC만 할 때는 괜찮음)
			
			//2. 연결하기
			String url = "jdbc:mysql://192.168.0.83:3307/webdb?characterEncoding=UTF-8&serverTimezone=UTC";
			conn = DriverManager.getConnection(url, "webdb", "webdb");		
		} catch(ClassNotFoundException e) {
			System.out.print("드라이버 로딩 실패 : " + e); // e.getMessage()
		} 
		
		return conn;
	}
	
	public List<BoardVo> findAll(){
		
		List<BoardVo> result = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();

			//3. SQL 준비 b.contents
			String sql = "select b.no, b.title, b.hit,  a.name "
					+ ", date_format(reg_date, '%Y-%m-%d %H:%i:%s') as reg_date, b.user_no"
					+ ", b.o_no, b.depth from user a, board b"
					+ " where a.no = b.user_no"
					+ " order by b.g_no desc, b.o_no asc";
			pstmt = conn.prepareStatement(sql);

			//4. 바인딩(binding) setString...?			
			//5. SQL 실행
			rs = pstmt.executeQuery();
			
			//boiler plate code => 상투적인 코드 => 비효율 
			
			while(rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				int hit = rs.getInt(3);
				String userName = rs.getString(4);
				String regDate = rs.getString(5);
				Long userNo = rs.getLong(6);
				int orderNo = rs.getInt(7);
				int depth = rs.getInt(8);
				
				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setHit(hit);
				vo.setUserName(userName);
				vo.setRegDate(regDate);
				vo.setUserNo(userNo);
				vo.setOrderNo(orderNo);
				vo.setDepth(depth);
				
				result.add(vo);
			}
			
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
	
	public boolean insert(String title, String contents, Long userNo , int groupNo, int orderNo, int depth) {
		
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			//3. SQL 준비
			String sql = null;
			if(groupNo == -1 && orderNo == -1 && depth == -1) {
				sql = "insert into board values(null, ?, ?, 0, (select ifnull(max(g_no) + 1, 1) from board a) , 1, 1 , now(), ?)";	
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, title);
				pstmt.setString(2, contents);
				pstmt.setLong(3, userNo);
			} else {
				sql = "insert into board values(null, ?, ?, 0, ?, ?, ?, now(), ?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1, title);
				pstmt.setString(2, contents);
				pstmt.setInt(3, groupNo);
				pstmt.setInt(4, orderNo);
				pstmt.setInt(5, depth);
				pstmt.setLong(6, userNo);
			}

			//4. 바인딩(binding)	
			
			//5. SQL 실행 , executeQuery는 rs, executeUpdate는 int로 반환한다. 
			result = (pstmt.executeUpdate() == 1);
			
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
	
	public boolean delete(Long no) {
		
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			//3. SQL 준비
			String sql = "delete from board where no = ?";
			pstmt = conn.prepareStatement(sql);

			//4. 바인딩(binding)	
			pstmt.setLong(1, no);
			
			//5. SQL 실행 , executeQuery는 rs, executeUpdate는 int로 반환한다. 
			result = (pstmt.executeUpdate() == 1);
			
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
	
	public BoardVo findByNo(Long no) {
		
		BoardVo result = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();

			//3. SQL 준비 제목, 내용
			String sql = "select title, contents, user_no, hit, g_no, o_no, depth from board where no = ?";
			pstmt = conn.prepareStatement(sql);

			//4. 바인딩(binding) setString...?			
			pstmt.setLong(1, no);			
			
			//5. SQL 실행
			rs = pstmt.executeQuery();
			
			//boiler plate code => 상투적인 코드 => 비효율 
			
			if(rs.next()) {
				String title = rs.getString(1);
				String contents = rs.getString(2);
				Long userNo = rs.getLong(3);
				int hit = rs.getInt(4);
				int groupNo = rs.getInt(5);
				int orderNo = rs.getInt(6);
				int depth = rs.getInt(7);
				
				result = new BoardVo();
				
				result.setNo(no);
				result.setTitle(title);
				result.setContents(contents);
				result.setUserNo(userNo);
				result.setHit(hit);
				result.setGroupNo(groupNo);
				result.setOrderNo(orderNo);
				result.setDepth(depth);
			}
			
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

	public boolean update(String title, String contents, Long no) {
		
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			//3. SQL 준비
			
			String sql = "update board set title = ?, contents = ? where no = ?";
			pstmt = conn.prepareStatement(sql);

			//4. 바인딩(binding)	
			pstmt.setString(1, title);
			pstmt.setString(2, contents);
			pstmt.setLong(3, no);
			
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
	
	public boolean updateHit(int cnt, Long no) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			//3. SQL 준비
			
			String sql = "update board set hit = ? where no = ?";
			pstmt = conn.prepareStatement(sql);

			//4. 바인딩(binding)	
			pstmt.setInt(1, cnt);
			pstmt.setLong(2, no);
			
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

	public boolean updateBeforeAdd(int groupNo, int orderNo) {
		boolean result = false;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();
			
			//3. SQL 준비
			
			String sql = "update board set o_no = (o_no + 1) where g_no = ? and o_no > ?";
			pstmt = conn.prepareStatement(sql);

			//4. 바인딩(binding)	
			pstmt.setInt(1, groupNo);
			pstmt.setInt(2, orderNo);
			
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

	public int boardCnt(String kwd) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		int result = -1;
		
		try {
			conn = getConnection();

			//3. SQL 준비
			String sql = "select count(*) from board where title like '%"+ kwd + "%'";
			pstmt = conn.prepareStatement(sql);

			//4. 바인딩(binding) setString...?			
			//5. SQL 실행
			rs = pstmt.executeQuery();
			
			//boiler plate code => 상투적인 코드 => 비효율 
			
			if(rs.next()) {
				result = rs.getInt(1);
			}
			
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

	public List<BoardVo> findKwd(String kwd, int offset, int limit) {
		List<BoardVo> result = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {
			conn = getConnection();

			//3. SQL 준비 b.contents
			String sql = "select b.no, b.title, b.hit,  a.name "
					+ ", date_format(reg_date, '%Y-%m-%d %H:%i:%s') as reg_date, b.user_no"
					+ ", b.o_no, b.depth from user a, board b"
					+ " where a.no = b.user_no and b.title like '%" + kwd + "%'"
					+ " order by b.g_no desc, b.o_no asc limit ?, ?";
			pstmt = conn.prepareStatement(sql);

			//4. 바인딩(binding) setString...?			
//			pstmt.setString(1, kwd);
			pstmt.setInt(1, offset);
			pstmt.setInt(2, limit);
			
			//5. SQL 실행
			rs = pstmt.executeQuery();
			
			//boiler plate code => 상투적인 코드 => 비효율 
			
			while(rs.next()) {
				Long no = rs.getLong(1);
				String title = rs.getString(2);
				int hit = rs.getInt(3);
				String userName = rs.getString(4);
				String regDate = rs.getString(5);
				Long userNo = rs.getLong(6);
				int orderNo = rs.getInt(7);
				int depth = rs.getInt(8);
				
				BoardVo vo = new BoardVo();
				vo.setNo(no);
				vo.setTitle(title);
				vo.setHit(hit);
				vo.setUserName(userName);
				vo.setRegDate(regDate);
				vo.setUserNo(userNo);
				vo.setOrderNo(orderNo);
				vo.setDepth(depth);
				
				result.add(vo);
//				System.out.println(vo);
			}
			
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
}
