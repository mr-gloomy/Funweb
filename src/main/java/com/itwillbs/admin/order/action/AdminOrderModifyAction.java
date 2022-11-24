package com.itwillbs.admin.order.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itwillbs.admin.order.db.AdminOrderDAO;
import com.itwillbs.order.db.OrderDTO;


public class AdminOrderModifyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println(" M : AdminOrderModifyAction_execute() 호출 ");
		
		// 세션제어(관리자)
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		ActionForward forward = new ActionForward();
		if(id == null || !id.equals("admin")) {
			forward.setPath("./MemberLogin.me");
			forward.setRedirect(false);
			return forward;
		}
		// 전달정보(status,trade_num,trans_num) 저장 DTO
		OrderDTO orDTO = new OrderDTO();
		
		orDTO.setO_status(Integer.parseInt(request.getParameter("status")));
		orDTO.setO_trade_num(request.getParameter("trade_num"));
		orDTO.setO_trans_num(request.getParameter("trans_num"));
		
		// DAO - updateOrder(DTO)
		AdminOrderDAO dao = new AdminOrderDAO();
		dao.updateOrder(orDTO);
		
		// 페이지이동(./AdminOrderList.ao)
		forward.setPath("./AdminOrderList.ao");
		forward.setRedirect(true);
		
		return forward;
	}

}
