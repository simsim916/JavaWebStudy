<%@ page import="mvcTest.StudentDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>|| Home ||</title>
</head>
<body>
<h2>** Dynamic Web Project **</h2>
<hr>
 
<%
 StudentDTO dto = (StudentDTO)session.getAttribute("StudentDTO");
if(dto!=null){
session.setAttribute("name",dto.getName());
}
%>

<c:if test="${sessionScope.StudentDTO!=null}">
		<h2>${sessionScope.name}님 안녕하세요 ~~~ Web !!!</h2>
</c:if>
<c:if test="${sessionScope.StudentDTO==null}">
	<h3>로그인 후 이용하세요~~</h3>
</c:if>

<form action="getpost" method="get">
	<input type="text" name="id" value="abcd">
	<input type="text" name="name" value="가나다라">
	<input type="submit" value="Test">
</form>
<form action="list">
    <input type ="submit" value="학생조회">
</form>
<hr>
<img alt="asd" src="./images/a1.png" width="300px">
<hr>
<c:if test="${sessionScope.StudentDTO!=null}">
	<a href="/web01/myinfo">Myinfo</a>
	<a href="/web01/logout">Logout</a>
</c:if>
<c:if test="${sessionScope.StudentDTO==null}">
	<a href="/web01/servletTestForm/flowEx04_LoginForm.jsp">LoginForm</a>
</c:if>
<br>
<a href="/web01/hello">Hello</a>
<a href="/web01/list">M01List</a>
<a href="/web01/life">LifeCycle</a>
<br>
<a href="/web01/servletTestForm/form01_Adder.html">Adder</a>
<a href="/web01/servletTestForm/form02_Radio.jsp">Radio</a>
<a href="/web01/servletTestForm/form03_Check.jsp">Check</a>
<a href="/web01/servletTestForm/form04_Select.jsp">Select</a><br>
<a href="/web01/flow01">Flow01</a>
<a href="/web01/sessioni">SessionI</a><br>

<a href="/web01/jsp01/ex01_HelloJsp.jsp">HelloJsp</a>
<a href="/web01/jsp01/ex02_mvc01List.jsp">M01List</a>
<a href="/web01/list2">M02List</a>
<a href="/web01/list22">M02List2</a>
</body>
</html>