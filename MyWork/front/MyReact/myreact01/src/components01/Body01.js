// ** JSX 컴포넌트 기본규칙
// => 컴포넌트명은 대문자로 시작 (1컴포넌트 1화일, 대부분 컴포넌트명 과 화일명 동일)
// => {JS 표현식} : 기본자료형, 산술식,..객체, 배열 등
// => 단, 컨텐츠 또는 값(리터럴) 을 표현하는 위치에서
//    객체, 배열명 직접사용은 불허
// => class속성은 class가 JS 예약어 이므로 className 으로 사용
// => 모든 Tag 는 닫힘 규칙
// => 최상위 Tag 규칙 (필요시 <div> 또는 <React.Fragment> Tag 로 감싸줌)
// => 조건부 랜더링 : 삼항식({} 내에서 가능), 조건문(JSX 에서는 사용불가능)

// 2) Css, 스타일 적용하기
// => 인라인 스타일링 : style={{스타일...}}
//    HTML 의경우 <h1 style="color:black; backgroundColor:Orange">
// => 스타일파일 분리
//  -> 일반 css 화일
//    - Body.css (컴포넌트화일명 과 동일)  ,
//    - import './Body.css' : 컴포넌트 imnport 와 달리 real_File_path 만 명시함
//  -> Sass (syntactically awesome stylesheets, 사스)
//    - 자주 사용되는 CSS 전처리기 (pre-processor) 중 하나임.
//    - 확장된 CSS 문법을 사용하여 CSS 코드를 쉽게 작성 할 수 있도록 해주며
//    - 코드의 재활용성과 가독성을 높여줌
//    - 확장자 .scss  .sass 를 지원하고 이 둘은 문법의 차이가 있으며
//       .scss 문법이 더 일반적임.
//    - 주요 차이점 : .sass 는 중괄호와 {} 와 세미콜론 ;  을 지원하지 않음
//    - 여러 파일에서 사용되는 내용은 따로 분리하여 관리
//      (utils.scss 라는 파일명 주로 사용됨)

// 3) 클래스명 중복 방지 를 위한 이름 규칙 (Naming Rule)
// => App.css 참고 하면  컴포넌트명-클래스명 형식으로 작성
// => BEM Naming : BEM 규칙을 준주하며 용도를 명확하게 포함하여 작성
//    - BEM (Block , Element, Modifier)
//    - Block: 가장 큰 단위로 독립적인 개체를 의미
//    - Element: Block을 구성하는 단위로 Block에 속하며 BEM의 Element는 중첩하지 않는다.
//    - Modifier: 상태를 뜻하는데, 특정한 행동 또는 동작을 의미.

// ** Props, 컴포넌트에 값 전달하기
// => Props(Properties)객체 : 부모에서 자식으로 값 전달
// => 그러므로 Body 컴포넌트에 Props 로 값을 전달하기위해서는
//    App 컴포넌트에서 전달해야함. ( name 값을 Body 로 전달)

// ** React Event (Html 과 차이점) 
// => 이벤트 리스너 카멜표기  
// => 콜백함수처럼 함수(이벤트 핸들러) 그자체를 전달
// => onClick={onClickHandler}
// => 기본이벤트 제거 ( return false 대신 e.preventDefault() 명시적으로 호출해야함 )

// ** 이벤트객체 활용 실습

// ** State

import './Body.css';
import img1 from "../images/aaa.gif";
import React from 'react';

function Body(props) {
  // => 부모로부터 전달받는 매개변수명은 자유롭게 사용가능하지만,
  //    일반적으로 props 로 사용한다.

  let n1 = 11, n2 = 20;
  let s1 = '안녕하세요~~', s2 = 'React & JSX';
  let b1 = true, b2 = false;
  let obj = { id: 'banana', name: '바나나' };

  //=====================================
  // ** 부모로부터 전달된 props 확인 
  //console.log(`** Body, props=${props}`);
  // ** 매개변수 구조분해 적용
  const { name, country } = props;

  // ** 이벤트 핸들러
  // 비교 : function clickTest() {...}
  const clickTest = (e) => {
    alert('** Event Test ... 안녕하세요 => ' + e.target.name);
    console.log(`** clickTest e 객체 확인 => ${e.type}`);
  }

  // ** 조건문: jSx 에서는 사용불가능하지만 컴포넌트에서는 사용가능 
  if (b2) {
    return (
      <div className='body'>
        <h1>** Body : JSX Test **</h1>
        <p>* props.name={props.name}, props.country={props.country}</p>
        <p>* props 구조분해 적용 name={name}, country={country}</p>
        <p>* 산술식: n1+n2={n1 + n2}</p>
        <p>* 문자식: s1+s2={s1 + s2}</p>
        <p>* 논리식_Test: b1={b1}, AND={b2 && b2}, 관계식={n1 == n2}</p>
        {/* <p>* Object: obj={obj} </p>
        => Error : Uncaught Error: Objects are not valid as a React child  
        => 컨텐츠 위치의 { } 내에서 객체, 배열명 직접사용은 불허  */}
        <p>* Object: obj.id={obj.id}, obj.name={obj.name}</p>
        <p>삼항식으로 n1+n2 의 값이 짝수이면 '짝수' 아니면 '홀수' 출력하기</p>
        <p>정답 {n1 + n2} 는 {(n1 + n2) % 2 == 0 ? '짝수' : '홀수'} 입니다.</p>
      </div>
    ); //return
  } else {
    return (
      <React.Fragment>
        {/* react 를 import 해야함 */}
        <div>
          <h1 style={{ color: 'black', backgroundColor: 'Orange' }} >** Body : JSX Test**</h1>
          <p> if 조건문 Test 중 .....</p>
          <img src={img1} alt={'imageTest'} style={{ width: 300, height: 200 }} />
          <button onClick={clickTest} name='apple'>Apple 클릭하세요!</button>
          <button onClick={clickTest} name='banana'>Banana 클릭하세요!</button>
          <p>** HTML 과 비교 : onclick = clickTest()</p>
        </div>
        <div>'최상위 Tag 규칙 : React.Fragment Tag 로 감싸줌, 랜더링 되지 않으므로 div 대신 사용'</div>
      </React.Fragment>
    );
  } //if
} //Body
export default Body;