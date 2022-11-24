package com.itwillbs.basket.aciton;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itwillbs.basket.db.BasketDAO;
import com.itwillbs.basket.db.BasketDTO;

public class BasketAddAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println(" M : BasketAddAction_execute() 호출 ");
		
		// 세션제어(id)
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		
		ActionForward forward = new ActionForward();
		if(id == null) {
			forward.setPath("./MemberLogin.me");
			forward.setRedirect(true);
			
			return forward;
		}
		
		// 장바구니 정보 저장 (구매할 상품 정보)
		// 1) 전달정보 저장
		BasketDTO dto = new BasketDTO();
		dto.setB_g_amount(Integer.parseInt(request.getParameter("amount")));
		dto.setB_g_color(request.getParameter("color"));
		dto.setB_g_num(Integer.parseInt(request.getParameter("gno")));
		dto.setB_g_size(request.getParameter("size"));
		dto.setB_m_id(id);
		System.out.println(" M : DTO : "+dto);
		// dto.setB_num(0);  B_num 은 bno, gno 처럼 계산해서 적용
		// 그러려고 정보 보내지도 않았음
		
		// 2) DB에 저장
		//	- 기존에 동일옵션의 정보가 있는지 판별
		//	- O : update	X : 정보 insert
		BasketDAO dao = new BasketDAO();
		boolean isUpdate = dao.checkBasket(dto);
		if(!isUpdate) {
			// 정보 insert
			dao.baksetAdd(dto);
			System.out.println(" M : 장바구니 추가 완료");
		}
		
		// 사용자의 선택에 따라서 장바구니 페이지로 이동/상품리스트 이동
		String isMove = request.getParameter("isMove");
		System.out.println(isMove);
		
		if(isMove.equals("true")) {
			// 페이지 이동
			forward.setPath("./BasketList.ba");
			forward.setRedirect(true);
		}else {
			// 페이지 이동
			forward.setPath("./GoodsList.go");
			forward.setRedirect(true);
		}
		

		
		return forward;
	}

}
