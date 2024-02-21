<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>JoinForm</title>
<link rel="stylesheet" type="text/css" href="/spring02/resources/myLib/myStyle.css">
<Script src="/spring02/resources/myLib/inCheck.js"></Script>
<!--  ** FileUpLoad Form **
=> form 과 table Tag 사용시 주의사항 : form 내부에 table 사용해야함
   -> form 단위작업시 인식안됨
   -> JQ 의 serialize, FormData 의 append all 등 

=> method="Post" : 255 byte 이상 대용량 전송 가능 하므로

=> <form enctype="속성값">
   <form> 태그의 데이터 (input 의 value)가 서버로 제출될때 해당 데이터가 인코딩되는 방법을 명시함.  
 
=> enctype="multipart/form-data" : 화일 upload 를 가능하게 해줌 
   ** multipart/form-data는 파일업로드가 있는 입력양식요소에 사용되는 enctype 속성의 값중 하나이고, 
       multipart는 폼데이터가 여러 부분으로 나뉘어 서버로 전송되는 것을 의미
       이 폼이 제출될 때 이 형식을 서버에 알려주며, 
       multipart/form-data로 지정이 되어 있어야 서버에서 정상적으로 데이터를 처리할 수 있다.     
-->


<script>
"use strict"

// ** ID 중복확인
// => UI 개선사항
// => 중복확인 버튼 추가
//    처음 : 중복확인버튼_enable / submit_disable
// => 중복확인 완료후 submit 이 가능하도록
//    중복확인버튼_disable / submit_enable
// => 중복확인 기능 : function idDupCheck()
//    id입력값의 무결성점검 -> id 확인요청 -> 서버로 전송 -> id , selectOne 결과 -> response: 사용가능/불가능 
// => 서버측 : 컨트롤러에 idDupCheck 요청을 처리하는 매핑메서드, view_Page(팝업창) 작성   

function idDupCheck(){
	
	// 1) id입력값의 무결성점검
	if(!iCheck){
		iCheck=idCheck();
	}else {
	// 2) 서버로 id 확인요청 -> 결과는 팝업창
		let url="idDupCheck?id="+document.getElementById('id').value;
		window.open(url,'_blank','width=400,height=300,resizable=yes,scrollbars=yes,toolbar=no,menubar=yes')
	}
}

/* --------------------------------------------------*/
// ** 화살표 함수
// => 익명함수를 간단하게 표기
//    function(){....}  
//    () => {....}  

// ** 입력값의 무결성 점검
// => ID 중복확인, 무결성 점검

// 1) 모든항목  focusout 이벤트핸들러
//    => 개별항목 점검확인하는 boolean Type 변수 (스위치변수) 
//    => 개별항목 점검 function() 작성
// 2) submit 진행전에 점검확인
//    => 모든항목의 점검이 완료된 경우에만  submit 진행
//    => function inCheck() 로 확인
//    => submit 버튼의 onclick 리스너에 등록
//       ( submit 의 default 이벤트 고려 )
// ----------------------------------------------------
// ** 실습

// 입력값에 대한 무결성 확인
// 1 대상이 확인됐는지 결과 확인을 위해 switch 전역변수 정의
	let iCheck = false;
	let pCheck = false;
	let p2Check = false;
	let nCheck = false;
	let aCheck = false; // age가 정수인지 확인
	let oCheck = false; // point가 실수인지 확인
	let bCheck = false;

// 2 개별적인 코드 확인
// 이벤트 : focusout, keydown - EnterKey 적용
// 오류가 없으면 switch 변수 값을 true로 변경, ErrorMessage 삭제
// 오류가 있으면 switch 변수 값을 false로 변경, ErrorMessage 출력
// 순서 : Tag 인식 -> Tag의 value 가져오기 -> 무결성 확인

   // => window.onload : window는 생략가능
   // => body 의 Tag 들을 인식가능한 상태일때 실행 되도록하기위함. 
