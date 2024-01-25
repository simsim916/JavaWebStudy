<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>Home</title>
</head>
<body>
	<h2>Hello Spring!</h2>

	<P>The time on the server is ${serverTime}.</P>
	<hr>
	<h2>Dynamic Web Project</h2>
	<c:if test="${sessionScope.mName != null }">
		<h3>${sessionScope.mName}님안녕하세요</h3>
	</c:if>
	<c:if test="${requestScope.message != null }">
		<h3>${requestScope.message}</h3>
	</c:if>
	<c:if test="${sessionScope.mName == null }">
		<h3>로그인 후 이용하세요</h3>
	</c:if>

<img alt="" src="resources/images/letsgo.png" width="300" height="200">
	<hr>

	<%
	if (request.getSession().getAttribute("mName") == null) {
	%>
	<a href="/web02/member/loginForm.jsp">Login</a>&nbsp;
	<a href="/web02/member/joinForm.jsp">Join</a>&nbsp;
	<br>&nbsp;
	<%
	} else {
	%>
	<a href="/web02/mdetail">Myinfo</a>
	<a href="/web02/mdetail?code=U">MyinfoEdit</a>
	<a href="/web02/logout">Logout</a>
	<br>&nbsp;
	<%
	}
	%>


	<a href="mlist">MList</a>
	<a href="mdetail">MDetail</a>
	<hr>
	
	<a href="mlistsp">MListSp</a>
	<a href="mdetailsp">MDetaiSpl</a>
	<hr>
	
	<a href="mlistsp2">MListAnno</a>
	<a href="mdetailsp2">MListAnno</a>
</body>
</html>
