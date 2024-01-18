<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** home **</title>
</head>
<body>
    <h2>Dynamic Web Project</h2>
    <c:choose>
        <c:when test="${sessionScope.loginName != null }">
            <h3>${sessionScope.loginName}님 안녕하세요</h3>
        </c:when>
        <c:otherwise>
            <h3>로그인 후 이용하세요</h3>
        </c:otherwise>
    </c:choose>

    <img alt="" src="images/letsgo.png" width="300" height="200">
    <hr>

<%--     <c:choose>
        <c:when test="${sessionScope.loginName == null }">
            <a href="/web01/servletTestForm/flowEx04_LoginForm.jsp">Login</a>&nbsp;
            <a href="/web01/logout">Logout</a>
            <br>&nbsp;
    </c:when>
        <c:otherwise>
            <a href="/web01/logout">Logout</a>
            <br>&nbsp;
        </c:otherwise>
    </c:choose> --%>
    
    <%
        if(request.getSession().getAttribute("loginName") == null) {
    %>
            <a href="/web02/member/LoginForm.jsp">Login</a>&nbsp;
            <a href="/web02/member/LoginForm.jsp">Join</a>&nbsp;
            <br>&nbsp;
    <%    } else { %>
            <a href="/web02/mdetail">Myinfo</a>
            <a href="/web02/logout">Logout</a>
            <br>&nbsp;  
    <%    } %>

    
    <a href="/web02/mlist">MList</a>
</body>
</html>