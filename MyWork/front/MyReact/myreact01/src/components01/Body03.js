// ** State 로 여러개의 사용자 입력 관리하기

import { useState } from 'react';
import './Body.css';

function Body() { 
  // ** state Test1 : input Tag 개별적으로 관리
  // => 코드가 길어지고 비효율적
  /*
  const [name, setName] = useState("") ;
  const [gender, setGender] = useState("") ;
  const [birth, setBirth] = useState("") ;
  const [info, setInfo] = useState("") ;

  const onChangeName = (e) => { setName(e.target.value) };
  const onChangeGender = (e) => { setGender(e.target.value) };
  const onChangeBirth = (e) => { setBirth(e.target.value) };
  const onChangeInfo = (e) => { setInfo(e.target.value) };
  
  // ** 컴포넌트의 Update 확인
  console.log('** 컴포넌트의 Update');
  return (
    <div className='body'>
      <h2>** Body State Test1 : 개별적으로 관리 **</h2>
      <div>
        <input value={name} onChange={onChangeName} placeholder='이름' />
      </div>
      <div> 
        <select value={gender} onChange={onChangeGender} >
          <option>성별</option>
          <option>남성</option>
          <option>여성</option>
        </select>
      </div>
      <div>  
        <input type='date' value={birth} onChange={onChangeBirth} />
      </div>
      <div>  
        <textarea value={info} onChange={onChangeInfo} />
      </div>
    </div>
  ); //return
  */
  // ** state Test2 : input Tag를 객체화해서 하나의 State로 관리
  // => 코드가 간결해지고 효율적  
  // => 수정사항
//    - State 객체 생성, 이벤트핸들러 1개로 통일
//    - 엘리먼트들의 value 속성값, name 속성 추가, 이벤트핸들러


  const [state, setState] = useState({
    name:"", gender:"", birth:"",info:""
  });

  const onChangeState = (e) => {
    console.log('수정대상: '+e.target.name);
    console.log('수정 값 : '+e.target.value);
    setState({
      ...state,
      [e.target.name]:e.target.value
      // 참고 name:"", gender:"", birth:"",[info]:e.target.value
    });
  } //onChangeState
  // => setState에 새로운 객체 전달
  //    스프레드로 기존객체 stat 값 나열
  //    객체 괄호표기법으로 name 속성을 key로 e.target.value 를 value 로 저장
  //    (객체 괄호표기법: 속성명을 괄호('[]')로 감싸서 표현

  // ** 컴포넌트의 Update 확인
  console.log('** 컴포넌트의 Update');
  return (
    <div className='body'>
      <h2>** Body State Test2 : 객체로 관리 **</h2>
      <div>
        <input name='name' value={state.name} onChange={onChangeState} placeholder='이름' />
      </div>
      <div> 
        <select name='gender' value={state.gender} onChange={onChangeState} >
          <option>성별</option>
          <option>남성</option>
          <option>여성</option>
        </select>
      </div>
      <div>  
        <input type='date' name='birth' value={state.birth} onChange={onChangeState} />
      </div>
      <div>  
        <textarea name='info' value={state.info} onChange={onChangeState} />
      </div>
    </div>
  ); //return
} //Body

export default Body;