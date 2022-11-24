package com.itwillbs.member.action;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itwillbs.member.db.MemberDAO;
import com.itwillbs.member.db.MemberDTO;

public class MemberUpdateProAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println(" M : MemberUpdateProAction_execute() 호출 ");
		
		// 한글처리(필터)
		// 전달데이터 저장(수정정보)
		
		MemberDTO dto = new MemberDTO();
		dto.setId(request.getParameter("id"));
		dto.setPw(request.getParameter("pw"));
		dto.setName(request.getParameter("name"));
		dto.setEmail(request.getParameter("email"));
		dto.setAddr(request.getParameter("addr"));
		dto.setTel(request.getParameter("tel"));
		dto.setGender(request.getParameter("gender"));
		String birth = request.getParameterValues("birth")[0]+"."+request.getParameterValues("birth")[1]+"."+request.getParameterValues("birth")[2];
		dto.setBirth(birth);
		
		System.out.println(" M : 수정될 정보 : "+dto);
		
		// DAO - 회원정보 수정
		MemberDAO dao = new MemberDAO();
		int result = dao.updateMember(dto);
		
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
			out.print("<script>");
			out.print("alert('회원정보 수정 완료!!'); ");
			out.print("location.href='./MemberInfo.me'; ");
			out.print("</script>");
			out.close();
			return null;
			
		}
		
	}

}
