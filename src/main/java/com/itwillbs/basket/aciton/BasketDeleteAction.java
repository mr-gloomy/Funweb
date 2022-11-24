package com.itwillbs.basket.aciton;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itwillbs.basket.db.BasketDAO;

public class BasketDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println(" M : BasketDeleteAction_execute() 호출 ");
		
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		
		ActionForward forward = new ActionForward();
		if(id == null) {
			forward.setPath("./MemberLogin.me");
			forward.setRedirect(true);
			return forward;
		}
		
		int b_num = Integer.parseInt(request.getParameter("b_num"));
		
		
		BasketDAO dao = new BasketDAO();
		dao.deleteBasket(b_num);
		
		forward.setPath("./BasketList.ba");
		forward.setRedirect(true);
		
		return forward;
	}

}
