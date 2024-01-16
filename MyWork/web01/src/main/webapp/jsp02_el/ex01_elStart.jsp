<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** EL 기본사항 **</title>
</head>
<body>
<h2>** EL 기본사항 **</h2>
<% String name = "홍길동"; %>
<pre>

=> EL : Expression Language , 표현언어
=> 편리한 값(Value) 의 출력과 사용

1. 값 (변수의 값) 의 출력 비교
=> Java 표현식 : <%=name%>
=> Java out 객체 : <% out.print("Out객체"); %>
=> EL 출력 : ${"EL을 이용한 출력, 표현언어"}
	-> Java 변수 출력 : \${"EL로 Java변수출력"+name} : 500Error
	-> EL 내부에서는 Java 변수 사용 불가	
	-> JSTL 과 병행해서 사용됨
<hr><b>

2. EL Test
** EL 자료형 **
정수형 : ${123}
실수형 : ${10.123}
문자형 : ${"안녕하세요"}
논리형 : ${true}
null : ${null}

** EL 연산 **
=> 산술 연산
\${4+5} => ${4+5}
\${7/3} => ${7/3}
-> 나누기 결과는 실수형

=> 관계(비교) 연산
gt : greater than / lt : less than
ge : greater equal / le : less equal
eq : equal, == / ne : not equal, !=
\${4>5}=>${4>5} 
\${4gt5}=>${4 gt 5} 
=> 논리(집합) 연산 : &&, ||
\${5>2 && 10>20} => ${5>2 && 10>20}
\${5>2 || 10>20} => ${5>2 || 10>20}

=> 조건(삼항) 식
\${5>2? 5 : 2}=> ${5>2? 5 : 2}

3. 기타
** Java 변수
=> Java 표현식 : <%=name%>
=> \${name} => ${name} 
	-> 자바변수 출력 X, JSTL로 정의한 변수는 출력
	-> name 의 값이 없음을 확인
	-> \${empty_name} => ${empty name}
   
   <!-- 
    => empty : 검사할 객체가 비어있는지 확인 
            비어있으면 true 
            list, map 타입의 객체가 값이 있는지 없는지 구분해줌  
    => EL 에 자바변수는 직접 값을 전달하지 못함
     (jsp에서 자바코드가 완전 분리됨을 목표로 하기때문에 자바변수를 사용할 필요는 없으므로)  
    => EL 에 변수명이 오면 JSTL로 정의한 변수 또는 속성(Attribute) 의 이름으로 인식함.              
    -->
** request 객체의 Parameter 처리
=> request 객체의 Parameter를 전달하는 el의 내부객체 제공 : param
=> 퀴리스트링으로 id 지정 전.후 Test : ~/web01/jsp02_el/ex01_elStart.jsp?id=banana 
 -> Java 표현식 <%=request.getParameter("id")%>
 \${empty_param.id } => ${empty param.id }
 \${param["id"]} => ${param["id"]} 
 \${empty_param.id ? false : param.id} => ${empty param.id ? false : param.id}

** getAttribute 처리
=> ex02
</b></pre>

</body>
</html>