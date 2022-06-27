<%@page import="com.kh.mvc.member.model.dto.MemberRole"%>
<%@page import="com.kh.mvc.member.model.dto.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%
	String msg = (String) session.getAttribute("msg");
	if(msg != null) session.removeAttribute("msg");//한번만 사용후 제거
	Member loginMember = (Member) session.getAttribute("loginMember");
	
	//saveId 처리
	String saveId = null;
	Cookie[] cookies = request.getCookies();
	if(cookies != null){
	for(Cookie c : cookies){
		String name = c.getName();
		String value = c.getValue();
		System.out.println("[cookie]" + name + " = " + value); //[cookie]saveId = admin
		if("saveId".equals(name)){
			saveId = value;
		}
		
	}
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Hello MVC</title>
<!-- request.getContextPath() : 해당 contextPath 이름을 반환해줌 -->
<link rel="stylesheet"
	href="<%=request.getContextPath() %>/css/style.css" />
<script>
	window.onload = () => {
<% if(msg != null){ %>
		alert("<%= msg %>");
<% } %>

//로그인 시 아이디와 비밀번호 유효성 검사.
<% if(loginMember == null){ %>
		document.loginFrm.onsubmit = (e) => {
			const memberId = document.querySelector("#memberId");
			const password = document.querySelector("#password");
			
			if(!/^.{4,}$/.test(memberId.value)){
				alert("유효한 아이디를 입력해주세요.");
				memberId.select();
				return false;
			}
			if(!/^.{4,}$/.test(password.value)){
				alert("유효한 비밀번호를 입력해주세요.");
				password.select();
				return false;
			}
		};
		
	<% } %>

	};
</script>
</head>
<body>
	<div id="container">
		<header>
			<h1>Hello MVC</h1>
			<div class="login-container">
			<!-- 메인화면에서 세션에 로그인멤버 정보가 없을 경우 로그인창이 보이게 해줌 -->
			<% if(loginMember == null) { %>
			<%-- <% System.out.println(request.getContextPath()); %> --%><!--/mvc  -->
				<!-- 로그인폼 시작 -->
				<form id="loginFrm" name="loginFrm" action="<%= request.getContextPath()%>/member/login" method="post">
					<table>
						<tr>
							<!-- saveId값이 있을 경우 아이디 인풋에 넣어줌. -->
							<td><input type="text" name="memberId" id="memberId"
								placeholder="아이디" tabindex="1" 
								value="<%= saveId != null ? saveId : "" %>"></td>
								<!-- 로그인(submit)버튼 -->
							<td><input type="submit" value="로그인" tabindex="3"></td>
						</tr>
						<tr>
							<td><input type="password" name="password" id="password"
								placeholder="비밀번호" tabindex="2"></td>
							<td></td>
						</tr>
						<tr>
							<td colspan="2"><input type="checkbox" name="saveId"
								<%= saveId != null ? "checked" : "" %>
								id="saveId" /> <label for="saveId">아이디저장</label>&nbsp;&nbsp; <input
								type="button" value="회원가입"
								onclick="location.href='<%= request.getContextPath()%>/member/memberEnroll';"></td>
						</tr>
					</table>
				</form>
				<!-- 로그인폼 끝-->
				<!-- 로그인멤버가 있을경우 로그인 후 화면을 보여줌. -->
			<% } else { %>
				<table id="login">
					<tr>
						<td>[<%= loginMember.getMemberName() %>]님, 안녕하세요.</td>
					</tr>
					<tr>
						<td>
							<input type="button" value="내정보보기" 
							onclick="location.href='<%= request.getContextPath() %>/member/memberView';"/>
							<input type="button" value="로그아웃"
							onclick="location.href='<%= request.getContextPath() %>/member/Logout';" />
						</td>
					</tr>
				</table>
			
			<% } %>
			</div>
			<!-- 메인메뉴 시작 -->
			<nav>
				<ul class="main-nav">
					<li class="home"><a href="<%= request.getContextPath() %>">Home</a></li>
					<li class="notice"><a href="#">공지사항</a></li>
					<li class="board"><a href="<%= request.getContextPath() %>/board/boardList">게시판</a></li>
					<% if(loginMember != null && loginMember.getMemberRole() == MemberRole.A){ %>
					<li class="admin"><a href="<%= request.getContextPath()%>/admin/memberList">회원관리</a></li>
					<% } %>
				</ul>
			</nav>
		</header>

		<section id="content">