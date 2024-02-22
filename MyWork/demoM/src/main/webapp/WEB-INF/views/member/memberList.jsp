<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> Spring02_MVC02 MemberList </title>
	<link rel="stylesheet" type="text/css" 
	href="resources/myLib/myStyle.css">
</head>
<body>
<h2> Spring02_MVC02 MemberList </h2>
<hr>
<c:if test="${!empty requestScope.message}">
=> ${requestScope.message}<br><hr>
</c:if>

<table border="1" style="width:100%">
	<tr bgcolor="Aquamarine">
		<th>ID</th><!-- <th>PASSWORD</th> --><th>NAME</th>
		<th>AGE</th><th>JNO</th><th>Info</th>
		<th>Point</th><th>BRITHDAY</th><th>추천인</th>
		<th>Image</th>
	</tr>
	
<c:if test="${!empty requestScope.banana }">
	<c:forEach var="b" items="${requestScope.banana}">
		<tr>
			<td>${b.id}</td><%-- <td>${b.password}</td> --%><td>${b.name}</td>
			<td>${b.age}</td><td>${b.jno}</td><td>${b.info}</td>
			<td>${b.point}</td><td>${b.birthday}</td><td>${b.rid}</td>
			<td><img alt="myImage" width="150" height="170"
				src="resources/uploadImages/${b.uploadfile}"></td>
		</tr>
	</c:forEach>
</c:if>
<c:if test="${empty requestScope.banana }">
	<tr>
		<td colspan="9"> 정보가 없습니다 </td>
	</tr>
</c:if>
</table>
<hr>
&nbsp;<a href="/home">Home</a>&nbsp;
&nbsp;<a href='javascript:history.go(-1)'>이전으로</a>&nbsp;

</body>
</html>