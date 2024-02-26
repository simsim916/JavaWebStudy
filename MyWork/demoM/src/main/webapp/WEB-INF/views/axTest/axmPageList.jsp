<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** axios BoardPageList **</title>
<link rel="stylesheet" type="text/css" 
	  href="/resources/myLib/myStyle.css">	  
<script>

//** 검색 & 페이징 포함한 요청의 Ajax 처리
//=> Ajax 요청 function 작성, url 을 매개변수로 전달 : axiMListCri(url) 
//=> Page 요청 : aTag -> span 으로 변경하고 function 으로 처리 
//=> Check 검색은 submit 을 사용하기 때문에 적용하지 않음(주석처리)

//=> Ajax 처리시에는 문서내부의 function이 인식 안되므로
// searchDB(), keywordClear() 모두 axTest03.js 에 작성  

'use strict'





// ** Board Check List
function checkClear() {
	let ck = document.querySelectorAll('.clear');
	for(let i = 0; i < ck.length; i++) {
		ck[i].checked = false;
	}
	
	return false; // reset 버튼의 기본이벤트를 제거해줘야 한다.
}

</script>
</head>
<body>
<h2>** Spring MVC2 BoardPageList **</h2>
<hr>
<c:if test="${!empty requestScope.message}">
 ${requestScope.message}<br><hr>
</c:if>
<hr>

<div id="searchBar">
	<select name="searchType" id="searchType" onchange="keywordClear()">
		<option value="all" ${pageMaker.cri.searchType == 'all' ? 'selected' : ""}>전체</option>
		<option value="id" ${pageMaker.cri.searchType == 'id' ? 'selected' : ""}>id</option>
		<option value="name" ${pageMaker.cri.searchType == 'name' ? 'selected' : ""}>name</option>
		<option value="age" ${pageMaker.cri.searchType == 'age' ? 'selected' : ""}>age</option>
		<option value="jno" ${pageMaker.cri.searchType == 'jno' ? 'selected' : ""}>jno</option>
		<option value="nj" ${pageMaker.cri.searchType == 'nj' ? 'selected' : ""}>name or jno</option>
	</select>
	<input type="text" name="keyword" id="keyword" value="${pageMaker.cri.keyword}">
	<button id="searchBtn" onclick="searchDB()">Search</button>
	<hr>
	
	
	<!-- ** CheckBox Test -->
<form action="mCheckList" method="get">
        <b>Jno : </b>
          <c:set var="ck1" value="false" />
          <c:set var="ck2" value="false" />
          <c:set var="ck3" value="false" />
          <c:set var="ck4" value="false" />
          <c:set var="ck5" value="false" />
      <c:forEach  var="id" items="${pageMaker.cri.check}" >
         <c:if test="${jno=='1'}"> <c:set var="ck1" value="true" /> </c:if>
         <c:if test="${jno=='2'}"> <c:set var="ck2" value="true" /> </c:if>
         <c:if test="${jno=='3'}"> <c:set var="ck3" value="true" /> </c:if>
         <c:if test="${jno=='4'}"> <c:set var="ck4" value="true" /> </c:if>
         <c:if test="${jno=='7'}"> <c:set var="ck5" value="true" /> </c:if>
      </c:forEach>
      <!-- ------------------------------------------------------------------------ -->
        <input type="checkbox" name="check" class="clear" value="1" ${ck1 ? 'checked' : ''}>Business&nbsp;
        <input type="checkbox" name="check" class="clear" value="2" ${ck2 ? 'checked' : ''}>static&nbsp;
        <input type="checkbox" name="check" class="clear" value="3" ${ck3 ? 'checked' : ''}>칭찬해조&nbsp;
        <input type="checkbox" name="check" class="clear" value="4" ${ck4 ? 'checked' : ''}>카톡으로얘기하조&nbsp;
        <input type="checkbox" name="check" class="clear" value="7" ${ck5 ? 'checked' : ''}>칠면조&nbsp;
        <input type="submit" value="Search">&nbsp;
        <input type="reset" value="Clear" onclick="return checkClear()"> <br>
    </form>
<hr>
</div>


