package com.kh.mvc.admin.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.mvc.member.model.dto.Member;
import com.kh.mvc.member.model.dto.MemberRole;
import com.kh.mvc.member.model.service.MemberService;

/**
 * Servlet implementation class AdminMemberRoleUpdateServlet
 */
@WebServlet("/admin/memberRoleUpdate")
public class AdminMemberRoleUpdateServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private MemberService memberService = new MemberService();
	
	/**
	 * 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//1.사용자입력값
			String memberId = request.getParameter("memberId");
			MemberRole memberRole = MemberRole.valueOf(request.getParameter("memberRole"));
			Member member = new Member();
			member.setMemberId(memberId);
			member.setMemberRole(memberRole);
			System.out.println("member = " + member);
			
			//2.업무로직
			int result = memberService.updateMemberRole(member);
			
			//3.DML : redirect처리
			//referer : 요청을 하기 전 url => 어디서 머물다가 왔는지 알려주기 때문에 권한 수정시 그대로 있는것 처럼 보인다.
			request.getSession().setAttribute("msg", "회원권한을 성공적으로 수정했습니다.");
			String referer = request.getHeader("Referer");
			System.out.println("referer = " + referer);
			response.sendRedirect(referer);
//			response.sendRedirect(request.getContextPath() + "/admin/memberList");
			
		}catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

}
