package com.itwillbs.basket.aciton;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itwillbs.basket.db.BasketDAO;

public class AmountUpdateAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println(" M : AmountUpdateAction_execute() 호출");
		
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		
		ActionForward forward = new ActionForward();
		if(id == null) {
			forward.setPath("./MemberLogin.me");
			forward.setRedirect(true);
			return forward;
		}
		int b_num = Integer.parseInt(request.getParameter("b_num"));
		int b_g_amount = Integer.parseInt(request.getParameter("b_g_amount"));
		
		System.out.println(" b_g_amount : "+b_g_amount);
		BasketDAO dao = new BasketDAO();
		dao.updateBasket(b_num, b_g_amount);
		
		forward.setPath("./BasketList.ba");
		forward.setRedirect(true);
		
		return forward;
		
	}

}
