package com.kh.mvc.member.controller;

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.mvc.member.model.service.MemberService;

/**
 * Servlet implementation class MemberDeleteServlet
 */
@WebServlet("/member/memberDelete")
public class MemberDeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private MemberService memberService = new MemberService();

	/**
	 * 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			//1. 사용자 입력값 처리
			String memberId = request.getParameter("memberId");
			
			//2. 서비스로직호출
			int result = memberService.deleteMember(memberId);
			
			// 모든 속성 제거하기 -> session.invaild써도 됨
			
			HttpSession session = request.getSession();
//			session.invalidate();로 한번에 지울 수 있다. -> 이걸 쓰면 헤더에 메세지전송을 못한다.
			//세션을 지우고 밑에서 세션에 다시 msg를 넣기 때문에 안됨.
			Enumeration<String> names = session.getAttributeNames();
			while(names.hasMoreElements()) {
				String name = names.nextElement();
				session.removeAttribute(name);
			}
			// saveId cookie 제거
			Cookie c = new Cookie("saveId",memberId);
			c.setPath(request.getContextPath());
			c.setMaxAge(0);			//쿠키의 유효기간 0=> 즉시삭제
			response.addCookie(c);	

			//3. 리다이렉트 처리
			session.setAttribute("msg", "회원을 성공적으로 삭제했습니다.");
			response.sendRedirect(request.getContextPath() + "/");
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}

	}

}
