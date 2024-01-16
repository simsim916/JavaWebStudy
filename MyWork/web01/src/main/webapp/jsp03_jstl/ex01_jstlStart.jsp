<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** JSTL Start **</title>
</head>
<body>
<h2>** JSTL Start **</h2>
<pre><b>
=> Jstl Library 를 정의 (현재문서_page 가 인식할 수 있도록)
	디렉티브 taglib 에 uri=".." prefix=".."
1. 출력 : out tag
=> Java의 out객체, 표현식, EL역할
<c:out value="~~ HELLO JSTL !!! 한글~~" /> 

2. 변수 정의
<c:set value="홍길동"  var="name"/>
<c:set value="22" var="age" />

3. 변수 출력 (out tag, EL)
=> JSTL 의 out tag
*name = <c:out value="${name}이름" />
*age = <c:out value="${age}" />

=> EL
*name = ${name}
*age * 100 = ${age*100}

=> Java 는 Jstl 변수와 호환되는가? : 안됨
<%-- * name = <%=name%> -> 안됨 --%>

4. 연산적용
<c:set value="${age+age}"  var="add"/>
\${add} = ${add}
<c:set value="${name==age}"  var="bool" />
\${bool} = ${bool}
<c:set value="${age>add ? age : add}" var="max"/>
\${max} = ${max}

5. 변수삭제
=> remove
<c:remove var="add"/>
\${empty_add} = ${empty add}

=> 정의하지않은 변수 삭제 : 오류발생하지 않음
<c:remove var="abcd"/>
\${empty_abcd} = ${empty abcd}

6. 우선순위
=> Jstl 변수 와 pageScope Attribute
<% pageContext.setAttribute("name","가가가"); %>
*name = ${name}
c:set value="홍길동"  var="name"
<c:set value="홍길동"  var="name"/>
*name = ${name}


</b></pre>
</body>
</html>