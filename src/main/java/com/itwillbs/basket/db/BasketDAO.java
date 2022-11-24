package com.itwillbs.basket.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.itwillbs.admin.goods.db.GoodsDTO;

public class BasketDAO {
	
	private Connection con = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private String sql = "";

	// 디비 연결해주는 메서드(커넥션풀)
	private Connection getConnection() throws Exception {
		// 1. 드라이버 로드 // 2. 디비연결

		// Context 객체 생성 (JNDI API)
		Context initCTX = new InitialContext();
		// 디비연동정보 불러오기 (context.xml 파일정보)
		DataSource ds = (DataSource) initCTX.lookup("java:comp/env/jdbc/funweb");
		// 디비정보(연결) 불러오기
		con = ds.getConnection();

		System.out.println(" DAO : 디비연결 성공(커넥션풀) ");
		System.out.println(" DAO : con : " + con);

		return con;
	}// 커넥션풀 끝

	// 자원해제 메서드-closeDB()
	public void closeDB() {
		System.out.println(" DAO : 디비연결자원 해제");

		try {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (con != null)
				con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}// 자원해제 메서드-closeDB()
	
	// 기존의 장바구니 체크 - checkBasket()
	public boolean checkBasket(BasketDTO dto) {
		boolean result = false;
		
		try {
			con = getConnection();
			// 동일상품 정보 체크
			sql = "select * from itwill_basket "
					+ "where b_m_id=? and b_g_num=? and b_g_size=? and b_g_color=?"; 
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, dto.getB_m_id());
			pstmt.setInt(2, dto.getB_g_num());
			pstmt.setString(3, dto.getB_g_size());
			pstmt.setString(4, dto.getB_g_color());
			rs = pstmt.executeQuery();
			if(rs.next()) {// 동일상품 있으니 수량만 수정
				sql = "update itwill_basket set b_g_amount=b_g_amount+? "
						+ "where b_m_id=? and b_g_num=? and b_g_size=? and b_g_color=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, dto.getB_g_amount());
				pstmt.setString(2, dto.getB_m_id());
				pstmt.setInt(3, dto.getB_g_num());
				pstmt.setString(4, dto.getB_g_size());
				pstmt.setString(5, dto.getB_g_color());
				
				int tmp = pstmt.executeUpdate();
				if(tmp == 1) {
					result = true;
				}
			}//if(rs.next()) 끝
			System.out.println(" DAO : "+(result? "기존의 정보 수정":"기존의 상품 없음"));
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			closeDB();
		}
		return result;
	}// 기존의 장바구니 체크 - checkBasket()
	
	// 장바구니 추가 - basketAdd(DTO)
	public void baksetAdd(BasketDTO dto) {
		int b_num = 0;
		try {
			// 1) 장바구니 번호 계산
			con = getConnection();
			sql = "select max(b_num) from itwill_basket";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				b_num = rs.getInt(1)+1;
			}
			// 2) 장바구니 저장 (insert)
			sql = "insert into itwill_basket(b_num,b_m_id,b_g_num,b_g_amount,b_g_size,b_g_color) "
					+ "values(?,?,?,?,?,?)";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, b_num);
			pstmt.setString(2, dto.getB_m_id());
			pstmt.setInt(3, dto.getB_g_num());
			pstmt.setInt(4, dto.getB_g_amount());
			pstmt.setString(5, dto.getB_g_size());
			pstmt.setString(6, dto.getB_g_color());
			pstmt.executeUpdate();
			System.out.println(" DAO : 장바구니에 상품 담기 완료!");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			closeDB();
		}
	}// 장바구니 추가 - basketAdd(DTO)
	
	// 장바구니 목록 조회 - getBasketList(id)
	public Vector getBasketList(String id) {
		Vector totalList = new Vector();
		List basketList = new ArrayList();
		List goodsList = new ArrayList();
		try {
			con = getConnection();
			// id에 해당하는 장바구니정보 조회
			sql = "select * from itwill_basket where b_m_id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				// baksetList 에 장바구니정보 저장
				// DB -> DTO -> List
				BasketDTO bkDTO = new BasketDTO();
				bkDTO.setB_date(rs.getTimestamp("b_date"));
				bkDTO.setB_g_amount(rs.getInt("b_g_amount"));
				bkDTO.setB_g_color(rs.getString("b_g_color"));
				bkDTO.setB_m_id(rs.getString("b_m_id"));
				bkDTO.setB_g_num(rs.getInt("b_g_num"));
				bkDTO.setB_g_size(rs.getString("b_g_size"));
				bkDTO.setB_num(rs.getInt("b_num"));
				
				basketList.add(bkDTO);
//				System.out.println(" DAO : "+basketList);
				
					// 장바구니 상품에 해당하는 상품정보 조회
					// DB -> DTO -> List
					sql = "select * from itwill_goods where gno=?";
					PreparedStatement pstmt2 = con.prepareStatement(sql);
					pstmt2.setInt(1, bkDTO.getB_g_num());
					ResultSet rs2 = pstmt2.executeQuery();
					if(rs2.next()) {
						// 장바구니 상품정보를 찾음 -> 저장
						GoodsDTO gDTO = new GoodsDTO();
						gDTO.setName(rs2.getString("name"));
						gDTO.setPrice(rs2.getInt("price"));
						gDTO.setImage(rs2.getString("image"));
						gDTO.setGno(rs2.getInt("gno"));
						// .... 나머지 정보는 필요에 따라
						
						// list 저장
						goodsList.add(gDTO);
						// 상품정보 저장 완료
					}
					
					
			}//while
			
			// totalList에 저장
			totalList.add(basketList);
			totalList.add(goodsList);
			
			System.out.println(" DAO : 장바구니 정보 + 상품정보 저장완료 totalList : "+totalList);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			closeDB();
		}
		
		return totalList;
	}// 장바구니 목록 조회 - getBasketList(id)
	
	// 장바구니 목록삭제 - deleteBasket(b_num)
	public void deleteBasket(int b_num) {
		try {
			con = getConnection();
			sql = "delete from itwill_basket where b_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, b_num);
			pstmt.executeUpdate();
			System.out.println(" DAO : "+b_num+"번 장바구니 삭제 완료");
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			closeDB();
		}
	}// 장바구니 목록삭제 - deleteBasket(b_num, b_g_amount) - 끝
	
	// 장바구니 목록삭제 - deleteBasket(String id)
	public void deleteBasket(String id) {
		try {
			con = getConnection();
			sql = "delete from itwill_basket where b_m_id=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, id);
			pstmt.executeUpdate();
			System.out.println(" DAO : "+id+"님 구매후 장바구니 삭제 완료");
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			closeDB();
		}
	}// 장바구니 목록삭제 - deleteBasket(String id) - 끝
	
	// 장바구니 수량변경 - updateBasket(b_num)
	public void updateBasket(int b_num, int b_g_amount) {
		try {
			con = getConnection();
			sql = "update itwill_basket set b_g_amount=? where b_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, b_g_amount);
			pstmt.setInt(2, b_num);
			pstmt.executeUpdate();
			System.out.println(" DAO : 수량 수정완료");
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			closeDB();
		}
		
	}// 장바구니 수량변경 - updateBasket(b_num, b_g_amount) - 끝
	
	
	
	

}
