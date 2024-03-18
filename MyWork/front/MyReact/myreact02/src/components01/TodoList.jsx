// ** 분석 기능 추가
// =>  analyzeTodo 함수 추가

// ======================================================================

// ** 검색기능 추가
// => 검색어처리 위한 state 변수 추가
// => input 엘리먼트에 속성추가
// => 검색어처리 위한 필터링 기능

// ** Array.prototype.filter()
// => filter() 메서드는 주어진 콜백함수의 테스트를 통과하는 모든 요소를 모아 새로운 배열로 반환.
// => arr.filter(callback(element[, index[, array]])[, thisArg])

// => callback 함수: 각 요소를 시험할 함수, true 반환하면 요소를 유지하고, false 반환하면 버림.
//    -> element : 처리할 현재 요소.
//    -> index 선택적 : 처리할 현재 요소의 인덱스.
//    -> array 선택적 : filter를 호출한 배열.
// => thisArg 선택적 : callback을 실행할 때 this 로 사용하는 값.
// => return(반환) 값
//    -> 테스트를 통과한 요소로 이루어진 새로운 배열
//    -> 어떤 요소도 테스트를 통과하지 못하면 빈 배열을 반환

// ======================================================================
import "./TodoList.css";
import TodoItem  from "./TodoItem";
import { useState, useMemo } from "react";

const TodoList = ({todo, onUpdate, onDelete}) => {

  // => 검색어처리 위한 state 변수 와 onChangeSearch 추가
  const [search, setSearch] = useState("");
  const onChangeSearch = (e) => { setSearch(e.target.value) }

  // => 검색어처리 위한 필터링 기능
  // => 필터링 완료된 배열을 map() 으로 전달하도록함
  // => 검색어가 있으면 filter 적용, 대소문자 구별하지 않도록 함
  //    ( 삼항식 으로 )
  const getSearchResult = () => {
    return search === "" ? todo 
            : todo.filter( (it) => it.content.toLowerCase().includes( search.toLowerCase()));
  }
  // ------------------------------------------------
  // ** 분석 기능 추가
  // 1) 분석 함수 추가
  // => 배열 todo 의 아이템 총갯수, 완료갯수, 미완료갯수 를 객체에 담아 return
 
  const analyzeTodo = () => {
    console.log("** analyzeTodo 호출!! **");
    const totalCount=todo.length;
    // => 배열 todo의 isDone 의 값이 true 인 item의 갯수 
    const doneCount= todo.filter( (it) => it.isDone ).length;
    const notDoneCount= totalCount - doneCount;
    return { totalCount, doneCount, notDoneCount } ;
  }; //analyzeTodo
 
  // 2) 분석 함수 호출
  // => analyzeTodo() 호출하고 return 값을 구조분해 할당
  //const {totalCount, doneCount, notDoneCount} = analyzeTodo();

  // 3) 분석 결과
  // => analyzeTodo() 는 todo 에 저장 아이템이 많아질수록
  //    연산량이 많이지며, 성능에 영향을 줄수있음
  // => 불필요한 호출이 있는지 확인 위해 analyzeTodo() 에 콘솔 메시지 추가
  //   ( 마운트시 1 + 검색어 단어 입력시마다 호출됨 을 확인 )
  // => 재연산이 필요없는 경우에도 호출됨을 알 수 있다.
  //   ( 컴포넌트 내부의 함수는 리랜더링 할때 마다 호출되기 때문 )  
  // => 해결 위해 useMemo() 적용.  

  // 4) useMemo() 적용 최적화
  // => const value = useMemo(callback, [의존성배열]);
  //    의존성배열 의 값이 바뀌면 callback 함수를 실행하고 결과값 return 
  // => 그러므로 todo 배열의 값에 변경사항이 있을때에만 analyzeTodo 함수 호출
  /* 
  => Code1.
  const returnObj = useMemo( () => {
    console.log("** analyzeTodo 호출!! **");
    const totalCount=todo.length;
    // => 배열 todo의 isDone 의 값이 true 인 item의 갯수 
    const doneCount= todo.filter( (it) => it.isDone ).length;
    const notDoneCount= totalCount - doneCount;
    return { totalCount, doneCount, notDoneCount }; //return
    }, [todo]);

  const {totalCount, doneCount, notDoneCount} = returnObj;
  // => 이때의  returnObj 는 callback 함수의 return 값을 가지고있는 객체
    */
  // => Code2.
  //    위 analyzeTodo() 를 useMemo 의 콜백함수로 사용하고,
  //    useMemo 의 return 값을 바로 할당.
  const {totalCount, doneCount, notDoneCount} = useMemo(analyzeTodo, [todo]);

  return (
    <div className="TodoList">
      <h3>Todo List 🌻</h3>
      <div>
        <div>* 총일정 갯수: {totalCount}</div>
        <div>* 완료된 일정: {doneCount}</div>
        <div>* 미완료 일정: {notDoneCount}</div>
      </div>
      <input  className="searchbar" 
              value={search}
              onChange={onChangeSearch}
              placeholder="검색어를 입력하세요 ~" />
      <div className="list_wrapper" >
        {/* 1) 배열 전달받기 전 출력용  
        <TodoItem />
        <TodoItem />
        <TodoItem /> */}
        {/* 2) 배열(todo) 전달후 
        { todo.map( (it) => ( <TodoItem key={it.id}  {...it} /> ) )}
        */}
        {/* => key 
             - 각각의 컴포넌트 구분을 위해 사용되며 반드시 지정해야함 (경고메세지)
             - 일정 수정 삭제시 사용됨
            => 펼침(Spread)연산자 로 객체형인 it 를 TodoItem 으로 전달
                {...it} -> {id:"", isDone:... }    */}

        {/* 3) 배열(todo) 에  filter() 적용  
            => TodoItem 로 전달하기전 filter() 처리하고 , 처리된 배열을 map() 으로 전달 */}
        { getSearchResult().map( (it) => ( <TodoItem key={it.id}  {...it} 
                                                    onUpdate={onUpdate} 
                                                    onDelete={onDelete} /> ) )}
      </div>
    </div>
  );
};//TodoList
export default TodoList;