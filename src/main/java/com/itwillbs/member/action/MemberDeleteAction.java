package com.itwillbs.member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itwillbs.member.db.MemberDAO;

public class MemberDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println(" M : MemberDeleteAction_execute() 호출 ");
		
		String id = request.getParameter("id");
		String pw = request.getParameter("pw");
		
		// DAO - 회원정보 삭제 메서드 호출
		MemberDAO dao = new MemberDAO();
		int result = dao.deleteMember(id, pw);
		
		
		// 페이지이동
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		if(result == 0) {
			out.print("<script>");
			out.print("alert('비밀번호 오류!'); ");
			out.print("history.back(); ");
			out.print("</script>");
			out.close();
			return null;
			
		}
		else if(result == -1) {

			out.print("<script>");
			out.print("alert('회원정보 없음!'); ");
			out.print("history.back(); ");
			out.print("</script>");
			out.close();
			return null;
			
		}else {
			// result == 1
			// 세션초기화
			HttpSession session = request.getSession();
			session.invalidate();
			out.print("<script>");
			out.print("alert('회원정보 삭제 완료!!'); ");
			out.print("location.href='./MemberInfo.me'; ");
			out.print("</script>");
			out.close();

			return null;
			
		}
	}

}
