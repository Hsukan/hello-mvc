package com.kh.mvc.member.controller;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.mvc.member.model.dto.Gender;
import com.kh.mvc.member.model.dto.Member;
import com.kh.mvc.member.model.service.MemberService;

/**
 * Servlet implementation class MemberInformation
 */
@WebServlet("/member/memberView")
public class MemberInformation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private MemberService memberService = new MemberService();
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		System.out.println("session@memberInformation = " + session.getAttribute("loginMember"));

		RequestDispatcher reqDispatcher = request.getRequestDispatcher("/WEB-INF/views/member/memberView.jsp");
		reqDispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 2. 사용자입력값처리
		String memberId = request.getParameter("memberId");
		String password = request.getParameter("password");
		String memberName = request.getParameter("memberName");
		String _gender = request.getParameter("gender");
		String _birthday = request.getParameter("birthday");
		String email = request.getParameter("email");
		String phone = request.getParameter("phone");
		String[] hobbies = request.getParameterValues("hobby");

		Gender gender = _gender != null ? Gender.valueOf(_gender) : null;
		String hobby = hobbies != null ? String.join(",", hobbies) : null;
		Date birthday = (_birthday != null && !"".equals(_birthday)) ? Date.valueOf(_birthday) : null;

		Member member = new Member(memberId, password, memberName, null, gender, birthday, email, phone, hobby, 0,
				null);
		System.out.println("member@MemberEnrollServlet = " + member);
		
		//3.
		int result = memberService.updatetMember(member); 
		System.out.println("result@MemberEnrollServlet = " + result);
		
		//4.
		HttpSession session = request.getSession();
		session.setAttribute("msg", "정보가 수정되었습니다.");
		session.setAttribute("loginMember", member);
		response.sendRedirect(request.getContextPath() + "/");
	}

}
