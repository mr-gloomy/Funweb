package com.itwillbs.admin.goods.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class AdminGoodsDAO {
		private Connection con = null;
		private PreparedStatement pstmt = null;
		private ResultSet rs = null;
		private String sql = "";
		
		// 디비 연결해주는 메서드(커넥션풀)
		private Connection getConnection() throws Exception {
			// 1. 드라이버 로드	// 2. 디비연결
			
			// Context 객체 생성 (JNDI API)
			Context initCTX = new InitialContext();
			// 디비연동정보 불러오기 (context.xml 파일정보)
			DataSource ds 
			      = (DataSource)initCTX.lookup("java:comp/env/jdbc/funweb");
			// 디비정보(연결)  불러오기
			con = ds.getConnection();
			
			System.out.println(" DAO : 디비연결 성공(커넥션풀) ");
			System.out.println(" DAO : con : "+con);
			
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
		
		// 상품등록 메서드 - insertGoods(DTO)
		 public void insertGoods(GoodsDTO dto) {
			 // 1. 상품번호 계산
			 int gno = 0;
			 
			 try {
				con = getConnection();
				sql = "select max(gno) from itwill_goods";
				pstmt = con.prepareStatement(sql);
				rs = pstmt.executeQuery();
				if(rs.next()) {
					gno = rs.getInt(1)+1;
				}
				System.out.println(" DAO : 상품번호 계산 완료! gno : "+gno);
				 // 2. 상품등록
				sql = "insert into itwill_goods(gno,category,name,content,"
						+ "size,color,amount,price,image,best) values(?,?,?,?,?,?,?,?,?,?) ";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, gno);
				pstmt.setString(2, dto.getCategory());
				pstmt.setString(3, dto.getName());
				pstmt.setString(4, dto.getContent());
				pstmt.setString(5, dto.getSize());
				pstmt.setString(6, dto.getColor());
				pstmt.setInt(7, dto.getAmount());
				pstmt.setInt(8, dto.getPrice());
				pstmt.setString(9, dto.getImage());
				pstmt.setInt(10, dto.getBest());
				
				pstmt.executeUpdate();
				
				System.out.println(" DAO : 상품등록 완료! ");
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				closeDB();
			}
		 }// 상품등록 메서드 - insertGoods(DTO) - 끝
		 
		 // 상품갯수 확인
			public int getGoodsCount() {
				int cnt = 0;
				
				try {
					//1.2. 디비연결
					con = getConnection();
					// 3. sql
					sql = "select count(*) from itwill_goods";
					pstmt = con.prepareStatement(sql);
					
					// 4. sql 실행
					rs = pstmt.executeQuery();
					// 5. 데이터 처리
					if(rs.next()) {
						//cnt = rs.getInt(1);
						cnt = rs.getInt("count(*)");
					}
					System.out.println(" DAO : 전체 상품개수 : "+cnt+"개");
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					closeDB();
				}
				return cnt;
			} // getGoodsCount() 끝
			
			public List getadminGoodsList() {
				List adminGoodsList = new ArrayList();
				GoodsDTO dto = null;
				try {
					con = getConnection();
					sql = "select * from itwill_goods";
					pstmt = con.prepareStatement(sql);
					rs = pstmt.executeQuery();
					while(rs.next()) {
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
					
					adminGoodsList.add(dto);
					}
					System.out.println(" DAO : 관리자 상품리스트 저장완료");
					System.out.println(" DAO : "+adminGoodsList.size());
				} catch (Exception e) {
					e.printStackTrace();
				}finally {
					closeDB();
				}
				
				System.out.println(" DAO : 상품정보 조회 완료! ");
				return adminGoodsList;
			}// getadminGoodsList() 끝
		// 상품 1개저의 정보 가져오기 - getAdminGoods(gno)
		public GoodsDTO getAdminGoods(int gno) {
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
					dto.setGno(gno);
					dto.setImage(rs.getString("image"));
					dto.setName(rs.getString("name"));
					dto.setPrice(rs.getInt("price"));
					dto.setSize(rs.getString("size"));
				}
				System.out.println(" DAO : 상품정보 가져오기 완료 ");
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				closeDB();
			}
			
			return dto;
		}// 상품 1개저의 정보 가져오기 - getAdminGoods(gno)-끝
		
		// 상품정보 수정메서드 - adminModifyGoods(DTO)
		public void adminModifyGoods(GoodsDTO dto) {
			
			try {
				con = getConnection();
				sql = "update itwill_goods set "
						+ "category=?,name=?,price=?,color=?,amount=?,size=?,content=?,best=? "
						+ "where gno=?";
				
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, dto.getCategory());
				pstmt.setString(2, dto.getName());
				pstmt.setInt(3, dto.getPrice());
				pstmt.setString(4, dto.getColor());
				pstmt.setInt(5, dto.getAmount());
				pstmt.setString(6, dto.getSize());
				pstmt.setString(7, dto.getContent());
				pstmt.setInt(8, dto.getBest());
				pstmt.setInt(9, dto.getGno());
				
				pstmt.executeUpdate();
				System.out.println(" DAO : 상품정보 수정 완료 ");
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				closeDB();
			}
		}// 상품정보 수정메서드 - adminModifyGoods(DTO)-끝
		
		// 상품 삭제 메서드 - adminRemoveGoods(gno)
		public void adminRemoveGoods(int gno) {
			try {
				con = getConnection();
				sql = "delete from itwill_goods where gno=?";
				pstmt = con.prepareStatement(sql);
				pstmt.setInt(1, gno);
				pstmt.executeUpdate();
				
				System.out.println(" DAO : 상품삭제 완료 ");
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				closeDB();
			}
			
			
		}// 상품 삭제 메서드 - adminRemoveGoods(gno)-끝
		

		
		
}
