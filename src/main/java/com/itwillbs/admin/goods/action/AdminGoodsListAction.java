package com.itwillbs.admin.goods.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itwillbs.admin.goods.db.AdminGoodsDAO;
import com.itwillbs.admin.goods.db.GoodsDTO;

public class AdminGoodsListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println(" M : AdminGoodsListAction_execute() 호출 ");
		// 세션제어
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		if(id == null || !id.equals("admin")) {
			response.sendRedirect("./Main.me");
			return null;
		}
		
		// DAO - adminGoodsList 메서드
		AdminGoodsDAO dao = new AdminGoodsDAO();
		List adminGoodsList = dao.getadminGoodsList();
		// 상품정보 request 영역에 저장
		request.setAttribute("adminGoodsList", adminGoodsList);
		
		// view출력 (./center/admin_goods_list.jsp)
		ActionForward forward = new ActionForward();
		forward.setPath("./center/admin_goods_list.jsp");
		forward.setRedirect(false);
		
		return forward;
	}

	
}