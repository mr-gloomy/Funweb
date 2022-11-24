package com.itwillbs.basket.db;

import java.sql.Timestamp;

import lombok.Data;

@Data // DTO 객체 생성에 필요한 모든 동작 자동구현
public class BasketDTO {
	
	private int b_num;
	private String b_m_id;
	private int b_g_num;
	private int b_g_amount;
	private String b_g_size;
	private String b_g_color;
	private Timestamp b_date;

}
