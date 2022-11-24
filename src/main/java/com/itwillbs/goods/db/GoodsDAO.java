package com.itwillbs.goods.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.itwillbs.admin.goods.db.GoodsDTO;
import com.itwillbs.basket.db.BasketDTO;

public class GoodsDAO {

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
	}

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
	}
	// 자원해제 메서드-closeDB()

	// 상품리스트 메서드
	public List getGoodsList() {
		List goodsList = new ArrayList();
		GoodsDTO dto = null;
		try {
			con = getConnection();
			sql = "select * from itwill_goods";
			pstmt = con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				dto = new GoodsDTO();

				dto.setAmount(rs.getInt("amount"));
				dto.setBest(rs.getInt("best"));
				dto.setCategory(rs.getString("category"));
				dto.setColor(rs.getString("color"));
				dto.setContent(rs.getString("content"));
				dto.setDate(rs.getTimestamp("date"));
				dto.setGno(rs.getInt("gno"));
				dto.setImage(rs.getString("image"));
				dto.setName(rs.getString("name"));
				dto.setPrice(rs.getInt("price"));
				dto.setSize(rs.getString("size"));

				goodsList.add(dto);
			}
			System.out.println(" DAO : 관리자 상품리스트 저장완료");
			System.out.println(" DAO : " + goodsList.size());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}

		System.out.println(" DAO : 상품정보 조회 완료! ");
		return goodsList;
	}// getadminGoodsList() 끝

	public List getGoodsList(String item) {
		List goodsList = new ArrayList();
		StringBuffer SQL = new StringBuffer();

		GoodsDTO dto = null;
		try {
			con = getConnection();
			// sql = "select * from itwill_goods";
			SQL.append("select * from itwill_goods");
			if (item.equals("all")) {
				System.out.println(" DAO : all "+SQL);
			} else if (item.equals("best")) {
				SQL.append(" where best=?");
				System.out.println(" DAO : best "+SQL);
			} else {
				SQL.append(" where category=?");
				System.out.println(" DAO : categroy "+SQL);
			}
//			pstmt = con.prepareStatement(SQL.toString());
			pstmt = con.prepareStatement(SQL+"");
			if(item.equals("all")) {
				
			}else if(item.equals("best")) {
				pstmt.setInt(1, 1);
			}else {
				pstmt.setString(1, item);
			}

			rs = pstmt.executeQuery();
			while (rs.next()) {
				dto = new GoodsDTO();

				dto.setAmount(rs.getInt("amount"));
				dto.setBest(rs.getInt("best"));
				dto.setCategory(rs.getString("category"));
				dto.setColor(rs.getString("color"));
				dto.setContent(rs.getString("content"));
				dto.setDate(rs.getTimestamp("date"));
				dto.setGno(rs.getInt("gno"));
				dto.setImage(rs.getString("image"));
				dto.setName(rs.getString("name"));
				dto.setPrice(rs.getInt("price"));
				dto.setSize(rs.getString("size"));

				goodsList.add(dto);
			}
			System.out.println(" DAO : 관리자 상품리스트 저장완료");
			System.out.println(" DAO : " + goodsList.size());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}

		System.out.println(" DAO : 상품정보 조회 완료! ");
		return goodsList;
	}// getGoodsList() 끝
	
	// 상품 상세정보 - getGoods(gno)
	public GoodsDTO getGoods(int gno) {
		GoodsDTO dto = null;
		
		try {
			con = getConnection();
			sql = "select * from itwill_goods where gno=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, gno);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				dto = new GoodsDTO();
				dto.setAmount(rs.getInt("amount"));
				dto.setBest(rs.getInt("best"));
				dto.setCategory(rs.getString("category"));
				dto.setColor(rs.getString("color"));
				dto.setContent(rs.getString("content"));
				dto.setDate(rs.getTimestamp("date"));
				dto.setGno(rs.getInt("gno"));
				dto.setImage(rs.getString("image"));
				dto.setName(rs.getString("name"));
				dto.setPrice(rs.getInt("price"));
				dto.setSize(rs.getString("size"));
			}
			System.out.println(" DAO : 상품조회 완료! : "+dto);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			closeDB();
		}
		
		return dto;
	}// getGoods(gno) 끝
	
	// 구매후 상품 수량 변경 - updateAmount()
    public void updateAmount(List basketList) {
       
       try {
          con = getConnection();
          
          for (int i = 0; i < basketList.size(); i++) {
             BasketDTO bkDTO = (BasketDTO)basketList.get(i);
             
             sql = "update itwill_goods set amount = amount-? where gno = ?";
             pstmt = con.prepareStatement(sql);
             pstmt.setInt(1, bkDTO.getB_g_amount());
             pstmt.setInt(2, bkDTO.getB_g_num());
             pstmt.executeUpdate();
          } // for
          
          System.out.println(" DAO : 구매후 수량 변경 완료! ");
          
       } catch (Exception e) {
          e.printStackTrace();
       } finally {
          closeDB();
       }
    }
    // 구매후 상품 수량 변경 - updateAmount()

	
	
	

}
