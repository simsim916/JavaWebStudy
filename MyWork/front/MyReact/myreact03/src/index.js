import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import { BrowserRouter } from "react-router-dom";

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  //<React.StrictMode>

//** BrowserRouter 컴포넌트
// => Router 적용하려는 최상위 컴포넌트를 감싸는 Rapper 컴포넌트
// => 브라우저의 주소 변경을 감지하며 컴포넌트가 페이지를 구성하고 이동하는데 필요한 다양한 기능 제공
  <BrowserRouter>
    <App />
  </BrowserRouter>
  //</React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
