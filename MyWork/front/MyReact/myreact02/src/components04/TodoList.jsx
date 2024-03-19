// ** Context 적용
// => immport: useContext, TodoContext 
// => useContext() 로 Data 전달받음
//    TodoList 컴포넌트 인자로 전달받은 Props Data는 필요 없으므로 삭제  

// ======================================================================
import "./TodoList.css";
import TodoItem  from "./TodoItem";
import { useState, useMemo, useContext } from "react";
import { TodoContext } from "../App04";

const TodoList = () => {
  // ** Context 적용
  // => 구조분해 적용해서 필요한 Data 만 정의
  //    onUpdate, onDelete 는 TodoItem 으로 전달하기 위해 필요했지만,
  //    이제는 필요없으므로 todo 만 정의함 
  const {todo} = useContext(TodoContext);

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
            => Context에서 직접 전달받는 Props는 재전달 할 필요가 없으므로 삭제한다.
              ( onUpdate, onDelete 속성 ) 
               그러나 해당하는 Item 값은 Props 로 전달해야하므로 그냥둔다.  */}
        { getSearchResult().map( (it) => ( <TodoItem key={it.id} {...it} /> ) )}
      </div>
    </div>
  );
};//TodoList
export default TodoList;