<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>|| Update ||</title>

</head>
<body>
<h2>** Update Test **</h2>
<c:set var="d" value="${requestScope.mDetail}" />
<form action="/web02/mupdate" method="post">
<table>
	<tr height="40"><td bgcolor="MediumPurple"><label for="id">아이디</label></td>
		<td><input type="text" id="id" name="id" value="${d.id}" readonly></td>
	</tr>
	<tr height="40"><td bgcolor="MediumPurple"><label for="password">비밀번호</label></td>
		<td><input type="text" id="password" name="password" value="${d.password}"></td>
	</tr>
	<tr height="40"><td bgcolor="MediumPurple"><label for="name">이름</label></td>
		<td><input type="text" id="name" name="name" value="${d.name}" ></td>
	</tr>
	<tr height="40"><td bgcolor="MediumPurple"><label for="age">나이</label></td>
		<td><input type="text" id="age" name="age" value="${d.age}"></td>
	</tr>
	<tr height="40"><td bgcolor="MediumPurple"><label for="jno">조번호</label></td>
		<td><select name="jno" id="jno">
			<option value="1" ${d.jno==1? "selected" : ""}>1조 : BusinessTeam</option>
			<option value="2" ${d.jno==2? "selected" : ""}>2조 : Static</option>
			<option value="3" ${d.jno==3? "selected" : ""}>3조 : 칭찬해조</option>
			<option value="4" ${d.jno==4? "selected" : ""}>4조 : 카톡으로얘기하조</option>
			<option value="7" ${d.jno==7? "selected" : ""}>7조 : 칠면조(관리팀)</option>
		</select></td>
	</tr>
	<tr height="40"><td bgcolor="MediumPurple"><label for="info">자기소개</label></td>
		<td><input type="text" id="info" name="info" value="${d.info}"></td>
	</tr>
	<tr height="40"><td bgcolor="MediumPurple"><label for="point">포인트</label></td>
		<td><input type="text" id="point" name="point"  value="${d.point}"></td>
	</tr>
	<tr height="40"><td bgcolor="MediumPurple"><label for="birthday">생일</label></td>
		<td><input type="date" id="birthday" name="birthday" value="${d.birthday}"></td>
	</tr>
	<tr height="40"><td bgcolor="MediumPurple"><label for="rid">추천인</label></td>
		<td><input type="text" id="rid" name="rid" value="${d.rid}"></td>
	</tr>
	<tr height="30"><td></td>
		<td><input type="submit" value="수정">&nbsp;&nbsp;
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