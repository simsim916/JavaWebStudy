<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> Spring MVC2 Password Update </title>
	<link rel="stylesheet" type="text/css" 
	href="resources/myLib/myStyle.css">
<script src="resources/myLib/inCheck.js"></script>
<script>
let pCheck = false;
let p2Check = false;

onload = function(){
	document.getElementById('password').focus();
	// password
	document.getElementById('password').addEventListener('keydown', (e) => {
		if(e.which == 13){
			e.preventDefault();
			document.getElementById('password2').focus();
		} // if
	});
	document.getElementById('password').addEventListener('focusout', () => {
		pCheck=pwCheck();	
	});
	
	// password2
	document.getElementById('password2').addEventListener('keydown', (e) => {
		if(e.which == 13){
			e.preventDefault();
			document.getElementById('submitTag').focus();
		} // if
	});
	document.getElementById('password2').addEventListener('focusout', () => {
		p2Check=pw2Check();	
	});
} // onload

function inCheck(){
	if(!pCheck){ document.getElementById('pMessage').innerHTML = ' 필수입력, password를 확인하세요 '}
	if(!p2Check){ document.getElementById('p2Message').innerHTML = ' 필수입력, password2를 확인하세요 '}
	// submit 확인
	if(pCheck && p2Check){
		if(confirm("정말 수정하겠습니까? (Yes:확인/No:취소)")){
			// submit 진행
			return true;
		} else{
			alert("수정이 취소됐")
			return false;
		} // confirm
	} else{
		// 하나라도 거짓이면 submit 거절
		return false;
	}
} // inCheck

</script>
</head>
<body>
<h2> Spring MVC2 Password Update </h2><br>
<div align="center">
<b> 새로운 비밀번호 입력 </b><br><br>
<form action="pwUpdate" method="post">
<table>
	<tr height="40">
		<td bgcolor="LightCyan"><label>Password</label></td>
		<td><input type="password" id="password" name="password"><br>
			<span id="pMessage" class="eMessage"></span>
		</td>
	</tr>
	<tr height="40">
		<td bgcolor="LightCyan"><label> 재확인</label></td>
		<td><input type="password" id="password2" placeholder="반드시 입력하세요"><br>
			<span id="p2Message" class="eMessage"></span>
		</td>
	</tr>
	<tr height="40">
		<td></td>
		<td><input type="submit" value="수정" id="submitTag" onclick="return inCheck()">&nbsp;&nbsp;
			<input type="reset" value="취소">
		</td>
	</tr>
</table>
</form>
</div>
<hr>
<c:if test="${!empty requestScope.message}">
=> ${requestScope.message}	
</c:if>
<hr>
&nbsp;<a href="/home">Home</a>&nbsp;
&nbsp;<a href='javascript:history.go(-1)'>이전으로</a>&nbsp;

</body>
</html>