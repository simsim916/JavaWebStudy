<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>|| Join ||</title>

</head>
<body>
<h2>** Join Test **</h2>
<form action="/web02/mjoin" method="post">
<table>
	<tr height="40"><td bgcolor="MediumPurple"><label for="id">아이디</label></td>
		<td><input type="text" id="id" name="id"></td>
	</tr>
	<tr height="40"><td bgcolor="MediumPurple"><label for="password">비밀번호</label></td>
		<td><input type="text" id="password" name="password"></td>
	</tr>
	<tr height="40"><td bgcolor="MediumPurple"><label for="name">이름</label></td>
		<td><input type="text" id="name" name="name"></td>
	</tr>
	<tr height="40"><td bgcolor="MediumPurple"><label for="age">나이</label></td>
		<td><input type="text" id="age" name="age"></td>
	</tr>
	<tr height="40"><td bgcolor="MediumPurple"><label for="jno">조번호</label></td>
		<td><select name="jno" id="jno">
			<option value="1">1조 : BusinessTeam</option>
			<option value="2">2조 : Static</option>
			<option value="3">3조 : 칭찬해조</option>
			<option value="4">4조 : 카톡으로얘기하조</option>
			<option value="7">7조 : 칠면조(관리팀)</option>
		</select></td>
	</tr>
	<tr height="40"><td bgcolor="MediumPurple"><label for="info">자기소개</label></td>
		<td><input type="text" id="info" name="info" placeholder="자기소개 & 희망사항"></td>
	</tr>
	<tr height="40"><td bgcolor="MediumPurple"><label for="point">포인트</label></td>
		<td><input type="text" id="point" name="point" placeholder="실수형"></td>
	</tr>
	<tr height="40"><td bgcolor="MediumPurple"><label for="birthday">생일</label></td>
		<td><input type="date" id="birthday" name="birthday"></td>
	</tr>
	<tr height="40"><td bgcolor="MediumPurple"><label for="rid">추천인</label></td>
		<td><input type="text" id="rid" name="rid"></td>
	</tr>
	<tr height="30"><td></td>
		<td><input type="submit" value="가입">&nbsp;&nbsp;
			<input type="reset" value="취소">
		</td>
	</tr>
</table>
<c:if test="${!empty requestScope.message}">
	회원가입 실패~
</c:if>
<a href="/web02/home.jsp">Home</a>
<a href="javascript:history.go(-1)">이전으로</a>
</body>
</html>