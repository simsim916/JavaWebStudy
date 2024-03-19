
// ** Context (문맥)
//    글이 지닌 방향성, 글(문장)이 궁극적으로 전달하려는 뜻 이나 목적.

// ** 리액트 Context
// => 같은 Context(문맥, 동일목적) 하에 있는 컴포넌트 그룹에 데이터를 공급한다는 의미로 
//    리액트 컴포넌트 트리 전체를 대상으로 데이터를 공급하는 기능이며,  
//    Props Drilling 문제를 해결할 수 있음.
// => Props Drilling 문제 란?
//    리액트 컴포넌트의 계층구조에서 컴포넌트간에 Data를 전달할때 Props를 사용.
//    이 Props는 부모에서 자식으로 단방향으로만 전달 가능하기 떄문에,
//    2단계이상 떨어져있는 컴포넌트에 Data 를 전달하려면,
//    직접 전달할수 없기 때문에 경로상의 모든 컴포넌트에 Props를 전달해야하는데,
//    이것을 드릴로 땅을 파내려가는것과 같다고하여 붙여진 명칭이다.   

// ** Context API
// => 리액트 Context 를 만들고 다루는 기능
// => Context 생성 (컴포넌트 밖에): React.createContext    
// => Context 에 Data 공급 : Context.Provider (Context 객체에 포함된 컴포넌트) 
//    - 컴포넌트 내부에 배치
//    - Context를 구독하는 컴포넌트들에게 context의 변화를 알리는 역할.
//    - value prop 값을 받아 하위의 컴포넌트에게 전달.
//    - 값의 갯수는 제한이 없고, 이를 전달 받을 수 있는 컴포넌트 수의 제한도 없다.
//    - Provider 하위에서 context를 구독하는 모든 컴포넌트는 Provider의 value가 바뀔 때마다 리렌더링됨.
// => Context가 공급하는 Data 사용하기
//    - useContext(Context)
//      인자는 Data를 공급할 Context 이고, 
//      이 Context 가 제공하는 Data를 return 함.  

// ** 실습 순서
// => App04 : Context 적용 1단계
//  -> TodoItem의 React.memo 적용안됨 확인
//  -> 수정하지않은 Item 까지 모두 출력됨(랜더링됨)
// => App05 : Context 적용 2단계 (Context 용도별 분리적용)
//  -> TodoItem의 React.memo 적용됨 확인
//  -> 수정한 Item 만 출력됨(랜더링됨)

//=================================================

// ** TodoList (일정관리 앱) 리팩토링 4.
// => 리팩토링: 제공하는 기능은 변하지 않으면서 내부구조를 개선하는 작업  
// => Context 적용 1.
//    Props 로 onUpdate, onDelete 를 TodoItem 컴포넌트로 전달하는 과정에서
//    발생하는 Props Drilling 문제를 해결  

import './App.css';
import Header from './components/Header';
import TodoEditor from './components04/TodoEditor';
import TodoList from './components04/TodoList';
import React, { useReducer, useState, useRef } from "react";
import { useCallback } from 'react';

// ** Mock Data
const mockTodo = [
{ id: 0,
  isDone: false,
  content: 'React 공부하기',
  createDate: new Date().getTime()
},
{ id: 1,
  isDone: true,
  content: 'JavaScript 공부하기',
  createDate: new Date().getTime()
},
{ id: 2,
  isDone: false,
  content: 'Java 공부하기',
  createDate: new Date().getTime()
},
{ id: 3,
  isDone: false,
  content: 'MySQL 예습하기',
  createDate: new Date().getTime()
},
{ id: 4,
  isDone: false,
  content: 'Spring 예습하기',
  createDate: new Date().getTime()
}
]
// ** useReducer 적용
function reducer(state, action) {
  switch (action.type) {
    case "Create" : { return [action.newItem, ...state] }
    case "Update" : { 
        return state.map( (it) => 
        it.id === action.targetId ? 
        { ...it, isDone: !it.isDone } : it );
      }
    case "Delete" : {  
        return state.filter( (it) => it.id !== action.targetId );
      }
    default: return state;
  } ; //switch
} //reducer

// ** Context 적용 1단계
// 1) Context 생성
// => 반드시 컴포넌트 밖에 생성해야 컴포넌트가 리랜더링 될때마다 재생성 되지않음.
// => 외부문서 (Context에 소속된 컴포넌트들) 에서 인식 가능 하도록 export 해야함.
// => React.createContext(default_value)
//    이때 인자 default_value 는 생성시 적절한 Provider를 찾지못했을때만 사용되는값 
export const TodoContext = React.createContext();

function App() {
  
  // ** useReducer 로
  const [todo, dispatch] = useReducer(reducer, mockTodo);
  const idRef = useRef(mockTodo.length);

  // ** 일정추가 (Create) 함수 생성
  const onCreate = (content) => {
    dispatch({ type:"Create",
               newItem: {
                  id: idRef.current,
                  content: content,
                  isDone: false,
                  createDate: new Date().getTime()
               }
    }); //dispatch
    idRef.current +=1;
  }; //onCreate ( useCallback 을 적용하지않음 )

  // ** 일정 수정
  const onUpdate = useCallback( (targetId) => {
      dispatch({ type:"Update", targetId }); //dispatch   
    }, [] );

  // ** 일정 삭제 
  const onDelete = useCallback( (targetId) => {
    dispatch({ type:"Delete", targetId }); //dispatch 
  }, [] );

  console.log("** App Update !! **");
  return (
    <div className="App">
      <Header />
      {/* 
      2) Provider 배치
        => Props로 Data를 공급받아야하는 컴포넌트들을 Provider 로 감싼다.
        => Provider에 공급할 Data 를 전달(설정)
           value Props 에 공급하려는 모든 Data 를 객체로 담는다.  
        => Provider 내부의 (Context에 소속된) 컴포넌트들은 별도로 Props를 받을 필요가 없으므로 
           기존의 Props는 제거한다.   
      3) Data 사용하기
        => Data를 공급받는 컴포넌트에서는 useContext() 를 이용해 사용가능.  
        */} 
      <TodoContext.Provider value={{ todo, onCreate, onUpdate, onDelete }}>
        <TodoEditor />
        <TodoList />
      </TodoContext.Provider>
    </div>
  );
}

export default App;
