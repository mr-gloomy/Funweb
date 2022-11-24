package com.itwillbs.member.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itwillbs.member.db.MemberDAO;
import com.itwillbs.member.db.MemberDTO;

public class MemberJoinAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println(" M : MemberJoinAction_execute() 호출 ");
		
		// 한글처리(생략)
		// 전달된 정보 저장(회원가입 정보)
		// DTO
		MemberDTO dto = new MemberDTO();
		dto.setId(request.getParameter("id"));
		dto.setPw(request.getParameter("pw"));
		dto.setName(request.getParameter("name"));
		dto.setGender(request.getParameter("gender"));
		dto.setEmail(request.getParameter("email"));
		dto.setAddr(request.getParameter("addr"));
		dto.setTel(request.getParameter("tel"));
		
		String[] birthArr = request.getParameterValues("birth");
//		System.out.println(" M : birthArr[0] : "+birthArr[0]);
//		System.out.println(" M : birthArr[1] : "+birthArr[1]);
//		System.out.println(" M : birthArr[2] : "+birthArr[2]);
		
		if(birthArr != null) {
			dto.setBirth(birthArr[0]+"."+birthArr[1]+"."+birthArr[2]);
			
		}
		System.out.println(" M : dto : "+dto);
		
		// DAO 객체 생성 - 회원가입 메서드 호출
		MemberDAO dao = new MemberDAO();
		dao.memberJoin(dto);
		System.out.println(" M : 회원가입 성공 ");
		
		// 페이지 이동
		ActionForward forward = new ActionForward();
		forward.setPath("./MemberLogin.me");
		forward.setRedirect(true);
		
		
		
		
		return forward;
	}

}
