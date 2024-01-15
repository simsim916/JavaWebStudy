<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="mvcTest.StudentDTO"%>
<%@page import="java.util.List"%>
<%@page import="mvcTest.StudentService"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>|| Jsp StudentList MVC01 ||</title>
</head>
<body>

<h2>|| Jsp StudentList MVC01 ||</h2>
	<%
	StudentService sc = new StudentService();
	List<StudentDTO> list = sc.selectList();
	
	if (list != null) {%>

		<table><thead><tr>
		<th>학생번호</th><th>이름</th><th>나이</th><th>조번호</th><th>정보</th><th>포인트</th>
		</tr></thead><tbody></tbody>

		<%for (int i = 0; i < list.size(); i++) { %>
			<tr>
			<td><%=list.get(i).getSno()%></td>
			<td><%=list.get(i).getName() %></td>
			<td><%=list.get(i).getAge() %></td>
			<td><%=list.get(i).getJno()%></td>
			<td><%=list.get(i).getInfo()%></td>
			<td><%=list.get(i).getPoint() %></td>
			</tr>
	
	<%}
	} else {%>
	<h3>출력할 자료가 없습니다.</h3>
	<%} %>
	</table>
</body>
</html>