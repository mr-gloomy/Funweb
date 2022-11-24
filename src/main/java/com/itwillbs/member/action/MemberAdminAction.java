package com.itwillbs.member.action;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itwillbs.member.db.MemberDAO;

public class MemberAdminAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println(" M : MemberAdminAction_execute() 호출 ");
		
		// 관리자/로그인 체크
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		
		ActionForward forward = new ActionForward();
		if(id == null || !id.equals("admin")) {
			forward.setPath("./MemberLogin.me");
			forward.setRedirect(true);
			return forward;
		}
		
		// DAO = 회원정보 모두 가져오기
		MemberDAO dao = new MemberDAO();
		List memberList = dao.getMemberList();
		// request 영역에 저장
		request.setAttribute("memberList", memberList);
		
		forward.setPath("./member/admin.jsp");
		forward.setRedirect(false);
		
		return forward;
	}

}
