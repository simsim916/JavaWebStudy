import './App.css';
import Home from "./pages/Home";
import Topics from "./pages/Topics";
import Contact from "./pages/Contact";
import { Route, Routes, Link, NavLink } from 'react-router-dom';

// ** Routing
// => 경로를 지정하는 과정

// ** Page Routing
// => 요청에 따라 적절한 페이지를 반환하는 과정
// => 이때 웹 페이지를 어디서 만드느냐에 따라 
//    SSR(Server Side Rendering), CSR(Client Side Rendering) 로 나뉘며,
//    리액트는 SPA(Single Page Application) 이며 CSR 방식을 채택하고 있다.
// => 두 방법 모두 장단점이 있으므로 서비스의 목적에 따라 적절한 방식을 채택한다.
// => CSR 은 처음접속시 Html 과 JS 에플리케이션을 함께 제공받기 때문에 
//    처음접속은 느리지만, 이후 페이지 이동은 브러우저에서 교체하므로 훨씬 빠르다.  

// ** SPA 에서 Page Routing
// => 마치 Page가 이동하는것처럼 사용자가 요청한 URL에 따라 해당 URL에 맞는 
//    페이지를 보여주는 것으로 실제는 적당한 컴포넌트가 배치 되도록 해줌

// ** React Router 
// => 리액트에서는 라우팅 관련 라이브러리가 많이 있지만, 가장 많이 사용되는것이 
//    리액트 라우터(React Router) 이다.
// => 서버로부터 신규 페이지를 불러오지 않는 상황에서 각각의 url에 의해 선택된 Page를
//    하나의 페이지에서 렌더링 해주는 라이브러리 
// => 사용자가 입력한 주소를 감지하는 역할을 하며,
//    다양한 환경에서 동작할 수 있도록 여러 종류의 라우터 컴포넌트를 제공함.
//    이중 대표적인 최상위 라우터 컴포넌트는 BrowserRouter 와 HashRouter
//   (아래 3.1 과 3.4 참고)
//      <Routes> , <Route>
// => https://goddaehee.tistory.com/305 참고

// ** React Router 적용하기
// 1. 프로젝트 root 경로에 리액트 라우터 설치
// => npm install react-router-dom
// => package.json 으로 버전확인 ( 6.x.x 인지 )
// => 구버전 제거 : npm uninstall react-router-dom
// => 최신버전 재설치 : npm install react-router-dom@6

// ** 버전 6 달라진점
// => Switch -> Routes
// => path 매칭 규칙
//    앞부분만 일치(exact 옵션사용) -> 정확히 일치 (exact 옵션사용불가)

// 2. Project 폴더 구성
// => src -> components, pages, images

// 3. 실습
// => 먼저 index.js에서 최상위 컴포넌트인 App 을 
//    BrowserRouter, HashRouter 등의 Rapper 컴포넌트로 감싸준다.

// 3.1) index.js 의 App 을 BrowserRouter 컴포넌트로 감싸기 
// => BrowserRouter
//  -> Router 를 적용하려는 최상위 컴포넌트를 감싸는 Rapper 컴포넌트
//  -> HTML5를 지원하는 브라우저의 주소 변경을 감지하며 컴포넌트가
//    페이지를 구성하고 이동하는데 필요한 다양한 기능 제공

// 3.2) Routes, Route 컴포넌트로 url 요청에 의한 랜더링 영역 지정하기
// => Routes: Route 컴포넌트들을 감싸며 ( 6 이전버전의 Switch 가 변경됨)
// => Route : path, element_path에 해당하는 컴포넌트

// 3.3) Page 이동 적용하기
// 3.3.1) a_href
// => page가 리로드(새로고침) 됨 
// => 즉, 리랜더링되며 useState 등으로 메모리상에 구축해놓은
//    모든 상태값들이 초기화됨.    

// 3.3.2) Link_to
// => Page가 리로드 되지않도록 해줌 (SPA 구현에 적합)
// => Page 가 새로고침 되지않으며 url만 변경됨 

// 3.3.3) NavLink_to
// => 사용자가 어느 페이지에 위치하는지 알 수 있도록 해줌
// => 개발자도구 elements Tab 에서 확인해보면 아래 style이 적용된 
//    <li> 에 class="active" 속성이 추가되어있음 확인가능
// => App.css 에 아래코드 추가후 확인
//      .active {
//          background-color: tomato;
//          text-decoration: none;
//       }

// 3.4) HashRouter 컴포넌트
// => 해시 주소를 감지하며 url 에 # 을 추가해 어떤 Path 에서 접근 하더라도
//    동일한 웹Page 를 제공할 수 있도록 해줌
// => BrowserRouter 와 비교해본다 
//  ( BrowserRouter: 주소창의 url 에 의해 page 변경 
//    HashRouter: 반드시 link 를 클릭해야 page 변경 )

// 3.5) Parameter(:id) 와 useParams() & Nested Routing Test
// => Topics.jsx

// 3.6) Parameter(쿼리스트링) 와 useSearchParams() & useLocation() Test
// => Contact.jsx

// =============================================================
function App() {

    console.log('** App Update !!! **');

    return (
        <div className="App">
            <h2>** React Router Dom Test **</h2>
            {/*
            ** 적용전  
            <Home />
            <Topics />
            <Contact />
            */}

            {/*
              ** Router 적용 실습 
                => 하단에 Routes 영역 정의
                3.3.1) a_href : page가 리로드(새로고침) 됨 
        */}
            <ul>
                <li><NavLink to='/'>Home</NavLink></li>
                <li><NavLink to='/topics'>Topics</NavLink></li>
                <li><NavLink to='/contact'>Contact</NavLink></li>
            </ul>

            <Routes>
                <Route path='/' element={<Home />} />
                <Route path='/topics/*' element={<Topics />} />
                <Route path='/contact/*' element={<Contact />} />
                <Route path='/*' element={"정의 되지 않은 요청입니다."} />
            </Routes>

        </div>

    )
}

export default App;