// ** Context 적용2
// => immport: useContext, TodoContext 
// => todo 만 필요하므로 용도에 맞는 Context 사용

// ======================================================================
import "./TodoList.css";
import TodoItem  from "./TodoItem";
import { useState, useMemo, useContext } from "react";
import { TodoStateContext } from "../App05";

const TodoList = () => {
  // ** 용도에 맞는 Context 적용
  // => TodoStateContext.Provider 에서 value={todo} 단일객체로 전달되므로 
  //    { } 없이 todo 를 전달 받는다. 
  const todo = useContext(TodoStateContext);

  // ------------------------------------------------
  // ** 검색 기능 
  const [search, setSearch] = useState("");
  const onChangeSearch = (e) => { setSearch(e.target.value) }

  const getSearchResult = () => {
    return search === "" ? todo 
            : todo.filter( (it) => it.content.toLowerCase().includes( search.toLowerCase()));
  }
  // ------------------------------------------------
  // ** 분석 기능 추가
  // 1) 분석 함수 추가
  const analyzeTodo = () => {
    console.log("** analyzeTodo 호출!! **");
    const totalCount=todo.length;
    // => 배열 todo의 isDone 의 값이 true 인 item의 갯수 
    const doneCount= todo.filter( (it) => it.isDone ).length;
    const notDoneCount= totalCount - doneCount;
    return { totalCount, doneCount, notDoneCount } ;
  }; //analyzeTodo
  // 2) 분석 함수 호출
  // => useMemo 적용
  const {totalCount, doneCount, notDoneCount} = useMemo(analyzeTodo, [todo]);
   // ------------------------------------------------
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
        {/* ** 검색기능 : 배열(todo) 에  filter() 적용   
            ** TodoItem Context 적용  
            => Context에서 직접 전달받는 Props는 필요 없으므로 삭제한다.
               그러나 해당하는 Item 은 Props 로 전달해야하므로 그냥둔다.  */}
        { getSearchResult().map( (it) => ( <TodoItem key={it.id} {...it} /> ) )}
      </div>
    </div>
  );
};//TodoList
export default TodoList;