import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';

// ** ReactDOM.createRoot
// => React 18에서는 기존에 사용하던 ReactDOM.render 대신 ReactDOM.createRoot가 도입
//    인수로 전달된 요소를 리액트앱의 root로 만들어 return 함.
// => Root : React에서 Root란 렌더 트리 DOM (리액트 엘리먼트돔) 의 가장 최상위 Root 요소.
// => 이전방법과 차이: 18 버전부터는 Root를 생성하고 Root에서 render 함수를 호출한다.

// ** root.render(...)
// => 인수로 전달된 컴포넌트를 돔 Root 에 추가함.

// ** ReactDOM.render (18 이전방법)
// => React 코드를 DOM에 붙이는 역할이며
//    Root를 DOM 노드를 통해 접근하기 때문에 사용자에게 노출되지 않으며
//    Root를 생성할 때 container를 넘겨주는 형태로 사용하기 때문에 
//    container에 변경이 없더라도 계속해서 DOM에 접근해야 한다.

// ** React.StrictMode
// => 없어도 동작에는 상관없으며, console.log 가 두번 연속해서 나타나는 현상 발생
// => 점검사항 (아래의 상황에 도움됨)
//  - 안전하지 않은 생명주기를 사용하는 컴포넌트 발견
//  - 레거시 문자열 ref 사용에 대한 경고 
//  - 권장되지 않는 findDOMNode 사용에 대한 경고
//  - 예상치 못한 부작용 검사
//  - 레거시 context API 검사
//  - Ensuring(보장) reusable state

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
