<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** BoardPage List **</title>
</head>
<body>
<h2>** BoardPage List **</h2>
	<table style="width: 100%">
	
	<tr bgcolor="Khaki">
		<th>seq</th>
		<th>id</th>
		<th>title</th>
		<th>content</th>
		<th>regdate</th>
		<th>조회수!</th>
	</tr>
	<c:if test="${!empty sessionScope.loginId }">
		<c:forEach items="${ requestScope.banana}" var="list">
			<tr>
				<td>${list.seq }</td>
				<!-- 답글 등록 후 Title 출력 전에 들여쓰기 추가  -->
				<c:if test="${list.indent>0}">
					<c:forEach begin="1" end="${list.indent}">
						<span>&nbsp;&nbsp;</span>
					</c:forEach>
					<span style="color:blue;">re..</span>
				</c:if>
				<!-- 로그인 한 경우에만 글 내용 볼 수 있도록 -->
				<c:if test="${!empty loginId}"></c:if>
					<a href="detail?seq=${list.seq}">${list.title}</a>
				<c:if test="${empty loginId }">
				${list.title}
				</c:if>
				<td><a href="detail?seq=${list.seq}">${list.title }</a></td>
				<td>${list.content }</td>
				<td>${list.regdate }</td>
				<td>${list.cnt }</td>
			</tr>
		</c:forEach>
	</c:if>
	<c:if test="${empty sessionScope.loginId }">
		<h2>로그인 하지 않으면 못 본다</h2>
	</c:if>
	</table>
	<hr>
	<div align="center">
<!-- ** Paging Block ** 
   => ver01: QueryString 수동 입력 -> 자동생성
     1) FirstPage, Prev
     => old   
     		<a href="bPageList?currPage=1&rowsPerPage=5">FP</a>&nbsp;
     		<a href="bPageList?currPage=${pageMaker.spageNo-1}&rowsPerPage=5">&LT;</a>&nbsp;&nbsp;
     -->
     <c:choose>
     	<c:when test="${pageMaker.prev && pageMaker.spageNo > 1}">
     	<!-- ver01 : makeQuery 메서드 적용 -->
     		<a href="bPageList${pageMaker.makeQuery(1)}">FP</a>&nbsp;
     		<a href="bPageList${pageMaker.makeQuery(pageMaker.spageNo-1)}">&LT;</a>&nbsp;&nbsp;
     	</c:when>
     	<c:otherwise>
     		<font color="gray">FP &nbsp; &LT; &nbsp;&nbsp;</font>
     	</c:otherwise>
     </c:choose>
     
     
     
<!-- 2) Display PageNo 
	=> currPage 제외한 PageNo a태그 붙이기
-->
     <c:forEach var="i" begin="${pageMaker.spageNo}" end="${pageMaker.epageNo}">
     	<c:if test="${i == pageMaker.cri.currPage}">
			<font color="orange" size=5><b>${i}</b></font>			     		
     	</c:if>
     	<c:if test="${i != pageMaker.cri.currPage}">
     		<a href="bPageList${pageMaker.makeQuery(i)}">${i}</a>&nbsp;&nbsp;
     	</c:if>
     </c:forEach>


<!-- 3) Next, LastPage 
      => ver01: makeQuery() 메서드 사용
      => ver02: searchQuery() 메서드 사용 -->
     <c:choose>
     	<c:when test="${pageMaker.next && pageMaker.epageNo > 0}">
     	<!-- && 뒤 조건절은 이상한 페이지번호가 넘어올걸 방지하기 위한 코드 -->
     		&nbsp;<a href="bPageList${pageMaker.makeQuery(pageMaker.spageNo+1)}">&GT;</a>&nbsp;&nbsp;
     		<a href="bPageList${pageMaker.makeQuery(pageMaker.lastPageNo)}">LP</a>&nbsp;
     	</c:when>
     	<c:otherwise>
     		<font color="gray">&nbsp;&GT;&nbsp;EP</font>
     	</c:otherwise>
     </c:choose>

	</div>
&nbsp;<a href="/spring02/home">HOME</a>&nbsp;
&nbsp;<a href="boardInsert">게시글등록</a>&nbsp;
&nbsp;<a href="javascript:history.go(-1)">이전으로</a>&nbsp;
</body>
</html>