<table style="width:100%">
<tr bgcolor="#7ba5f0" >
	<th>id</th><th>name</th><th>age</th><th>jno</th><th>info</th><th>point</th><th>birthday</th><th>rid</th><th>image</th>
</tr>
<c:if test="${!empty requestScope.banana}">
	<c:forEach var="b" items="${requestScope.banana}">
		<tr>
		<td>${b.id}</td>
		<td>${b.name}</td>
		<td>${b.age}</td>
		<td>${b.jno}</td>
		<td>${b.info}</td>
		<td>${b.point}</td>
		<td>${b.birthday}</td>
		<td>${b.rid}</td>
		<td><img alt="이미지" src="/resources/images/${b.uploadfile}"></td>
		</tr>	
	</c:forEach>
</c:if>
<c:if test="${empty requestScope.banana}">
	<tr><td colspan="5">~~ 출력자료가 1건도 없습니다. ~~ </td>
	</tr>
</c:if>
</table>
<hr>
<div align="center">
<!-- ** Paging Block ** 
	=> ver01: QueryString 수동 입력 -> 자동생성 makeQuery 메서드 적용
	=> ver02: makeQuery메서드 -> searchQuery메서드 로 변경
 	 1) FirstPage, Prev  
 	 => 수동입력(Old)
 	 	<a href="bPageList?currPage=1&rowsPerPage=5">FP</a>&nbsp;
		<a href="bPageList?currPage=${pageMaker.spageNo-1}&rowsPerPage=5">&LT;</a>&nbsp;&nbsp;
	 => 자동생성(makeQuery)
 	 -->
  <c:choose>
	<c:when test="${pageMaker.prev && pageMaker.spageNo>1}">
	<!-- ver01 : 자동생성(makeQuery) 
		<a href="bPageList${pageMaker.makeQuery(1)}">FP</a>&nbsp;
		<a href="bPageList${pageMaker.makeQuery(pageMaker.spageNo-1)}">&LT;</a>&nbsp;&nbsp;
	-->
	<!-- ver02 : searchQuery 메서드 사용 -->
		<a href="${pageMaker.searchQuery(1)}">FP</a>&nbsp;
		<a href="${pageMaker.searchQuery(pageMaker.spageNo-1)}">&LT;</a>&nbsp;&nbsp;
	</c:when>	
	<c:otherwise>
		<font color="Gray">FP&nbsp;&LT;&nbsp;&nbsp;</font>
	</c:otherwise>
  </c:choose> 	 
<!-- 2) Display PageNo 
	=> currPage 제외한 PageNo 만 a Tag 적용 -->
  <c:forEach var="i" begin="${pageMaker.spageNo}" end="${pageMaker.epageNo}">
  	<c:if test="${i==pageMaker.cri.currPage}">
  		<font color="Orange" size="5"><b>${i}</b></font>&nbsp;
  	</c:if>
  	<c:if test="${i!=pageMaker.cri.currPage}">
  		<a href="mPageList${pageMaker.searchQuery(i)}">${i}</a>&nbsp;
  	</c:if>
  </c:forEach>
<!-- 3) Next, LastPage  -->
  <c:choose>
  	<c:when test="${pageMaker.next && pageMaker.epageNo>0}">
  		&nbsp;<a href="mPageList${pageMaker.searchQuery(pageMaker.epageNo+1)}">&GT;</a>
  		&nbsp;<a href="mPageList${pageMaker.searchQuery(pageMaker.lastPageNo)}">LP</a>
  	</c:when>
  	<c:otherwise>
  		<font color="Gray">&nbsp;&GT;&nbsp;LP</font>
  	</c:otherwise>
  </c:choose>
</div>
<hr>
<!-- 로그인 한 경우에만 새글등록 할 수 있도록 -->  
<c:if test="${not empty sessionScope.loginID}">
	&nbsp;<a href="boardInsert">새글등록</a>&nbsp;
</c:if>
&nbsp;<a href="/home">Home</a>&nbsp;
&nbsp;<a href="javascript:history.go(-1)">이전으로</a>&nbsp;
</body>
</html>