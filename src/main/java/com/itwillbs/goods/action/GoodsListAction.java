package com.itwillbs.goods.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itwillbs.goods.db.GoodsDAO;

public class GoodsListAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println(" M : GoodsListAction_execute() 호출");
		
		// 전달정보 저장
		String item = request.getParameter("item");
		if(item==null) {
			item = "all";
		}
		System.out.println(" M : item : "+item);
		
		// DB에 저장된 상품정보를 가져오기
		GoodsDAO dao = new GoodsDAO();
		List goodsList = dao.getGoodsList(item);
		// 연결된 view 페이지로 정보 전달
		request.setAttribute("goodsList", goodsList);
		// 페이지 이동
		ActionForward forward = new ActionForward();
		forward.setPath("./company/goods_list.jsp");
		forward.setRedirect(false);
		
		return forward;
	}

}
