// ** State
// => 값을 저장하거나 변경 할 수 있는 객체로 이벤트와 함께 주로 사용됨.
//    - 즉, 버튼 클릭시 버튼의 컬러를 변경할때 등에 사용됨 
//    - 이벤트 발생 -> 이로인하여 화면의 리랜더링이 필요한 경우 리랜더링이 자동으로실행될 수 있도록 해줌
//      -> State변수 로 지정된 변수의 값에 변화가 일어나면 리액트 에서는 리랜더링 해줌  
// => useState 생성자함수로 State 생성
//    const [text_State변수, setText_set함수] = useState("초기값");
// => useState 를 호출하면 현재상태값과 이 State변수의 값을 변경하는 set함수를 담은 배열을 return.
// => 이후 State변수 값이 변하면 이를 반영하기위해 컴포넌트를 리랜더링 함.
//    ( 이것을 컴포넌트의 Update 라함 )

// ** State 로 사용자 입력 관리하기
// => 사용자가 Text 를 입력할때마다 console 출력하기 
// => 과제 "-" 버튼 만들기
//    - 최소값은 0 : alert 경고창 출력   
//    - 최대값은 100 : alert 경고창 출력, 0 으로 초기화

import { useState } from 'react';
import './Body.css';

function Body() { 
  // ** state Test1
  // => test 1) state변수 count 를 출력
  const [count, setCount] = useState(0) ;
  const onIncrease = () => {
    if (count>19) {
      alert('!! 용량 초과 $$$$ !!') ;
      setCount(0);
    }else {
      console.log('state_count Test => '+count);
      setCount(count+1); 

    }
  } //onIncrease
  const onDecrease = () => {
    if (count<1) {
      alert('!! 최소값 입니다~ !!') ;
      setCount(0);
    }else {
      setCount(count-1);
      console.log('state_count Test => '+count);
    }
  } //onDecrease

  // ** state Test2
  // => count2를 1씩 증가 시키면서, 5회 증가하면
  //    setCount(count+5) 호출 , 리랜더링 하도록 수정
  // => 이벤트를 호출하고 변수값이 변경되어도 state 변수값이 변경되지않으면,
  //    랜더링 되지않음을 Test 해본다. 
  let count2=0; 
  const onIncrease2 = () => {
    if (count>19) {
      alert('!! 용량 초과 $$$$ !!') ;
      setCount(0);
      count2=0;
    }else {
      count2+=1;  // state변수인 count 값에는 영향을 주지 않음
      console.log('state_count Test => '+count2);
      //test2 : count2 의 값 5의배수 일때 setCount() 호출
      if (count2%5==0) setCount(count+5) ; 
    }
  } //onIncrease
  const onDecrease2 = () => {
    if (count<1) {
      alert('!! 최소값 입니다~ !!') ;
      setCount(0);
      count2=0;
    }else {
      count2-=1;  
      console.log('state_count Test => '+count2);
      //test2 : count2 의 값 5의배수 일때 setCount() 호출
      if (count2%5==0) setCount(count-5) ; 
    }
  } //onDecrease
  // =====================================================
  // ** state Test3 : 다양한 input Tag
  // => text ( textarea 도 동일함, value 속성값으로 전달 )
  const [text, setText] = useState('');
  const textChange = (e) => {
    setText(e.target.value);
  } //textChange
  // => date
  const [date, setDate] = useState('');
  const dateChange = (e) => {
    console.log('** Date => '+e.target.value);
    setDate(e.target.value);
  }
  // => select
  // -> html 과 차이점
  //    selected 대신 value를 사용해 기본값 할당. (아래코드에서는 애플)
  //    선택된 option 값을 가져오려면, onChange를 사용해야하며,
  //    option 의 컨텐츠가 select 의 value 에 전달됨.
  //    ( select Tag 에 value 속성을 정의하지 않아도 전달됨 )

  // => props 로 option을 동적으로 만드는경우
  //    props 로 전달받은 Data_list 를 이용해 option 을 만드는 경우에는
  //    option 의 key, value 속성을 이용함
  //    ( https://blog.toycrane.xyz/react에서-select-box-컴포넌트-만들기-a20e2bf082b2 )

  const [option, setOption] = useState('애플');
  const jobChange = (e) => {
    console.log('** select : value='+e.target.value);
    setOption(e.target.value);
  }

  // ** 컴포넌트의 Update 확인
  console.log('** 컴포넌트의 Update');
  return (
    <div className='body'>
      <h1>** Body **</h1>
      <p>** State Test **</p>
      <button onClick={onIncrease}>+</button>
      <span>count={count}</span>
      <button onClick={onDecrease}>-</button>
      <div>
        <input value={text} onChange={textChange} />
        <span>{text}</span>
        <p>[연습] input Tag 의 type date 활용해서 값이 변경될때 마다 그값을 console 로 출력하기</p>
        <input type='date' value={date} onChange={dateChange} />
        <select onChange={jobChange} value={option}>
          <option>구글</option>
          <option>애플</option>
          <option>네이버</option>
          <option>카카오</option>
          <option>MS</option>
        </select>
      </div>
    </div>
  ); //return
  
} //Body
export default Body;