<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> updateForm </title>
</head>
<body>
<form action="update" method="get">
<table>
	<tr height="40">
		<td bgcolor="Plum"><label for="id"> JNO </label></td>
		<!-- readonly 읽기전용 설정, 서버로 데이터 전송
		disabled : 서버로 전송되지 않음 -->
		<td><input type="text" name="jno" id="jno" value="${requestScope.apple.jno}" readonly size="20"></td>
	</tr>
	<tr height="40">
		<td bgcolor="Plum"><label for="jname"> JNAME </label></td>
		<td><input type="text" name="jname" id="jname" value="${requestScope.apple.jname}" size="20"></td>
	</tr>
	<tr height="40">
		<td bgcolor="Plum"><label for="captain"> CAPTAIN </label></td>
		<td><input type="text" name="captain" id="captain" value="${requestScope.apple.captain}" size="20"></td>
	</tr>
	<tr height="40">
		<td bgcolor="Plum"><label for="project"> PROJECT </label></td>
		<td><input type="text" name="project" id="project" value="${requestScope.apple.project}" size="20"></td>
	</tr>
	<tr height="40">
		<td bgcolor="Plum"><label for="slogan"> SLOGAN </label></td>
		<td><input type="text" name="slogan" id="slogan" value="${requestScope.apple.slogan}" size="20"></td>
	</tr>
	<tr>
		<td></td>
		<td>
		<input type="submit" value="변경">&nbsp;&nbsp;
		<input type="reset" value="취소">
		</td>
	</tr>
</table>	
</form>
<hr>

<c:if test="${!empty requestScope.message}">
=> ${requestScope.message}	
</c:if>

<br>
&nbsp;<a href="/spring02/home">Home</a>&nbsp;
&nbsp;<a href='javascript:history.go(-1)'>이전으로</a>&nbsp;

</body>
</html>