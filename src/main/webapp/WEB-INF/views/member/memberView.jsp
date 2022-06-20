<%@page import="java.util.Arrays"%>
<%@page import="com.kh.mvc.member.model.dto.Gender"%>
<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/common/header.jsp" %>
<%
	/* System.out.println("session@memberView = " + session.getAttribute("loginMember")); */
	/* System.out.println("memberId@memberView = " + loginMember.getMemberId()); */
	
	String memberId = loginMember.getMemberId();
	String password = loginMember.getPassword();
	String memberName = loginMember.getMemberName();
	Date birthday = loginMember.getBirthday();
	String email = loginMember.getEmail();
	String phone = loginMember.getPhone();
	int point = loginMember.getPoint();
	Gender gender = loginMember.getGender();
	System.out.println("gender@memberView = " + gender);
	String hobby = loginMember.getHobby();
	System.out.println("hobbies@memberView = " + hobby);
	String[] hobbies = hobby.split(",");
	/* System.out.println(Arrays.asList(hobbies).contains("운동")); */

%>
<section id=enroll-container>
	<h2>회원 정보</h2>
	<form name="memberUpdateFrm" method="post">
		<table>
			<tr>
				<th>아이디<sup>*</sup></th>
				<td>
					<input type="text" name="memberId" id="memberId" value="<%= memberId %>" readonly>
				</td>
			</tr>
			<tr>
				<th>패스워드<sup>*</sup></th>
				<td>
					<input type="password" name="password" id="password" value="<%= password %>" required>
				</td>
			</tr>
			<tr>
				<th>패스워드확인<sup>*</sup></th>
				<td>	
					<input type="password" id="passwordCheck" value="" required><br>
				</td>
			</tr> 
			<tr>
				<th>이름<sup>*</sup></th>
				<td>	
				<input type="text"  name="memberName" id="memberName" value="<%= memberName %>"  required><br>
				</td>
			</tr>
			<tr>
				<th>생년월일</th>
				<td>	
				<input type="date" name="birthday" id="birthday" value="<%= birthday %>"><br>
				</td>
			</tr> 
			<tr>
				<th>이메일</th>
				<td>	
					<input type="email" placeholder="abc@xyz.com" name="email" id="email" value="<%= email %>"><br>
				</td>
			</tr>
			<tr>
				<th>휴대폰<sup>*</sup></th>
				<td>	
					<input type="tel" placeholder="(-없이)01012345678" name="phone" id="phone" maxlength="11" value="<%= phone %>" required><br>
				</td>
			</tr>
			<tr>
				<th>포인트</th>
				<td>	
					<input type="text" placeholder="" name="point" id="point" value="<%= point %>" readonly><br>
				</td>
			</tr>
			<tr>
				<th>성별 </th>
				<td>
			       		 <input type="radio" name="gender" id="gender0" value="M"
			       		 <% if(gender == Gender.M){ %>
			       		 		checked
			       		 <% } %>	
			       		 >
						 <label for="gender0">남</label>
						 <input type="radio" name="gender" id="gender1" value="F"
			       		 <% if(gender == Gender.F){ %>
			       		 		checked
			       		 <% } %>	
						 >
						 <label for="gender1">여</label>
				</td>
			</tr>
			<tr>
				<th>취미 </th>
				<td>
					<input type="checkbox" name="hobby" id="hobby0" value="운동" 
					<% if(Arrays.asList(hobbies).contains("운동")){ %>
						checked
					<% } %>
					><label for="hobby0">운동</label>
					<input type="checkbox" name="hobby" id="hobby1" value="등산" 
					<% if(Arrays.asList(hobbies).contains("등산")){ %>
						checked
					<% } %>
					><label for="hobby1">등산</label>
					<input type="checkbox" name="hobby" id="hobby2" value="독서" 
					<% if(Arrays.asList(hobbies).contains("독서")){ %>
						checked
					<% } %>
					><label for="hobby2">독서</label><br />
					<input type="checkbox" name="hobby" id="hobby3" value="게임"
					<% if(Arrays.asList(hobbies).contains("게임")){ %>
						checked
					<% } %>
					 ><label for="hobby3">게임</label>
					<input type="checkbox" name="hobby" id="hobby4" value="여행"
					<% if(Arrays.asList(hobbies).contains("여행")){ %>
						checked
					<% } %>
					 ><label for="hobby4">여행</label><br />


				</td>
			</tr>
		</table>
        <input type="button" onclick="updateMember();" value="정보수정"/>
        <input type="button" onclick="deleteMember();" value="탈퇴"/>
	</form>
</section>
<%@ include file="/WEB-INF/views/common/footer.jsp" %>
