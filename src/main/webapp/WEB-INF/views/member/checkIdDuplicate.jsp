<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
boolean available = (boolean) request.getAttribute("available");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>아이디중복검사</title>
<style>
div#checkId-container {
	text-align: center;
	padding-top: 50px;
}

span#duplicated {
	color: red;
	font-weight: bold;
}
</style>
</head>
<body>
	<div id="checkId-container">
		<%
		if (available) {
		%>
		<!-- id 사용가능한 경우 -->
		<p>
			<span><%=request.getParameter("memberId")%></span>는 사용이 가능합니다.
		</p>
		<button type="button" onclick="closePopup();">닫기</button>
		<% } else { %>
		<!-- id 사용불가능한 경우 -->
		<p>
			<span id="duplicated"><%=request.getParameter("memberId")%></span>는 이미 사용중입니다.
		</p>
		<form action="<%=request.getContextPath()%>/member/checkIdDuplicate" name="checkIdDuplicateFrm">
			<input type="text" name="memberId" placeholder="아이디를 입력하세요." /> 
			<input type="submit" value="중복검사" />
		</form>
		<script>
			document.checkIdDuplicateFrm.onsubmit = (e) => {
				const frm = e.target;
				if(!/^[a-zA-Z0-9]{4,}$/.test(frm.memberId.value)){
					alert("유효한 아이디를 입력하세요.");
					frm.memberId.select();
					return false;
				}
			}
		</script>
		<% } %>
	</div>
	<script>
	const closePopup = () => {
		// opener 부모윈도우객체
		const frm = opener.document.memberEnrollFrm;
		//히든폼으로 넘어온 idVaild값을 1로 만들어주어 가입이 가능하게 해준다.
		frm.idValid.value = 1;
		
		//그리고 부모윈도우에서 아이디인풋의 value값을 중복검사를 진행한 아이디로 적어줌.
		frm.memberId.value = "<%= request.getParameter("memberId") %>";
		
		self.close(); // 현재창 닫기
	};
	</script>
</body>
</html>
