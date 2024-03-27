  // "proxy": "http://localhost:8088",

//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// ** Final Project 실습
// => 실습폴더: SpringBoot 프로젝트 /src/main 

// => react 프로젝트 생성 	
// - npx create-react-app myreact03
// - VS_code 에서 생성안되면, 폴더에서 직접 생성 

// => 프로젝트 폴더로 이동후 ( cd reactp01 )
// - npm install react-router-dom
// - npm install axios

// => 프로젝트 생성 확인
// npm start

// => CORS 위한 Proxy 설정
// package.json (22행 근처, "scripts":... 아랫쪽) 에 아래 Proxy 설정 추가 

// --------------------------------------
// "scripts": {
//     "start": "react-scripts start",
//     "build": "react-scripts build",
//     "test": "react-scripts test",
//     "eject": "react-scripts eject"
//   },
// "proxy":"http://localhost:8080",
// --------------------------------------

// => Github 연동시 문제발생하는경우
// - .git 파일 중복으로 인한 발생
// - 프론트 프로젝트의 .git 파일을 삭제 한 후 올려본다.

// ===============================================================
// *** Full Stack 프로젝트 작성시 고려사항
// => CORS 
// => JWT ( JSON Web Token )
// JSON 포맷으로 사용자에 대한 속성을 저장하는 Claim 기반의 Web Token을 의미함.
// JWT는 Token 자체를 정보로 사용하는 Self-Contained 방식으로 정보를 안전하게 전달함.
// 사용자 정보에 JWT를 이용하면 Client는 자신의 정보를 보는 것 만 가능하고 수정은 불가능하며, 
// 수정은 반드시 Server를 통해서만 가능. 
// 그러므로 인증이나 정보 전달에 사용됩니다. (session  인증 대신 사용) 

// *** SOP & CORS
// => 웹 생태계에는 다른 출처로의 리소스 요청을 제한하는 것과 관련된 두 가지 정책이 존재한다. 
//      CORS 와  SOP(Same-Origin Policy)이다.

// => SOP(Same-Origin Policy)
//  SOP는 지난 2011년, RFC 6454에서 처음 등장한 보안 정책으로 
//  말 그대로 “같은 출처에서만 리소스를 공유할 수 있다”라는 규칙을 가진 정책이다.
//  그러나 웹이라는 오픈스페이스 환경에서 다른 출처에 있는 리소스를 가져와서
//  사용하는 일은 굉장히 흔한 일이라 무작정 막을 수도 없는 노릇이니
//  몇 가지 예외 조항을 두고 이 조항에 해당하는 리소스 요청은 출처가 다르더라도 허용하기로 했는데,
//  그 중 하나가 “CORS 정책을 지킨 리소스 요청”이다.

// => CORS(Cross-Origin Resource Sharing)
// - 교차(다른) 출처 리소스 공유 
//   교차 출처 리소스(자원 ) 공유는 웹 페이지 상의 제한된 리소스를 
//   최초 자원이 서비스된 도메인 밖의 다른 도메인으로부터 요청할 수 있게 허용하는 구조.
//   CORS는 교차 출처 요청을 허용하는 것이 안전한지 아닌지를 판별하기 위해
//   브라우저와 서버가 상호 통신하는 하나의 방법을 정의하여 허용.
//   이렇게 함으로 동일 출처 요청보다 더 많은 기능을 허용하면서
//   모든 교차 출처 요청을 허용하는 것보다 더 안전하다.
//   CORS의 사양은 원래 W3C 권고안으로 배포되었으나, 현재는 CORS를 재정의하면서
//   WHATWG의 Fetch Living Standard 사양이 유지보수 되고있다.

// - no-cors : 교차(다른) 출처 리소스 공유 제한 (정책, 규정)  
// - 출처 (Origin) : Protocol,  Host, 포트 번호를 합친 것으로  서버를 찾아가기 위한 가장기본적인 주소
//~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

import './App.css';
import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { apiCall } from './service/apiService';

//import { Route, Routes, Link, NavLink } from 'react-router-dom';
import Header from './components/Header';
import Footer from './components/Footer';
import Main from './components/Main';

function App() {

  const navigate = useNavigate();
  const [isLoggedIn, setIsLoggedIn] = useState(false);  // 로그인 상태 저장 변수
  const [loginInfo, setLoginInfo] = useState(""); // 회원 로그인 정보
  
  // 1. 로그인 확인
  // => 브라우져의 sessionStorage에서 로그인정보 확인
  if ( !isLoggedIn ) {
    const loginCheck =JSON.parse(sessionStorage.getItem("loginInfo"));
    //if (loginCheck.token !== null) {  -> token 적용이후 확인
    if (loginCheck !== null) {  // token 적용이전 확인
      alert(`** sessionStorage 로그인 확인 username=${loginCheck.username}`);
      setIsLoggedIn(true);
      setLoginInfo(loginCheck);
    } 
  } 

  // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
  // 2. 로그인 함수
  const onLoginSubmit = (userId, userPassword) => {

    let url = "/user/login";
    const data = { id:userId, password:userPassword };

    apiCall(url, 'POST', data, null)
    .then((response) => {
        // => 로그인 성공
        //  -> 브라우져의 sessionStorage에 로그인정보 보관 (JSON 포맷으로), 
        //     로그인상태값 과 loginInfo상태값  set  
        //  -> apiCall 함수 에서는 response.data 값을 return 함.
        sessionStorage.setItem("loginInfo", JSON.stringify(response));
        alert('로그인 성공');
        setIsLoggedIn(true);
        setLoginInfo(response);
        navigate("/");
    }).catch((err) => {
        setIsLoggedIn(false);
        setLoginInfo('');
        if (err===502) { alert("id 또는 password 가 다릅니다, 다시하세요 ~~");
        }else { alert(`** onLoginSubmit 시스템 오류, err=${err}`); }
        navigate("/login");
    }); //apiCall

  }; //onLoginSubmit

  // 3. 로그아웃
  const onLogout = () => {
    let url = "/auth/logout";
    alert(`** 로그아웃 token 확인 => ${loginInfo.token}`);
    apiCall(url, 'GET', null, loginInfo.token)
    .then((response) => {
        // => 로그인아웃 성공
        //  -> 브라우져의 sessionStorage에 로그인정보 삭제, 
        //     로그인상태값 과 userName 초기화  
        sessionStorage.removeItem("loginInfo");
        alert('로그아웃 성공');
        setIsLoggedIn(false);
        setLoginInfo('');
    }).catch((err) => {
        //setIsLoggedIn(false);
        if (err===502) { alert("로그 아웃 실패, 다시하세요 ~~");
        }else { alert(`** onLogout 시스템 오류, err=${err}`); }
    }); //apiCall
    navigate("/");
  }; //onLogout

  return (
    <div className="App">
      <Header userName={loginInfo.username} isLoggedIn={isLoggedIn} onLogout={onLogout} />
      <Main token={loginInfo.token}
            onLoginSubmit={onLoginSubmit}
      />
      <Footer />
    </div>
  ); //return
}

export default App;
