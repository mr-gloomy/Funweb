package com.itwillbs.member.action;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MemberFrontController extends HttpServlet {

	protected void doProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println(" Member - doProcess() 호출");

		// 1 가상주소 계산
		String requestURI = request.getRequestURI();
		System.out.println(" C : requestURI : " + requestURI);
		String ctxPath = request.getContextPath();
		System.out.println(" C : ctxPath : " + ctxPath);
		String command = requestURI.substring(ctxPath.length());
		System.out.println(" C : command : " + command);

		System.out.println(" C : 1. 가상주소 계산 끝 \n");
		
		Action action = null;
		ActionForward forward = null;
		// 2 가상주소 매핑(패턴 1,2,3)
		if (command.equals("/MemberJoin.me")) {
			System.out.println(" C : /MemberJoin.me 호출");
			System.out.println(" C : [패턴1] DB사용X, view이동");
			forward = new ActionForward();
			forward.setPath("./member/join.jsp");
			forward.setRedirect(false);

		} // Memberjoin.me 끝
		else if(command.equals("/MemberJoinAction.me")) {
			System.out.println(" C : /MemberJoinAction.me 호출");
			System.out.println(" C : [패턴2] DB사용O, 페이지 이동");
			
			// MemberJoinAction 객체 생성
			action = new MemberJoinAction();
			
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			
		} // MemberJoinAction.me 끝
		else if(command.equals("/MemberLogin.me")) {
			System.out.println(" C : /MemberLogin.me 호출");
			System.out.println(" C : [패턴1] DB사용X, view이동");
			forward = new ActionForward();
			forward.setPath("./member/login.jsp");
			forward.setRedirect(false);
			
			
		} // MemberLogin.me 끝
		else if(command.equals("/MemberIdCheck.me")) {
			System.out.println(" C : /MemberIdCheck.me 호출");
			System.out.println(" C : [패턴1] DB사용X, view이동");
			forward = new ActionForward();
			forward.setPath("./member/idCheck.jsp");
			forward.setRedirect(false);
			
		} // MemberIdCheck.me 끝
		else if(command.equals("/MemberIdCheckAction.me")) {
			System.out.println(" C : /MemberIdCheckAction.me 호출");
			System.out.println(" C : [패턴3] DB사용O, view출력");
			
			// MemberIdCheckAction() 객체 생성
			action = new MemberIdCheckAction();
			try {
				
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}// MemberIdCheckAction.me 끝
		else if(command.equals("/MemberLoginAction.me")) {
			System.out.println(" C : /MemberLoginAction.me 호출");
			System.out.println(" C : [패턴2] DB사용O, 페이지 이동");
			
			// MemberLoginAction() 객체생성
			action = new MemberLoginAction();
			try {
				forward = action.execute(request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
				
		}// MemberLoginAction.e 끝
		else if(command.equals("/Main.me")) {
			System.out.println(" C : /Main.me 호출");
			System.out.println(" C : [패턴1] DB사용X, view페이지 출력");
			forward = new ActionForward();
			forward.setPath("./main/main.jsp");
			forward.setRedirect(false);
		}// Main.me 끝
		else if(command.equals("/MemberLogout.me")) {
			System.out.println(" C : /MemberLogout.me 호출");
			System.out.println(" C : [패턴2] 비즈니스로직, 페이지이동");
			action = new MemberLogoutAction();
			try {
				forward = action.execute(request, response);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}// MemberLogout.me 끝
		else if(command.equals("/MemberInfo.me")) {
			System.out.println(" C : /MemberInfo.me 호출");
			System.out.println(" C : [패턴3] DB사용O, view페이지 출력");
			// MemberInfoAction()
			action = new MemberInfoAction();
			
			try {
				forward = action.execute(request, response);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		} // MemberInfo.me 끝
		else if(command.equals("/MemberUpdate.me")) {
			System.out.println(" C : /MemberUpdate.me 호출");
			System.out.println(" C : [패턴3] DB사용O, view페이지 출력");
			// MemberInfoAction()
			action = new MemberUpdateAction();
			
			try {
				forward = action.execute(request, response);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}// MemberUpdate.me 끝
		else if(command.equals("/MemberUpdatePro.me")) {
			System.out.println(" C : /MemberUpdatePro.me 호출" );
			System.out.println(" C : [패턴2] 비즈니스로직, 페이지이동 ");
			// MemberUpdateProAction() 객체
			action = new MemberUpdateProAction();
			
			try {
				
				forward = action.execute(request, response);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		} //  MemberUpdatePro.me 끝
		else if(command.equals("/MemberDelete.me")) {
			System.out.println(" C : /MemberDelete.me 호출");
			System.out.println(" C : [패턴1] DBX, view 출력");
			forward = new ActionForward();
			forward.setPath("./member/delete.jsp");
			forward.setRedirect(false);
			
		} // MemberDelete.me 끝
		else if(command.equals("/MemberDeleteAction.me")) {
			System.out.println(" C : /MemberDeleteAction.me 호출 ");
			System.out.println(" C : [패턴2] 비즈니스로직, 페이지이동 ");
			
			// MemberDeleteAction() 객체
			action = new MemberDeleteAction();
			
			try {
				forward = action.execute(request, response);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		} // MemberDeleteAction.me 끝
		else if(command.equals("/MemberAdmin.me")) {
			System.out.println(" C : /MemberAdmin.me 호출 ");
			System.out.println(" C : [패턴3] DB사용O, view 출력");
			
			// MemberAdminAction() 객체
			action = new MemberAdminAction();
			
			try {
				forward = action.execute(request, response);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}// MemberAdmin.me 끝
		else if(command.equals("/MemberAdminDeleteAction.me")) {
			System.out.println(" C : /MemberAdminDeleteAction.me 호출");
			System.out.println(" C : [패턴2] 비즈니스로직, 페이지이동 ");
			
			action = new MemberAdminDeleteAction();
			
			try {
				forward = action.execute(request, response);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	
		
		
		System.out.println(" C : 2. 가상주소 매핑 끝 \n");

		// 3 페이지 이동
		if (forward != null) { // 이동정보가 있을때 (티켓이 있을때)

			if (forward.isRedirect() == true) { // true
				System.out.println(" C : sendRedirect() : " + forward.getPath());
				response.sendRedirect(forward.getPath());

			} else { // false
				System.out.println(" C : forward() : " + forward.getPath());

				RequestDispatcher dis = request.getRequestDispatcher(forward.getPath());
				dis.forward(request, response);
			}

		}

		System.out.println(" C : 3. 페이지 이동 끝 \n");
	} // doProcess() 끝

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println(" Member - doGet() 호출");
		doProcess(request, response);

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println(" Member - doPost() 호출");
		doProcess(request, response);

	}

}