onload = function(){
	document.getElementById('id').focus();
	
	// ID
	   // -> keydown_EnterKey 에 포커스이동 적용
	   // -> 제어문자의 ascii 코드 값(참고)
	   //     esc=27, EnterKey=13, Space_Bar=32
	document.getElementById('id').addEventListener('keydown', (e) => {
		if(e.which == 13){
			// form에서는 enter누르면 자동 submit발생
			// 이를 제거하기 위해 preventDefault 이용
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
			document.getElementById('name').focus();
		} // if
	});
	document.getElementById('password2').addEventListener('focusout', () => {
		p2Check=pw2Check();	
	});
	
	// name
	document.getElementById('name').addEventListener('keydown', (e) => {
		if(e.which == 13){
			e.preventDefault();
			document.getElementById('age').focus();
		} // if
	});
	document.getElementById('name').addEventListener('focusout', () => {
		nCheck=nmCheck();	
	});
	
	// age
	document.getElementById('age').addEventListener('keydown', (e) => {
		if(e.which == 13){
			e.preventDefault();
			document.getElementById('jno').focus();
		} // if
	});
	document.getElementById('age').addEventListener('focusout', () => {
		aCheck=agCheck();	
	});
	
	// jno
	document.getElementById('jno').addEventListener('keydown', (e) => {
		if(e.which == 13){
			e.preventDefault();
			document.getElementById('info').focus();
		} // if
	});
	
	// info
		document.getElementById('info').addEventListener('keydown', (e) => {
		if(e.which == 13){
			e.preventDefault();
			document.getElementById('point').focus();
		} // if
	});
	
	// point
	document.getElementById('point').addEventListener('keydown', (e) => {
		if(e.which == 13){
			e.preventDefault();
			document.getElementById('birthday').focus();
		} // if
	});
	document.getElementById('point').addEventListener('focusout', () => {
		oCheck=poCheck();	
	});
	
	// birthday
	document.getElementById('birthday').addEventListener('keydown', (e) => {
		if(e.which == 13){
			e.preventDefault();
			document.getElementById('rid').focus();
		} // if
	});
	document.getElementById('birthday').addEventListener('focusout', () => {
		bCheck=bdCheck();	
	});
	
	// rid
	document.getElementById('rid').addEventListener('keydown', (e) => {
		if(e.which == 13){
			e.preventDefault();
			document.getElementById('submitTag').focus();
		} // if
	});
} // onload

