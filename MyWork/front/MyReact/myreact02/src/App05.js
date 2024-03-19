// ** TodoList (일정관리 앱) 리팩토링 4.
// ** 리액트 Context 2.
// => 불필요한 랜더링을 방지하여 최적화 하기위해 
//    Context 를 역할별로 분리한다.
// => Provider 를 계층적으로 적용할 수있으며,
//    이 경우 하위 Provider 값이 우선적용됨.
//  ( 복수의 Provider 를 형제 Level로 적용하는것은 불허_오류  )

// ** Context.Provider (Context 객체에 포함된 컴포넌트) 
// => 컴포넌트 내부에 배치
// => Context를 구독하는 컴포넌트들에게 context의 변화를 알리는 역할.
// => value prop 값을 받아 하위의 컴포넌트에게 전달.
// => 값의 갯수는 제한이 없고, 이를 전달 받을 수 있는 컴포넌트 수의 제한도 없다.
// => Provider 하위에서 context를 구독하는 모든 컴포넌트는 Provider의 value가 바뀔 때마다 리렌더링됨.

//=================================================

import './App.css';
import Header from './components/Header';
import TodoEditor from './components05/TodoEditor';
import TodoList from './components05/TodoList';
import React, {  useMemo, useCallback, useReducer, useRef, useState } from "react";

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

// ** Context 적용 2단계
// 1) Context 생성
// => 불필요한 랜더링을 방지하여 최적화 하기위해 
//    Context 를 역할별로 분리한다.
export const TodoStateContext = React.createContext();
// => todo 의 변경에 영향받는 컴포넌트를 위한 Context 
export const TodoDispatchContext = React.createContext();
// => dispath 함수 onCreate, onUpdate, onDelete 의 변경에
//    영향받는 컴포넌트를 위한 Context

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

  // ------------------------------------------------
  // ** TodoDispatchContext.Provider value 속성값
  //    onCreate, onUpdate, onDelete 함수 최적화 
  // => 처음 한번만 (TodoList가 처음 리랜더링 될때) 실행되도록 메모이제이션함. 
  //    전달시점에 생성하여 보내기 때문에 이렇게 하지 않으면 최적화가 적용되지않음
  const memoizedDispatches = useMemo(() => {
    return { onCreate, onUpdate, onDelete };
  }, []);
  // ------------------------------------------------
  console.log("** App Update !! **");
  return (
    <div className="App">
      <Header />
      {/* 
      2) Context 분리 후 Provider 배치 
        => 계층적으로 배치
        => 이 경우 하위 Provider 값이 우선적용됨.
        => 용도별로 Context를 분리했다고 최적화가 적용되지는 않음
           TodoDispatchContext.Provider value 속성값 onCreate, onUpdate, onDelete 함수는
           위에 useCallback 함수로 최적화된 onUpdate, onDelete 가 아니고
           전달시점에 생성하여 보내기 때문에 별도로 메모이제이션을 적용해 주어야 최적화할 수 있다. 
      */} 
      <TodoStateContext.Provider value={todo}>
        <TodoDispatchContext.Provider value={memoizedDispatches}>
        {/* <TodoDispatchContext.Provider value={{onCreate, onUpdate, onDelete}}>  
            => Context 분리 했어도 최적화 적용 되지 않음  */}
          <TodoEditor />
          <TodoList />
        </TodoDispatchContext.Provider>
      </TodoStateContext.Provider>  
    </div>
  );
}

export default App;
