import "./TodoItem.css";
import React from "react";
 
const TodoItem = ({id, isDone, content, createDate, onUpdate, onDelete}) => {

  const onChangeCheckbox = () => { onUpdate(id); }
  //const onClickButton = () => { onDelete(id); }

  // => 최적화 (React.memo 적용) 전/후 출력 비교
  console.log(`** TodoItem Update id=${id} **`);
  return(
    <div className="TodoItem">
      <div>
        <input type="checkbox" checked={isDone} onChange={onChangeCheckbox} />
      </div>
      <div className="title_col">{content}</div>
      <div className="date_col">
        { new Date(createDate).toLocaleDateString() }  
        {/* 타임스템프 형식을 Date 형식으로 변환하고, 
            toLocaleDateString() 을 적용하여 문자열 로 랜더링 */}
      </div>
      <div className="btn_col">
        {/* 코드비교 
        <button onClick={onClickButton} >삭제</button> */}
        <button onClick={() => { onDelete(id) }} >삭제</button>
      </div>
    </div>
  );
}; //TodoItem
 
export default  React.memo(TodoItem);