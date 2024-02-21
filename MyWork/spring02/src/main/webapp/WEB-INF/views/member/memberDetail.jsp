<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> Spring02_MemberDetail </title>
	<link rel="stylesheet" type="text/css" 
	href="/spring02/resources/myLib/myStyle.css">
</head>
<body>
<table border="1" style="width:100%">
	<tr bgcolor="purple">
		<th>ID</th><th>PASSWORD</th><th>NAME</th><th>AGE</th><th>JNO</th>
		<th>Info</th><th>Point</th><th>BRITHDAY</th><th>추천인</th><th>Images</th>
	</tr>
<c:set var="a" value="${requestScope.apple}"/>
	<tr>
		<td>${a.id}</td><td>${a.password}</td><td>${a.name}</td><td>${a.age}</td><td>${a.jno}</td>
		<td>${a.info}</td><td>${a.point}</td><td>${a.birthday}</td><td>${a.rid}</td>
		<td><img alt="myImage" width="150" height="170"
				src="/spring02/resources/uploadImages/${a.uploadfile}"></td>
	</tr>
</table>

<c:if test="${!empty requestScope.message}">
=> ${requestScope.message}	
</c:if>

&nbsp;<a href="/spring02/home">Home</a>&nbsp;
&nbsp;<a href='javascript:history.go(-1)'>이전으로</a>&nbsp;

</body>
</html>