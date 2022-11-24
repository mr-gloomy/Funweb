package com.itwillbs.goods.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itwillbs.goods.db.GoodsDAO;

public class GoodsDetailAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println(" M : GoodsDetailAction_execute() 호출 ");
		// 한글처리 생략
		// 전달정보 저장(gno)
		int gno = Integer.parseInt(request.getParameter("gno"));
		// DAO - getGodds(gno) 상품정보 가져와서 view 출력
		GoodsDAO dao = new GoodsDAO();
		
		// request 저장
		request.setAttribute("dto", dao.getGoods(gno));
		
		// 페이지이동
		ActionForward forward = new ActionForward();
		forward.setPath("./company/goods_detail.jsp");
		forward.setRedirect(false);
		
		
		return forward;
	}

}
