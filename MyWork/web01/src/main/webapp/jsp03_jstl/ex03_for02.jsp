<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** JSTL forEach begin, end, step Test **</title>
</head>
<body>
<h2>** JSTL forEach begin, end, step Test **</h2>
<pre>
=> 구간반복: StartIndex(begin), LastIndex(end), 증감값(step) 적용하기
=> step 의 default 값은 1
=> 실습 1)
   1 ~ 10 까지를 다음처럼 출력하세요 ~~
   -> 1, 2, 3, .....10         
   -> java의 예 : for (int i=1; i<11; i++) { ......  }   
=> 결과
</pre>
<c:forEach var="i" begin="1" end="10" step="1" varStatus="vs">
	<span>${i}${vs.last? "" : ", "}</span>
</c:forEach>
<br>
<hr><pre>
=> 실습 2)
   1 ~ 10 중에서 짝수만, index, count 출력하기
   단, table 을 이용해서 출력하세요 ~~
   ex03_for01 의 table 과 비교해 보세요 ~~    
=> count : 반복횟수 
=> index : 배열등 index가 존재하는 경우에는 index 값을 출력
           반복자(iterator) 의 값   
           step 을 지정하지 않으면 1씩 증가     
</pre>
<table border="1" style="text-align:center; width:90%;">
   <tr>
   		<th>num</th><th>index</th><th>count</th>
   </tr>
   
   <c:forEach begin="1" end="10" step="1" varStatus="vs">
   <c:if test="${vs.index%2==0}">
   	    <tr>
   			<td>${vs.index}</td><td>${vs.index}</td><td>${vs.count}</td>
	    </tr>
   </c:if>
   </c:forEach>
</table> 
<table border="1" style="text-align:center; width:90%;">
   <tr>
   		<th>num</th><th>index</th><th>count</th>
   </tr>
   
   <c:forEach var="i" begin="2" end="10" step="2" varStatus="vs">
   <tr>
   		<td>${i}</td><td>${vs.index}</td><td>${vs.count}</td>
   </tr>
   </c:forEach>
</table> 
   
   => 실습 3) 1~30 을 다음처럼 1행에 5개씩 출력하세요~<br>
<!-- 
1,2,3,4,5
6,7,8,9,10
11,12,13,14,15
...
............30 -->
   
   <c:forEach var="i" begin="1" end="30" step="1" varStatus="vs">
   	<c:if test="${vs.count%5!=0}">${i},&nbsp;</c:if>
   	<c:if test="${vs.count%5==0}">${i}<br></c:if>
   </c:forEach>
   
   <c:forEach var="i" begin="1" end="25" step="5" varStatus="vs">
	   <c:forEach var="j" begin="${i}" end="${i+3}" step="1" varStatus="vs">
   			${j},&nbsp;
	   </c:forEach>
	   	${i+4}<br>
   </c:forEach>
</body>
</html>