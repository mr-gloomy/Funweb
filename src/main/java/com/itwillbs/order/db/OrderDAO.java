package com.itwillbs.order.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.itwillbs.admin.goods.db.GoodsDTO;
import com.itwillbs.basket.db.BasketDTO;

public class OrderDAO {
	private Connection con = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	private String sql = "";
	
	// 디비 연결해주는 메서드(커넥션풀)
	private Connection getConnection() throws Exception {
		
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
	
	// 주문정보 저장 - addOrder(OrderDTO, BasketList, GoodsList)
	public void addOrder(OrderDTO orDTO,ArrayList bkList, ArrayList goList) {
	      int o_num = 0; // 일련번호
	      Calendar cal = Calendar.getInstance();
	      SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	      int trade_num = 0; // 주문번호
	      
	      try {
	         con = getConnection();
	         // o_num 계산하기
	         sql = "select max(o_num) from itwill_order";
	         pstmt = con.prepareStatement(sql);
	         rs = pstmt.executeQuery();
	         if(rs.next()) {
	            o_num = rs.getInt(1)+1;
	         }
	         trade_num = o_num;
	         // 주문번호 생성 ex) 20221114-1
	         // 주문정보 저장(최소 1개 이상)
	         for(int i=0;i<bkList.size();i++) {
	            BasketDTO bkDTO = (BasketDTO)bkList.get(i);
	            GoodsDTO goDTO = (GoodsDTO)goList.get(i);
	            
	            // insert
	            sql="insert into itwill_order "
	                  + "values(?,?,?,?,?,"
	                        + "?,?,?,?,?,"
	                        + "?,?,?,?,?,"
	                        + "?,now(),?,now(),?)";
	            pstmt = con.prepareStatement(sql);
	            // ???
	            pstmt.setInt(1, o_num);
	               pstmt.setString(2, sdf.format(cal.getTime())+"-"+trade_num);  //주문번호
	               
	               pstmt.setInt(3, bkDTO.getB_g_num());  //상품번호
	               pstmt.setString(4, goDTO.getName());  //상품이름
	               pstmt.setInt(5, bkDTO.getB_g_amount());  //상품수량
	               pstmt.setString(6, bkDTO.getB_g_size());  //상품사이즈
	               pstmt.setString(7, bkDTO.getB_g_color());  //상품컬러
	               
	               pstmt.setString(8, orDTO.getO_m_id());  //회원아이디
	               pstmt.setString(9, orDTO.getO_r_name());  //받는사람
	               pstmt.setString(10, orDTO.getO_r_phone()); //전화번호
	               pstmt.setString(11, orDTO.getO_r_addr1()); //주소1
	               pstmt.setString(12, orDTO.getO_r_addr2()); //주소2
	               pstmt.setString(13, orDTO.getO_r_msg()); //메모
	               
	               pstmt.setInt(14, bkDTO.getB_g_amount() * goDTO.getPrice());  //합계금액(상품 구매 총액)
	               
	               pstmt.setString(15, orDTO.getO_trade_type());  //결제타입
	               pstmt.setString(16, orDTO.getO_trade_payer());  //결제자
	               //pstmt.setTimestamp(17, null);  //결제시간
	               
	               pstmt.setString(17, "");  //운송장번호
	               
	               //pstmt.setTimestamp(18, null);  //테이블저장
	               pstmt.setInt(18, 0);  //주문상태
	               
	            pstmt.executeUpdate();
	            
	            o_num++;
	         } // for()
	         
	         System.out.println("DAO : 주문정보 저장완료!");
	         
	      } catch (Exception e) {
	         e.printStackTrace();
	      } finally {
	         closeDB();
	      }
	}// 주문정보 저장 - addOrder(OrderDTO, BasketList, GoodsList)
	
	public List<OrderDTO> getOrderList(String id) {
			List<OrderDTO> orderList = new ArrayList<OrderDTO>();
					
			try {
				con = getConnection();
				sql = "select "
						+ "o_trade_num,o_g_name,o_g_amount,o_g_color,o_g_size, "
						+ "sum(o_sum_money) as o_sum_mony,o_trans_num,o_date,o_status,o_trade_type "
						+ "from itwill_order "
						+ "where o_m_id=? "
						+ "group by o_trade_num "
						+ "order by o_trade_num asc";
				pstmt = con.prepareStatement(sql);
				pstmt.setString(1, id);
				rs = pstmt.executeQuery();
				while(rs.next()) {
					OrderDTO orDTO = new OrderDTO();
					orDTO.setO_trade_num(rs.getString(1));
					orDTO.setO_g_name(rs.getString(2));
					orDTO.setO_g_amount(rs.getInt(3));
					orDTO.setO_g_color(rs.getString(4));
					orDTO.setO_g_size(rs.getString(5));
					orDTO.setO_sum_money(rs.getInt(6));
					orDTO.setO_trans_num(rs.getString(7));
					orDTO.setO_date(rs.getTimestamp(8));
					orDTO.setO_status(rs.getInt(9));
					orDTO.setO_trade_type(rs.getString(10));
					orderList.add(orDTO);
				}//while
				System.out.println(" DAO : "+id+"님의 주문정보 저장 완료");
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				closeDB();
			}
		
		
		return orderList;
	}

	// 주문상세정보 - orderDetail(trade_num)
	public List<OrderDTO> orderDetail(String trade_num){
		List<OrderDTO> orderList = new ArrayList<OrderDTO>();
		
		try {
			con= getConnection();
			sql = "select * from itwill_order where o_trade_num=?";
			pstmt = con.prepareStatement(sql);
			pstmt.setString(1, trade_num);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				OrderDTO dto = new OrderDTO();
				
				dto.setO_date(rs.getTimestamp("o_date"));
				dto.setO_g_amount(rs.getInt("o_g_amount"));
				dto.setO_g_color(rs.getString("o_g_color"));
				dto.setO_g_size(rs.getString("o_g_size"));
				dto.setO_g_name(rs.getString("o_g_name"));
				dto.setO_trade_num(trade_num); // 받아온거라서 그냥 쓴거 디비에서 꺼내와도 된다
				dto.setO_trans_num(rs.getString("o_trans_num"));
				dto.setO_sum_money(rs.getInt("o_sum_money"));
				dto.setO_status(rs.getInt("o_status"));
				dto.setO_trade_type(rs.getString("o_trade_type"));
				
				orderList.add(dto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeDB();
		}
		
		return orderList;
	}// 주문상세정보 - orderDetail(trade_num)
	
	
	
	
}
