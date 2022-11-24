package com.itwillbs.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itwillbs.member.db.MemberDAO;
import com.itwillbs.member.db.MemberDTO;

public class MemberInfoAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println(" M : MemberInfoAction_execute() 호출 ");
		
		// 세션 제어
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		
		ActionForward forward = new ActionForward();
		if(id == null) {
			forward.setPath("./Main.me");
			forward.setRedirect(true);
			return forward;
		}
		
		
		// DAO - 회원정보 가져오는 메서드 (getMember(ID) )
		MemberDAO dao = new MemberDAO();
		MemberDTO dto = dao.getMember(id);
				
		// 정보를 request 영역에 저장(view 전달)
		request.setAttribute("dto", dto);
		
		// ./member/info.jsp 페이지 이동
		forward.setPath("./member/info.jsp");
		forward.setRedirect(false);
		
		return forward;
	}

}
