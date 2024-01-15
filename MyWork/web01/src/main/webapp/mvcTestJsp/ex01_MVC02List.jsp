<%@page import="java.util.List"%>
<%@page import="mvcTest.StudentDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>MVC02_List_Java</title>
</head>
<body>
	<%
	List<StudentDTO> list = (List)request.getAttribute("stList");
			if (list != null) {%>

			<table><thead><tr>
			<th>학생번호</th><th>이름</th><th>나이</th><th>조번호</th><th>정보</th><th>포인트</th>
			</tr></thead><tbody></tbody>
<%		for (StudentDTO i : list){%>
			<tr>
			<td><%=i.getSno()%></td>
			<td><%=i.getName() %></td>
			<td><%=i.getAge() %></td>
			<td><%=i.getJno()%></td>
			<td><%=i.getInfo()%></td>
			<td><%=i.getPoint() %></td>
			</tr>
	
	<%}
	} else {%>
	<h3>출력할 자료가 없습니다.</h3>
	<%} %>
</body>
</html>