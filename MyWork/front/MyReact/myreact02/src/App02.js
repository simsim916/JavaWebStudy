// ** useReducer()
// => React에서 컴포넌트의 상태 관리를 위해 가장 많이 쓰이는 hook은 setState() 함수,
//    좀 더 복잡한 상태 관리가 필요한 컴포넌트에서는 setReducer() hook 함수를 사용.
// => const [<상태 객체>, <dispatch 함수>] = useReducer(<reducer 함수>, <초기상태값>)
// => reducer 함수는 현재 상태(state) 객체와 행동(action) 객체를 인자로 받아서 
//    새로운 상태(state) 객체를 반환하는 함수.
// => dispatch 함수는 컴포넌트 내에서 상태 변경을 일으키기 위해서 사용되는데
//    인자로 reducer 함수에 넘길 행동(action) 객체를 받습니다.
//    행동(action) 객체는 어떤 동작인지를 나타내는 type 속성과 해당 동작과 괸련된 데이터를 담고 있음.
// => 즉, 컴포넌트에서 dispatch 함수에 행동(action)을 던지면, 
//    reducer 함수가 이 행동(action)에 따라서 상태(state)를 변경해줌.

// ** TodoList (일정관리 앱) 2.
// => useReducer 적용
//=================================================
import './App.css';
import Header from './components01/Header';
import TodoEditor from './components01/TodoEditor';
import TodoList from './components01/TodoList';
import TestComp from './components01/TestComp';
import { useReducer, useState, useRef } from "react";

// 2. Mock Data
const mockTodo = [
{
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

function App() {
  // 3.1) 배열을 리스트로 랜더링하기
  //const [ todo, setTodo ] = useState(mockTodo);
  // *** => useReducer 로
  const [todo, dispatch] = useReducer(reducer, mockTodo);

  const idRef = useRef(mockTodo.length);
  /*
  *** 이전의 이벤트 핸들러 함수 
    => dispatch_상태변화촉발(이벤트 감지) 와
       reducer()_상태값 변경함수 로 나뉘어짐
   */
  // 3.2) 일정추가 (Create) 함수 생성
  // => 상태값 변경부분을 reducer() 에게 맡기고, 이를위해
  //    dispatch 호출해서 action(type, data) 값을 전달해주어야함
  const onCreate = (content) => {
    dispatch({ type:"Create",
               newItem: {
                  id: idRef.current,
                  content: content,
                  isDone: false,
                  createDate: new Date().getTime()
               }
    }); //dispatch
    //setTodo([newItem, ...todo]); => reducer() 에게 맡김
    idRef.current +=1;
  }; //onCreate

  // ==============================================
  // 3.3) 일정 수정
  const onUpdate = (targetId) => {
    dispatch({ type:"Update", targetId }); //dispatch   
  }
  // 3.4) 일정 삭제 
  const onDelete = (targetId) => {
    dispatch({ type:"Delete", targetId }); //dispatch 
  }
 
  return (
    <div className="App">
      <TestComp />
      <Header />
      <TodoEditor onCreate={onCreate} />
      <TodoList todo={todo} onUpdate={onUpdate} onDelete={onDelete} />
    </div>
    
  );
}

export default App;
