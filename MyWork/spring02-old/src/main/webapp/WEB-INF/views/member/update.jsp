<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>|| Update ||</title>

</head>
<body>
<h2>** Update Test **</h2>
<c:set var="d" value="${requestScope.mDetail}" />
<form action="update" method="post" enctype="multipart/form-data">
<table>
	<tr height="40"><td bgcolor="MediumPurple"><label for="id">아이디</label></td>
		<td><input type="text" id="id" name="id" value="${d.id}" readonly></td>
	</tr>
	
	<%-- PasswordEncoder 적용후 분리함 
	<tr height="40"><td bgcolor="MediumPurple"><label for="password">비밀번호</label></td>
		<td><input type="text" id="password" name="password" value="${d.password}"></td>
	</tr> --%>
	<tr height="40"><td bgcolor="MediumPurple"><label for="name">이름</label></td>
		<td><input type="text" id="name" name="name" value="${d.name}" ></td>
	</tr>
	<tr height="40"><td bgcolor="MediumPurple"><label for="age">나이</label></td>
		<td><input type="text" id="age" name="age" value="${d.age}"></td>
	</tr>
	<tr height="40"><td bgcolor="MediumPurple"><label for="jno">조번호</label></td>
		<td><select name="jno" id="jno">
			<option value="1" ${d.jno==1? "selected" : ""}>1조 : BusinessTeam</option>
			<option value="2" ${d.jno==2? "selected" : ""}>2조 : Static</option>
			<option value="3" ${d.jno==3? "selected" : ""}>3조 : 칭찬해조</option>
			<option value="4" ${d.jno==4? "selected" : ""}>4조 : 카톡으로얘기하조</option>
			<option value="7" ${d.jno==7? "selected" : ""}>7조 : 칠면조(관리팀)</option>
		</select></td>
	</tr>
	<tr height="40"><td bgcolor="MediumPurple"><label for="info">자기소개</label></td>
		<td><input type="text" id="info" name="info" value="${d.info}"></td>
	</tr>
	<tr height="40"><td bgcolor="MediumPurple"><label for="point">포인트</label></td>
		<td><input type="text" id="point" name="point"  value="${d.point}"></td>
	</tr>
	<tr height="40"><td bgcolor="MediumPurple"><label for="birthday">생일</label></td>
		<td><input type="date" id="birthday" name="birthday" value="${d.birthday}"></td>
	</tr>
	<tr height="40"><td bgcolor="MediumPurple"><label for="rid">추천인</label></td>
		<td><input type="text" id="rid" name="rid" value="${d.rid}"></td>
	</tr>
	<!-- Image Update 추가 
         => form Tag : method, enctype 확인
         => new Image 를 선택하는 경우 -> uploadfilef 사용
         => new Image 를 선택하지않는 경우 
            -> 본래 Image 를 사용 -> uploadfile 값이 필요함
   -->   
	<tr height="40"><td bgcolor="uploadfilef"><label for="rid">내 이미지</label></td>
		<td><img class="select_img" alt="MyImage" width="80" src="/spring02/resources/uploadImages/${d.uploadfile}">
		<input type="file" id="uploadfilef" name="uploadfilef" size=20></td>
		<input type="hidden" id="uploadfile" name="uploadfile" value="${d.uploadfile}"></td>
	</tr>
	<script>
        document.getElementById('uploadfilef').onchange=function(e){
         if(this.files && this.files[0]) {
            let reader = new FileReader;
            reader.readAsDataURL(this.files[0]);
             reader.onload = function(e) {
                // => jQuery를 사용하지 않는경우 
                document.getElementsByClassName('select_img')[0].src=e.target.result;
                
               //$(".select_img").attr("src", e.target.result)
               //            .width(70).height(90); 
               } // onload_function
          } // if   
        }; //change  
      </script>
	<tr height="30"><td></td>
		<td><input type="submit" value="수정">&nbsp;&nbsp;
			<input type="reset" value="취소">
		</td>
	</tr>
</table>
&nbsp;<a href="pwUpdate">비밀번호 수정</a>&nbsp;
<c:if test="${!empty requestScope.message}">
	회원가입 실패~
</c:if>
<a href="/web02/home.jsp">Home</a>
<a href="javascript:history.go(-1)">이전으로</a>
</body>
</html>