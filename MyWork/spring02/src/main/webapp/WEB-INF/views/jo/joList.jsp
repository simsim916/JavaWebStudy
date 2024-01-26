<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> Spring02_MVC02 MemberList </title>
	<link rel="stylesheet" type="text/css" 
	href="/spring02/resources/myLib/myStyle.css">
</head>
<body>
<h2> Spring02_MVC02 JoList </h2>

<table border="1" style="width:100%">
	<tr bgcolor="Aquamarine">
		<th>JNO</th><th>JNAME</th><th>CAPTAIN</th>
		<th>PROJECT</th><th>SLOGAN</th>
	</tr>
	
<c:if test="${!empty requestScope.banana }">
	<c:forEach var="b" items="${requestScope.banana}">
		<tr>
			<td><a href="joDetail?jCode=D&jno=${b.jno}">${b.jno}</a></td>
			<td>${b.jname}</td><td>${b.captain}</td>
			<td>${b.project}</td><td>${b.slogan}</td>
		</tr>
	</c:forEach>
</c:if>
<c:if test="${empty requestScope.banana }">
	<tr>
		<td colspan="9"> 정보가 없습니다 </td>
	</tr>
</c:if>
</table>
&nbsp;<a href="joInsert">조추가</a>&nbsp;
<br>
&nbsp;<a href="/spring02/home">Home</a>&nbsp;
&nbsp;<a href='javascript:history.go(-1)'>이전으로</a>&nbsp;
<hr>
<c:if test="${!empty requestScope.message}">
=> ${requestScope.message}<br><hr>
</c:if>
</body>
</html>