package com.itwillbs.order.action;

import java.util.ArrayList;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itwillbs.basket.db.BasketDAO;
import com.itwillbs.member.db.MemberDAO;
import com.itwillbs.member.db.MemberDTO;

public class OrderStartAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println(" M : OrderStartAction_execute() 호출 ");
		
		// 사용자 정보(세션제어)
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		ActionForward forward = new ActionForward();
		if(id == null) {
			forward.setPath("./MemberLogin.me");
			forward.setRedirect(false);
			return forward;
		}
		// 구매 정보(장바구니 정보)
		BasketDAO bDao = new BasketDAO();
		Vector totalList = bDao.getBasketList(id);
		ArrayList basketList = (ArrayList)totalList.get(0); 
		ArrayList goodsList = (ArrayList)totalList.get(1); 
		
		// 사용자 정보
		MemberDAO mDao = new MemberDAO();
		MemberDTO mDto = mDao.getMember(id);
		
		// 저장해서 view 출력
		request.setAttribute("basketList", basketList);
		request.setAttribute("goodsList", goodsList);
		request.setAttribute("mDto", mDto);
		
		// ./order/goods_buy.jsp
		forward.setPath("./order/goods_buy.jsp");
		forward.setRedirect(false);
		
		return forward;
	}

}
