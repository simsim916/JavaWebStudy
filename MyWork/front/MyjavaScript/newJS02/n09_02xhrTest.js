/**
** XMLHttpRequest Test
// 1. 통신 담당 객체 준비
// => XMLHttpRequest 객체 생성
// 2. 요청을 실행
// => 클라이언트로부터 요청 이벤트 발생시 실행되는 메서드:startMethod() 작성 
//    	-> 생성된  XMLHttpRequest 객체 를 이용해서
//		      요청준비 (결과처리를 담당할 메서드를 지정) -> 요청 설정 -> 보냄
// 3. 결과처리
// => 결과처리 담당 메서드 작성
// => 응답 결과가 오면 자동 실행
 */
// ** 실습
"use strict"
// 1. XMLHttpRequest 객체 생성
let xhrObj; //XMLHttpRequest 객체의 인스턴스를 보관할 변수를 전역으로 선언
function createXHR() {
  xhrObj = new XMLHttpRequest();
} //createXHR
//2.
//2.1) Basic
function startMethod(){
  // a) 객체생성
  createXHR();
  // b) Response를 담당할 메서드를 지정 
	// => 응답처리를 위한 이 메서드는 () 없이 이름만 기술해야 한다.
  xhrObj.onreadystatechange=resultF;
  // c) 요청설정 : open()
  xhrObj.open('Get','n08_Promise01.html','true');
  // => 전송방식(Get), 요청페이지(url), 비동기적 통신이면 "true"
  // d) 요청전송
  xhrObj.send(null);
} //startMethod

// 2.2) JS Parameter_Get
//=> Make QueryString
function getParameterValues(){
	let id=document.getElementById('id').value;
	let password=document.getElementById('password').value;
	let name=document.getElementById('name').value;
	return 'id='+id+'&password='+password+'&name='+name; 
	// id=banana&password=12345!&name=바나나
}//getParameterValues

// => Get
function startMethodG( ) {
	createXHR();
	xhrObj.onreadystatechange=resultF;
	// => 요청설정
	//    parameter : url 퀴리스트링으로 처리
	let url='n09_03parameterResult.html?'+getParameterValues();
	// n09_03parameterResult.html?id=banana&password=12345!&name=바나나
	xhrObj.open('Get',url,'true');
	// => 요청전송
	xhrObj.send(null);
} //startMethodG

// => Post : DataValue 를 body 영역에 담아 전송
// => ~.html Post 요청시 Data를 처리할 수 없어 405(Method Not Allowed) 오류발생   
function startMethodP( ) {
	createXHR();
	xhrObj.onreadystatechange=resultF;
	// => 요청설정
	let url='n09_03parameterResult.html';
	xhrObj.open('Post',url,'true');
	// => 요청전송 : parameter 를 매개변수로 전달 , request Header 값 처리
	xhrObj.setRequestHeader('Content-Type','application/x-www-form-urlencoded;charset=utf-8');
	xhrObj.send(getParameterValues());
  console.log('Post send 완료');
} //startMethodP

//3.
function resultF() {
  if ( xhrObj.readyState==4 ) {
    // => 요청객체의 상태가 모든 데이터를 받을 수 있는 상태로 완료된 경우
    if ( xhrObj.status==200 ) {
      // => response 성공
      document.getElementById('resultArea').innerHTML=
      '** 서버 Response =><br>' + xhrObj.responseText;
    }else {
      document.getElementById('resultArea').innerHTML=
      '** 서버 Response =><br>' + 'ERROR, 찾을수 없음';
    } //if_200
  } //if_4
} //resultF

// => jQuery 구문과 비교해보세요 ~~
/*
$.ajax({
	  		type:'Get',  // Get/Post
	  		url:'ax03_parameterResult.jsp',
	  		data: {
	  			id:document.getElementById('id').value,
	  			password:$('#password').val(),
	  			name:$('#name').val()
	  		},
	  		success:function(result) {
	  			alert('** Ajax jQuery Test 성공 **');
	  			$('#resultArea').html('** 서버의 Response_JQuery => <br>'+result);
	  		},
	  		error:function() {
	  			alert('** Ajax jQuery Test 실패 **');
	  			$('#resultArea').html('** 서버의 Response_JQuery => Error !!!<br>');
	  		}
	  	  }); //ajax
*/
