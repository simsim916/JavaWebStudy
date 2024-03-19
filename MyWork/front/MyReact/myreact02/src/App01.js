
// ** 최적화 적용
// => 부모 랜더링시 자식 컴포넌트는 무조건 랜더링 되며 이때 모든 변수값은 초기화되고, 함수는 재호출된다.
//    이로 인한 불필요한 리랜더링을 방지하여 실행시 효율성 향상켜주는것을 말함.  

// => 랜더링 최적화: React.memo (메모이제이션)
//    부모컴포넌트의 영향에서 벗어나 마운트시에만 랜더링함.
//    Header, TodoItem

// => 함수 호출의 최적화: useMemo(callback, [의존성배열])  
//    두번째 인자인 의존성배열 의 값이 바뀌었을때만 callback 함수를 실행하고 결과값을 return함.
//    TodoList

// ** TodoList (일정관리 앱) 1.
// 1. UI
// 2. Mock Data 만들기
// => Mock Data: 모조 Data (개발중 테스트 목적으로 사용하는 Data)
// => 할 일 item(Data_set) 을 담을 배열 생성
// => 앞으로 데이터를 저장하고 관리할 배열, Data_Table 역할
// => 구조
//    id : 아이템 식별을 위한 고유키
//    isDone: 할일 완료 여부 ( boolean )
//    content: 해야 할 일정
//    createDate: 일정 생성(등록) 시간
// => new Date().getTime()
//    Date 값을 getTime 메서드를 이용해 타임스템프 값으로 변환
//    이렇게 하면 보관데이터 용량이 줄어듬

 // ** Date 주요 메서드
 // => toDateString() :  날짜를 문자열로
 // => toLocaleDateString() : 지역별 맞는 포맷으로

// 3. 기능구현
// 3.1) 배열을 리스트로 랜더링하기
// => App.js
//  -> Mock Data mockTodo 를 state 변수 todo 로
//  -> todo 를 Props 이용하여 TodoList 로 전달
// => TodoList
//  -> 전달받은 배열을 map 메서드로 1건씩 TodoItem 으로 전달
//    ( map 을 이용해 컴포넌트 반복하기 )

// => 조건 추가하기 (검색기능)

// 3.2) 입력 (할일 추가)
// => TodoEditor
//  -> 새 item 입력, 추가 버튼 클릭
//  -> 부모 App 에게 이벤트 발생을 알리고 item 전달
// => App
//  -> 전달받은 새 item 을 배열에 추가, 
//  -> state변수인 todo 값 수정
// => TodoEditor: 입력폼 초기화    

// 3.3) 일정 수정

// 3.4) 일정 삭제

//=================================================
import './App.css';
import Header from './components01/Header';
import TodoEditor from './components01/TodoEditor';
import TodoList from './components01/TodoList';
import { useState, useRef } from "react";

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

function App() {
  // 3.1) 배열을 리스트로 랜더링하기
  // => Mock Data mockTodo 를 state 변수 todo 로
  // => todo 를 Props 이용하여 TodoList 로 전달
  // => TodoList
  //  -> 전달받은 배열을 map 메서드로 1건씩 TodoItem 으로 전달
  //    ( map 을 이용해 컴포넌트 반복하기 )
  const [ todo, setTodo ] = useState(mockTodo);
  const idRef = useRef(mockTodo.length);

  // 3.2) 일정추가 (Create) 함수 생성
  // => new 일정을 인자로 전달받아 배열에 저장
  // => 생성된 함수를 TodoEditor 컴포넌트 로 전달
  const onCreate = (content) => {
    // -> 추가할 일정 객체 생성
    const newItem = {
      id: idRef.current,
      content: content,
      isDone: false,
      createDate: new Date().getTime()
    }
    // -> 생성된 객체를 state변수 todo배열에 적용
    //    [todo배열 + newItem]
    //    단, 출력순서 등을 고려해서 newItem 을 index 값 0 이 되도록 저장
    setTodo([newItem, ...todo]);
    idRef.current +=1;
  }; //onCreate

  // ==============================================
  // 3.3) 일정 수정
  // => todo 변경 (checked:완료/ unChecked:미완료  수정)
  // => todo.map() 으로 id 가 일치하는 item 의 isDone 값 변경후(토글방식) return
  // => 수정대상인 id 를 인자로 전달받음
  const onUpdate = (targetId) => {
    setTodo( todo.map( (it) => 
      it.id === targetId ? 
      { ...it, isDone: !it.isDone } : it ) ); //setTodo
  }

  // 3.4) 일정 삭제 
  // => todo 변경 (삭제 대상 제거)
  // => todo.filter() 로 id 가 일치하는 item 만 제외시키고 다른 item 들은 return
  // => 삭제 대상인 id 를 인자로 전달받음
  const onDelete = (targetId) => {
    setTodo( todo.filter( (it) => it.id !== targetId )); //setTodo
  }
  
  console.log("** App Update !! **");
  return (
    <div className="App">
      <Header />
      {/* 3.2) 입력을 위한 일정추가 함수 전달 */}
      <TodoEditor onCreate={onCreate} />
      {/* 3.1) 배열을 리스트로 랜더링하기 */}
      <TodoList todo={todo} onUpdate={onUpdate} onDelete={onDelete} />
    </div>
  );
}

export default App;
