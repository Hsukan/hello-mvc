package com.kh.mvc.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.mvc.member.model.dto.Member;
import com.kh.mvc.member.model.service.MemberService;

/**
 * Servlet implementation class CheckIdDuplicateServlet
 */
@WebServlet("/member/checkIdDuplicate")
public class CheckIdDuplicateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MemberService memberService = new MemberService();

	/**
	 *  select * from member where member_id = ?
	 *  - member객체 반환 -> 해당 id 사용 불가
	 *  - null이 반환 -> 해당 id 사용 가능
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. 인코딩
		request.setCharacterEncoding("utf-8");
		
		// 2. 사용자 입력값 처리
		String memberId = request.getParameter("memberId");
		System.out.println("memberId@checkDuplicateServlet = " + memberId);
		
		// 3. 업무로직
		Member member = memberService.findById(memberId);
		//member(찾은 유저아이디)가 null이어야 사용가능하므로 null이 true
		boolean available = member == null;
		System.out.println("available = " + available);
		
		// 4. 응답 처리
		request.setAttribute("available", available);
		request.getRequestDispatcher("/WEB-INF/views/member/checkIdDuplicate.jsp")
			.forward(request, response);
	}

}
