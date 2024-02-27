<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> LoginForm </title>
	<link rel="stylesheet" type="text/css" 
	href="/resources/myLib/myStyle.css">
<Script src="/resources/myLib/inCheck.js"></Script>
<Script>
"use strict"
let iCheck = false;
let pCheck = false;
onload = function(){
document.getElementById('id').focus();
	// ID
	document.getElementById('id').addEventListener('keydown', (e) => {
		if(e.which == 13){
			e.preventDefault();
			document.getElementById('password').focus();
		} // if
	});
	   
	document.getElementById('id').addEventListener('focusout', () => {
		iCheck=idCheck();	
	});
	   
	// password
	document.getElementById('password').addEventListener('keydown', (e) => {
		if(e.which == 13){
			e.preventDefault();
			document.getElementById('submitTag').focus();
		} // if
	});
	
	document.getElementById('password').addEventListener('focusout', () => {
		pCheck=pwCheck();	
	});
} // onload

function inCheck(){
	if(!iCheck){ document.getElementById('iMessage').innerHTML = ' 필수입력, id를 확인하세요 '}
	if(!pCheck){ document.getElementById('pMessage').innerHTML = ' 필수입력, password를 확인하세요 '}
	if(iCheck && pCheck){
		return true;
	} else {
		return false;
	}
} // inCheck

</Script>
</head>
<body>
<h2> Spring_MVC2 LoginForm </h2>
<form action="login" method="post">
<table>
	<tr height="40">
		<td bgcolor="Gold"><label for="id"> I D </label></td>
		<td><input type="text" name="id" id="id" size="20">
			<br> <span id="iMessage" class="eMessage"></span>
		</td>
	</tr>
	<tr height="40">
		<td bgcolor="Gold"><label for="password"> PASSWORD </label></td>
		<td><input type="password" name="password" id="password" size="20">
			<br> <span id="pMessage" class="eMessage"></span>
		</td>
	</tr>
	<tr>
		<td></td>
		<td>
		<input type="submit" id="submitTag" value="로그인" onclick="return inCheck()">&nbsp;&nbsp;
		<input type="reset" value="취소">
		</td>
	</tr>
</table>	
</form>
<hr>

<c:if test="${!empty requestScope.message}">
=> ${requestScope.message}	
</c:if>

<hr>
&nbsp;<a href="/home">Home</a>&nbsp;
&nbsp;<a href='javascript:history.go(-1)'>이전으로</a>&nbsp;

</body>
</html>