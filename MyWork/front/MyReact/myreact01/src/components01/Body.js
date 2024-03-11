// ** useRef (Reference)
// => DOM 요소를 직접 제어 할 수 있음.
//    ( DOM 노드, 엘리먼트, 리액트 컴포넌트의 주소값 참조 가능 ) 

// => useRef는 상태 값을 참조하되 그게 렌더링을 일으키지는 않게 하기 위해 사용하는 리액트 훅

// => current 속성을 가지고 있는 객체를 반환. 
//    인자로 넘어온 초깃값을  이 current 속성에 할당하며 이 속성은 값을 변경하여도
//    리액트 컴포넌트는 리렌더링 되지 않으며,
//    리액트 컴포넌트가 리렌더링 되는 경우도 이 속성의 값을 잃지 않음. 

// => ref 는 렌더링 중에 읽거나 쓰려고 할 경우 순수기능을 잃고 예상치 못한 결과를 낼 수도 있어서
//    event handler 에서 주로 사용함.

// => 입력폼 초기화, 포커스하기 등에 사용

// ** 리액트 훅 (HOOK)
// => 클래스 컴포넌트가 가지고 있던 유용한 기능을 
//    함수컴포넌트에서도 사용가능하도록 개발하여 제공하는 기능들
//    use~~ 로 명명됨 (useEffect, useContext, useReducer, useCallBack, useMemo 등)
//    HOOK(갈고리) : 클래스 기능을 낚아채듯 가져와 사용한다데서 유래..

// ** 생명 주기 메소드
// => 클래스 컴포넌트 에서 사용가능 (LifecycleExample 로 확인)
// => https://blog.naver.com/ssery6507/222714907042

// => 함수 컴포넌트에서는 Hooks (useEffect) 를 이용해서 처리가능
// => 실습순서 : 카운터 앱 만들기, useEffect Test

import { useRef, useState } from 'react';
import './Body.css';

function Body() { 

  const [text, setText] = useState("");
  const textRef = useRef();
  // => 인자(매개변수, Parameter) 로 전달된값을 초기값으로하는
  //    Ref 객체를 생성하고 textRef 에 저장
  // => input Tag 에서 ref={textRef} 명령으로 textRef 가 DOM 입력폼에 접근하도록 설정
  //    그러면 textRef 를 이용하여 입력폼을 직접 조작할 수 있음. 

  const onChangeText = (e) => { setText(e.target.value) } 
  const onClickBtn = () => {  
    // test1) 클릭후 입력폼 초기화에 적용
    //alert(text);
    //textRef.current.value="";

    // test2) 포커스 적용
    // => 입력된 text 가 3글자 미만 이면 포커스가 머문 상태로 입력 기다림
    if ( text.length < 3 ) {
      textRef.current.focus();
    }else {
      alert(text);
      textRef.current.value="";
    }
  } //onClickBtn 

  // ** 컴포넌트의 Update 확인
  console.log('** Body 컴포넌트 Update !');
  return (
    <div className='body'>
        <h2>** Body : useRef(Reference) Test **</h2>
        <input ref={textRef} value={text} onChange={onChangeText}/>
        <button onClick={onClickBtn}>완료</button>
    </div>   

  ); //return 
  
  
} //Body

export default Body;