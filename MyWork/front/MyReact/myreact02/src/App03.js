
// ** 최적화
// => 웹서비스의 성능을 개선하는 기술
// => 불필요하게 낭비되는 연산을 줄여 랜더링의 성능을 높여줌.

// ** 리액트의 연산 최적화 
// => 메모이제이션(Memoization) 기법
//    직역하면 메모하는 방법 으로
//    특정 입력에 대한 결과를 저장했다가, 동일한 요청이 들어오면
//    저장해둔 결과값을 제공하므로 빠르게 응답하며
//    불필요한 연산을 줄여주는 기술
// => 이러한 기능을 알고리즘에서는 Dynamic(동적) Programming (DP) 라함.
// => 리액트의 연산 최적화 기법
//  - useMemo(), useCallback(), React.memo

// ** useMemo()
// => 함수의 불필요한 재실행 방지
// => 메모이제이션(Memoization) 기법을 이용해 연산의 결과값을
//    기억해 두었다가 필요할때 사용함으로 불필요한 함수호출을 줄여주는 훅.  
// => const value = useMemo(callback, [의존성배열]);
//    의존성배열의 값이 바뀌면 callback 함수를 실행하고 결과값 return
// => TodoList 컴포넌트에 analyzeTodo 함수 추가 하고 Test

// => useEffect 와 비교하기  ( 아래 비교예제 참고 )
//  -> useEffect(callback_함수, [deps]_의존성 배열)
//    두번째 인자인 의존성 배열요소의 값이 변경되면 첫번째 인자인 콜백함수를 실행함 
//    ( 결과를 return 하지않음 )

//  -> useMemo() 와 useEffect() 차이점  
//    - useMemo는 랜더링 직전 실행, useEffect는 랜더링 직후 실행
//    - useMemo는 callback함수 결과값 return, useEffect는 결과 return 하지않음
//    - useMemo는 함수 최적화용, useEffect는 side effect (예_서버에서 Data 가져오기 등, myreact01_App03 참고) 수행

// ** useCallback()
// => 함수의 불필요한 재생성 방지
//    컴포넌트 리랜더링시 내부의 함수를 재생성하지 않도록 메모이제이션함.  
// => useCallback(callback, [의존성배열])
//    [의존성배열] 이 변경되면 callback 함수를 재생성 return 함.
// => useCallback(callback, [])
//    비어있는 배열을 전달하면 callback 함수는 마운트시에만 생성되고, 이후에는 재생성 되지않음
// => 주의사항
//    이때 callback 함수내부에서 State 변수에 접근한다면, 
//    마운트시의  State 변수값만 사용할 수있으므로 주의해야 하며,
//    이것을 해결하려면 "함수형 업데이트" 기능을 이용해야함
//   ( 단, 아래코드처럼 useReducer() 를 이용하는경우에는 무관함 )
// => 아래의 onUpdate, onDelete 함수를 리랜더링 할때마다 재생성 하지 않도록 적용.

// ** React.memo
// => 컴포넌트의 불필요한 리랜더링을 방지해주는 고차 컴포넌트 (Header 에 적용)
// => 리랜더링의 기준은 부모에서 전달된 Props가 변경된 경우에만 리랜더링됨    
// => React.memo(메모이제이션 하려는 컴포넌트)
//    인자로 전달된 컴포넌트를 메모이제이션 된 컴포넌트로 return
//    이를 Enhanced(강화된, 향상된) Component 라함.
// => 컴포넌트 선언과 동시에 적용하는것 도 가능
//    const comp = React.memo(() => {....})

// => 고차 컴포넌트 (HOC: High Order Component)
//    컴포넌트 기능을 재사용 하기위한 리액트 고급기술 
//    인자로 전달된 컴포넌트에 새로운 기능을 추가해 
//    더욱 강화된 컴포넌트를 return 하는 컴포넌트(함수) 를 말하며
//    이때 return 되는 강화된 컴포넌트를 Enhanced(강화된, 향상된) Component 라함.   
// => const enhanced_Component = React.memo(Header)

//=================================================

// ** TodoList (일정관리 앱) 3.
// => useMemo(), useCallback(), React.memo 적용

import './App.css';
import Header from './components01/Header';
import TodoEditor from './components01/TodoEditor';
import TodoList from './components01/TodoList';
import { useReducer, useState, useRef, useEffect, useMemo } from "react";
import { useCallback } from 'react';

// 2. Mock Data
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

function App() {
  
  // *** => useReducer 로
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
    console.log("** onCreate , todo.length => "+todo.length);
    // => useCallback 을 적용하지않으므로 
    //    일정을 추가할때 마다 App 컴포넌트 리랜더링 되면서, 내부의 함수도 재생성되면서
    //    변경된 State 변수값에 접근하여 정확한 길이를 출력함.
  }; //onCreate

  // ----------------------------------------------
  // ** useCallback 적용
  // => 비어있는 배열을 두번째 인자로 전달하여 마운트시에만 생성되도록 함.
  // => useReducer() 를 이용하므로 State 변수에 직접 접근하지 않기때문에
  //    "함수형 업데이트" 기능을 사용하지 않아도 됨.

  // => useCallback(callback, [의존성배열])
  //    [의존성배열] 이 변경되면 callback 함수를 재생성 return 함.
  // => useCallback(callback, [])
  //    비어있는 배열을 전달하면 callback 함수는 마운트시에만 생성되고, 이후에는 재생성 되지않음

  // ** 일정 수정
  const onUpdate = useCallback( (targetId) => {
      dispatch({ type:"Update", targetId }); //dispatch   
      console.log("** onUpdate_useCallback, todo.length => "+todo.length);
      // => useCallback 의 첫번째 인자인 콜백함수는 일정이 추가되어도 
      //    최초 생성시의 전달받은 State 변수값(todo.length 값 5)) 만 가지고 있음을 확인해본다. 
      //    ( 새로운 State 변수값 접근불가 )  
      //    반면에 useCallback 을 적용하지않은 onCreate 는 
      //    일정을 추가할때 마다 새로운 State 변수값에 접근하여 정확한 길이를 출력함.
    }, [] );

  // ** 일정 삭제 
  const onDelete = useCallback( (targetId) => {
    dispatch({ type:"Delete", targetId }); //dispatch 
  }, [] );

  // ** useMemo 와 useEffect 와 호출시점 비교예제 
  useMemo(() => { console.log("** useMemo Call !!!"); }, []); //1st
  console.log("** App Update !! **");  //2nd
  useEffect(() => { console.log("** useEffect Call !!!"); }, []); //3rd (랜더링후)

  return (
    <div className="App">
      {/* <TestComp /> */}
      <Header />
      <TodoEditor onCreate={onCreate} />
      <TodoList todo={todo} onUpdate={onUpdate} onDelete={onDelete} />
    </div>
  );
}

export default App;
