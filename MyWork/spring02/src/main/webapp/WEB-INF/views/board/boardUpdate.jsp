<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시글 작성</title>
</head>
<body>
<c:if test="${!empty requestScope.message}">
	<h3>${requestScope.message}</h3>
</c:if>
	<form action="update">
		<label>
			번호 : <input type="text" name="seq" value="${requestScope.seq}" readonly>
		</label>
		<br>
		<label>
			제목 : <input type="text" name="title" value="${requestScope.board.title}">
		</label>
		<br>
		<label>
			내용 : <input type="text" name="content" value="${requestScope.board.content}">
		</label>
		<input type="submit" value="제출">
		<input type="reset" value="리셋">
	</form>
</body>
</html>