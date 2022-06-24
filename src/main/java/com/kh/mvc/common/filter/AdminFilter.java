package com.kh.mvc.common.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kh.mvc.member.model.dto.Member;
import com.kh.mvc.member.model.dto.MemberRole;

/**
 * Servlet Filter implementation class AdminFilter
 */
@WebFilter("/admin/*") //admin으로 시작하는 모~~~든 요청
public class AdminFilter implements Filter {

	/**
	 * 
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpReq = (HttpServletRequest) request;
		HttpServletResponse httpRes = (HttpServletResponse) response;
		HttpSession session = httpReq.getSession();
		
		//로그인 멤버 가져오기
		Member loginMember = (Member) session.getAttribute("loginMember");
		
		//조건식 순서도 중요하다~ 왼쪽항이 참이면 오른쪽항 검사 X
		if(loginMember == null || loginMember.getMemberRole() != MemberRole.A) {
			//관리자가 아닌 회원을 걸러냄
			session.setAttribute("msg", "관리자만 이용 가능합니다.");
			httpRes.sendRedirect(httpReq.getContextPath() + "/");
			return;
		}
		
		chain.doFilter(request, response); // -> 이게 실행되면 다음 서블릿으로 이동
	}

}