// 3) submit 실행 여부 판단 & 실행
// => 모든항목의 무결성을 확인
// => 오류가 없으면 : return true
// => 오류가 1항목이라도 있으면 : return false
function inCheck(){
	if(!iCheck){ document.getElementById('iMessage').innerHTML = ' 필수입력, id를 확인하세요 '}
	if(!pCheck){ document.getElementById('pMessage').innerHTML = ' 필수입력, password를 확인하세요 '}
	if(!p2Check){ document.getElementById('p2Message').innerHTML = ' 필수입력, password2를 확인하세요 '}
	if(!nCheck){ document.getElementById('nMessage').innerHTML = ' 필수입력, name을 확인하세요 '}
	if(!aCheck){ document.getElementById('aMessage').innerHTML = ' 필수입력, age를 확인하세요 '}
	if(!oCheck){ document.getElementById('oMessage').innerHTML = ' 필수입력, point를 확인하세요 '}
	if(!bCheck){ document.getElementById('bMessage').innerHTML = ' 필수입력, birthday를 확인하세요 '}
	// submit 확인
	if(iCheck && pCheck && p2Check && nCheck && aCheck && oCheck && bCheck){
		if(confirm("정말 가입하겠습니까? (Yes:확인/No:취소)")){
			// submit 진행
			return true;
		} else{
			alert("가입이 취소됐")
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
	<form action="join" method="post" enctype="multipart/form-data">
		<table>
			<tr height="40">
				<td bgcolor="MediumPurple"><label for="id"> I D </label></td>
				<td><input type="text" name="id" id="id" placeholder="영문과 숫자로 4-10글자" size="20"> <br> <span id="iMessage" class="eMessage"></span></td>
				<button type="button" id="idDup" onclick="idDupCheck()"  >ID중복 확인</button>
				
			</tr>
			<tr height="40">
				<td bgcolor="MediumPurple"><label for="password"> PASSWORD </label></td>
				<td><input type="password" name="password" id="password" placeholder="특수문자 필수" size="20"> <br> <span id="pMessage" class="eMessage"></span></td>
			</tr>
			<tr height="40">
				<td bgcolor="GreenYellow"><label for="password2"> PW 확인 </label></td>
				<td><input type="password" id="password2" placeholder="PASSWORD 재입력" size="20"> <br> <span id="p2Message" class="eMessage"></span></td>
			</tr>
			<tr height="40">
				<td bgcolor="MediumPurple"><label for="name"> Name </label></td>
				<td><input type="text" name="name" id="name" size="20"> <br> <span id="nMessage" class="eMessage"></span></td>
			</tr>
			<tr height="40">
				<td bgcolor="MediumPurple"><label for="age"> Age </label></td>
				<td><input type="text" name="age" id="age" size="20"> <br> <span id="aMessage" class="eMessage"></span></td>
			</tr>
			<tr height="40">
				<td bgcolor="MediumPurple"><label for="jno"> Jno </label></td>
				<td><select name="jno" id="jno">
						<option value="1">1조 : Business</option>
						<option value="2">2조 : Static</option>
						<option value="3">3조 : 칭찬해조</option>
						<option value="4">4조 : 카톡으로얘기하조</option>
						<option value="7">7조 : 칠면조</option>
				</select></td>
			</tr>
			<tr height="40">
				<td bgcolor="MediumPurple"><label for="info"> Info </label></td>
				<td><input type="text" name="info" id="info" placeholder="자기소개 & 희망사항" size="20"></td>
			</tr>
			<tr height="40">
				<td bgcolor="MediumPurple"><label for="point"> Point </label></td>
				<td><input type="text" name="point" id="point" placeholder="실수 입력" size="20"> <br> <span id="oMessage" class="eMessage"></span></td>
			</tr>
			<tr height="40">
				<td bgcolor="MediumPurple"><label for="birthday"> Birthday </label></td>
				<td><input type="date" name="birthday" id="birthday"> <br> <span id="bMessage" class="eMessage"></span></td>
			</tr>
			<tr height="40">
				<td bgcolor="MediumPurple"><label for="rid"> 추천인 </label></td>
				<td><input type="text" name="rid" id="rid" size="20"></td>
			</tr>
			<tr height="40">
			<!-- file upload 기능추가 -->
				<td bgcolor="MediumPurple"><label for="uploadfileF"> image </label></td>
				<td>
				<img src="" class="select_img">
				<input type="file" name="uploadfilef" id="uploadfileF" size="20">
				</td>
			</tr>
			<script>
        document.getElementById('uploadfileF').onchange=function(e){
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
			<tr>
				<td></td>
				<td>
<!-- => Tag 의 onclick 이벤트를 작성하고, onclick 이벤트핸들러가 가지고있던
    기본동작인 submit 을 선택적으로 진행되도록 해준다. 
    - submit 진행 : default (또는 return true)
    - submit 정지 : submit 이벤트를 무효화 해야함 (return false 또는 이벤트.preventDefault())  -->
<!-- incheck가 true를 return하면 submit 실행 -->
         <input type="submit" id="submitTag" value="가입" onclick="return inCheck()" disabled>&nbsp;&nbsp;
<!-- Button Test
=> default : form 내부에서는  submit 와  동일하게 작동됨 
            inCheck() 의 return 값에 따라 (true 면) submit 진행됨 
=> 단, type 속성을 선택하면 (button, reset, submit 등) 속성에 맞게 실행됨
   예) button 을 선택하면 submit 은 실행되지않음
   Enter_Key : form 내부에서는 누르면 submit이 진행됨 -->
<!-- <button onclick="inCheck()">ButtonTest</button> -->
    	<input type="reset" value="취소">
				</td>
			</tr>
		</table>
	</form>
	<hr>
	<!-- <button onclick="inCheck()">ButtonTest</button> -->

	<c:if test="${!empty requestScope.message}">
=> ${requestScope.message}	
</c:if>

	<hr>
	&nbsp;
	<a href="/spring02/home">Home</a>&nbsp; &nbsp;
	<a href='javascript:history.go(-1)'>이전으로</a>&nbsp;

</body>
</html>