<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title> updateForm </title>
	<link rel="stylesheet" type="text/css" 
	href="/spring02/resources/myLib/writeBoard.css">
</head>
<body>
<c:set var="b" value="${ requestScope.apple }"></c:set>
   <!-- 컨테이너 -->
   <div class="container">
      <!-- 전체 마진, 패딩 -->
      <div class="content">
         <!-- 컨텐츠 wrap -->
         <!-- <form action=".https://lastteamproject.web.app/news_n_event/"></form> -->
      <h2 style="font-size:400%;">** 글 수정 페이지 **</h2>
      <br>
         <div class="boardWriteForm">
         <form action="update" method="post">
            <div class="board_wrap">
               <!-- 제목영역 -->
               <div class="title_wrap">
                  <div class="title">
                     <strong>제목</strong>
                  </div>
                  <div class="board_category">
                     <div class="title_input_box">
                        <input name="title" value="${ b.title }" type="text">
                     </div>
                  </div>
               </div>
               <!-- 본문영역 -->
               <div class="main_text_wrap">
                  <div class="tool_bar_top">
                     <div class="list_n_aline_tool_wrap">
                        <div class="list_tool_wrap">
                        </div>
                     </div>
                  </div>
                  <div class="main_text_line"></div>
                  <div class="main_text">
                     <div class="main_text_body">
                        <textarea name="content" id="main_text" cols="30" rows="10">${ b.content }</textarea>
                     </div>
                  </div>
                  <div class="tool_bar_bottom">
                     <span class="letter_num">글번호 : <input type="text" name="seq" value="${ b.seq }" readonly></span> &nbsp;&nbsp;&nbsp;
                     <span class="letter_num">작성자 : <input type="text" name="id" value="${ b.id }" readonly></span> &nbsp;&nbsp;&nbsp;
                     <span class="letter_num">작성 시간 : <input type="text" name="regdate" value="${ b.regdate }" readonly></span>
                     <span class="letter_num"><input type="hidden" name="cnt" value="${ b.cnt }" readonly></span>
                  </div>
               </div>
               <!-- 등록/취소 영역 -->
            </div>
            <c:if test="${sessionScope.loginID eq b.id}">
               <div class="push_cancel_wrap">
                  <input type="submit" id="push_button" value="적용">
                  <input type="reset" id="cancel_button" style="color:black;">
               </div>
            </c:if>
            </form>
            <br>
               <div class="push_cancel_wrap">
                  <a href="boardList" id="list_button">글목록</a>
               </div>
            </div>
         </div>
      </div>
</body>
</html>