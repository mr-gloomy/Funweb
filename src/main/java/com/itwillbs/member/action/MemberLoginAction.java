package com.itwillbs.member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itwillbs.member.db.MemberDAO;

public class MemberLoginAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println(" M : MemberLoginAction_execute() 호출 ");
		
		// 한글처리(생략)
		// 전달정보 저장(id, pw)
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		
		// DAO 객체 - 로그인 여부 체크 메서드
		MemberDAO dao = new MemberDAO();
		int result = dao.memeberLogin(id, pw);
		
		// 체크 결과에 따른 페이지 이동(JS)

		if(result == 0) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print("<script>");
			out.print("alert('비밀번호 오류!'); ");
			out.print("history.back(); ");
			out.print("</script>");
			out.close();
			return null;
			
			
		
		}
		
		if(result == -1) {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print("<script>");
			out.print("alert('회원정보 없음!'); ");
			out.print("history.back(); ");
			out.print("</script>");
			out.close();
			return null;
		}
		
		// result == 1
		// 로그인 성공 -> 아이디 세션영역에 저장
		HttpSession session = request.getSession();
		session.setAttribute("id", id);
		
		ActionForward forward = new ActionForward();
		forward.setPath("./Main.me");
		forward.setRedirect(true);
		
		return forward;
	}

}
