package com.kh.mvc.common.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * Servlet Filter implementation class EncodingFilter
 */
//우선순위는 web.xml에 등록순서대로
//@WebFilter("/*")
public class EncodingFilter implements Filter {
	private String encodingType;
	
    /**
     * Default constructor. 
     */
    public EncodingFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		request.setCharacterEncoding(encodingType);
		
//		System.out.println("[EncodingFilter : " + encodingType + "처리]");
		
		
		chain.doFilter(request, response);
	}

	/**
	 * web.xml에서 정보를 가져와서 사용할 수 있다.
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		this.encodingType = fConfig.getInitParameter("encodingType");
	}

}
