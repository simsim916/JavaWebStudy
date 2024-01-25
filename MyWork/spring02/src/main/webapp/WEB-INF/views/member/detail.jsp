<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>|| My Info ||</title>
</head>
<body>
<c:set var="d" value="${requestScope.mDetail}"/>
<b>ID : </b> ${d.id} <br>
<b>Password : </b> ${requestScope.mDetail.password}
<b>NAME : </b> ${d.name} <br>
</body>
</html>