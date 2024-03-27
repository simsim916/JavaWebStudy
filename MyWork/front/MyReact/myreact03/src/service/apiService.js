import { API_BASE_URL } from "./app-config";
// => 필요시 사용 
import axios from "axios";

// 1. axios 요청 함수 
// => 요청시 필요한 정보를 매개변수로 전달받음
// => ACCESS_TOKEN 도 매개변수로 전달함

export async function apiCall(url, method, requestData, token) {

  // 1.1) headers & token
  // => indexOf('join')
  //  - JavasScript 의 문자열 확인함수
  //  - 존재하면 찾는문자열이 첫번째 나타나는 위치(index) 를 return,
  //    없으면 -1 dmf return
  let headers = ''; 
  if (url.indexOf('join') >= 0  && token == null) {
      headers = { 'Content-Type': 'multipart/form-data' };  
  }else if (token !== null) {
      headers = { 'Content-Type': 'application/json',
                  'Authorization': 'Bearer '+token  };  
  }else {
      headers = { 'Content-Type': 'application/json' };  
  }

  // 1.2) axios 전송 options
  let options = {
      url: API_BASE_URL + url,
      method: method, 
      headers: headers,
  };
  // => 전송할 Data 가 있으면 추가할수 있도록 "," 추가함    
  
  // 1.3) 전송 Data(requestData) 있는 경우 data 속성 추가
  if (requestData) {
    options.data = requestData;
  }

  console.log(`** apiCall options.method=${options.method}`);
  console.log(`** apiCall options.url=${options.url}`);
  console.log(`** apiCall options.data=${options.headers}`);
  alert(`** apiCall 전달된 Data 확인: options.data=${options.data}`);
  // => 전달된 options 값들 확인

  // 2. Axios 요청
  return axios(options)
          .then(response => { 
              return response.data;
          }).catch(err => {
              console.error(`** apiCall Error status=${err.response.status}, message=${err.message}`); 

            /* => 403 오류 처리
            //    한군데서 처리하면 좋겠지만 
            //    요청에 따라 오류가 이중으로 처리될 수 있으므로 현재는 보류 
              if (err.response.status===403) {
                alert(`** Server Reject : 로그인후 다시 하세요 => ${err.response.status}`);
                window.location.href = "/login";
              } 
              */
              return Promise.reject(err.response.status);
              //return Promise.reject(err);
              // => Promise.reject(err) 의 인자 err 은 객체형이 아니고, 문자형 단일값만 
              //    전달하기때문에 필요한 정보 (err.response.status) 만 전달함 
        }); //catch
} //apiCall
/*
** status 403 처리 
if (err.response.status===403) {
  alert(`** Server Reject : 로그인후 다시 하세요 => ${err.response.status}`);
  navigate("/login");
} else return Promise.reject(err.response.status);
*/
/*
** 로컬 스토리지 Local Storage 
=> 브라우저를 닫아도 남아있음.
     window.localStorage에 위치함. 키 밸류 저장소이기 때문에 키와 밸류를 순서대로 저장하면 된다
     지속적으로 필요한 데이터 (자동 로그인을 위한 사용자 정보)는 로컬 스토리지에 저장. 
     그러나 비밀번호와 같은 중요 정보는 절대저장해서는 안됨 ( 클라이언트에 저장하는 것이므로 보안취약 )
​= > 메소드
	- localStorage.setItem(키, 값) : 로컬 스토리지에 저장
	- localStorage.getItem(키) : 로컬 스토리지에서 해당 키의 값 조회
	- localStorage.removeItem(키) : 로컬 스토리지에 해당 키가 지워짐
	- localStorage.clear( ) : 로컬 스토리지 전체가 비워짐

​** 세션 스토리지 Session Storage 
=> 브라우저를 닫으면 사라짐
     window.sessionStorage에 위치 
     잠깐 필요한 일회성 정보는 세션 스토리지에 저장
=> 메서드
	- sessionStorage.setItem(키, 값) :  스토리지에 저장
	- sessionStorage.getItem(키) :  스토리지에서 해당 키의 값 조회
	- sessionStorage.removeItem(키) : 스토리지에 해당 키가 지워짐
	- sessionStorage.clear( ) : 스토리지 전체가 비워짐
*/
  
// ** Login
// => token이 존재하면 로컬 스토리지에 저장
// => id, password 오류부분 미작성
// export async function signin(userDTO) {
//   return apiCall("/auth/signin", "POST", userDTO).then((response) => {
//     if (response.token) {
//         console.log('** response => '+response);
//         // => response에 token이 존재하는 경우
//         alert('** Token => ' + response.token);  // 확인

//         // => 로컬 스토리지에 토큰 저장
//         //localStorage.setItem(ACCESS_TOKEN, response.token);
//          // => 세션 스토리지에 토큰 저장
//         sessionStorage.setItem(ACCESS_TOKEN, response.token);

//         // Todo(첫) 화면으로 리디렉트
//         window.location.href = "/";
//     }
//   });
// }

// // ** Logout
// // => 로컬 스토리지에서 token을 제거 하고 loginForm 으로
// export function signout() {
//     //localStorage.setItem(ACCESS_TOKEN, null);  // 로컬 스토리지 클리어
//     sessionStorage.setItem(ACCESS_TOKEN, null);  // 세션 스토리지 클리어
//   window.location.href = "/login";
// }
// // ** SignUp
// export function signup(userDTO) {
//   return apiCall("/auth/signup", "POST", userDTO);
// }