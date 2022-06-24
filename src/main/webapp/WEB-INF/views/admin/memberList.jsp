<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%
	List<Member> list = (List<Member>) request.getAttribute("list");
%>
<!-- 관리자용 admin.css link -->
<link rel="stylesheet" href="<%=request.getContextPath()%>/css/admin.css" />
<section id="memberList-container">
	<h2>회원관리</h2>
	
	<table id="tbl-member">
		<thead>
			<tr>
				<th>아이디</th>
				<th>이름</th>
				<th>회원권한</th><%-- select태그로 처리 --%>
				<th>성별</th>
				<th>생년월일</th>
				<th>이메일</th>
				<th>전화번호</th>
				<th>포인트</th><%-- 세자리컴마 DecimalFormat --%>
				<th>취미</th>
				<th>가입일</th><%-- 날짜형식 yyyy-MM-dd  --%>
			</tr>
		</thead>
		<tbody>
		<%
			if(list == null || list.isEmpty()){
		%>
			<tr>
				<td colspan="10" align="center"> 검색 결과가 없습니다. </td>
			</tr>
		<%
			} 
			else {
				for(Member m : list){
		%>
			<tr>
				<td><%= m.getMemberId() %></td>
				<td><%= m.getMemberName() %></td>
				<td>
					<select>
						<option value="A" <%= MemberRole.A == m.getMemberRole() ? "selected" : "" %>>관리자</option>
						<option value="U" <%= MemberRole.U == m.getMemberRole() ? "selected" : "" %>>일반</option>
					</select>	
				</td>
				<td><%= m.getGender() == null ? "" : ("M".equals(m.getGender()) ? "남" : "여") %></td>
				<td><%= m.getBirthday() %></td>
				<td><%= m.getEmail()!=null ? m.getEmail() : "" %></td>
				<td><%= m.getPhone() %></td>
				<td><%= new DecimalFormat("#,###").format(m.getPoint()) %></td>
				<td><%= m.getHobby() != null ? m.getHobby() : "" %></td>
				<td><%= new SimpleDateFormat("yyyy-MM-dd").format(m.getEnrollDate()) %></td>		
			</tr>		
		<%		} 
			}
		%>
			</tbody>

	</table>
</section>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>