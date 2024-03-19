// ** Context 적용
// => immport: useContext, TodoContext 
// => useContext() 로 Data 전달받음
//    TodoItem 컴포넌트 인자로 전달받은 Props Data 중
//    Context 로 전달받는 Data는 필요 없으므로 삭제  

import "./TodoItem.css";
import React, {useContext} from "react";
import { TodoContext } from "../App04";
 
const TodoItem = ({id, isDone, content, createDate}) => {

  // ** Context 적용
  // => 적용후 최적화 기능 점검
  //    React.memo 이 작동하지 않음을 확인
  //    이유는 todo 와 함수들이 하나의 Context 로 묶여있기 때문에
  //    관련이 없는 컴포넌트에서도 부모가 랜더링 되면 같이 랜더링된다.
  //    그러므로 Context 는 역할별로 분리하는것이 바람직하다.
  const { onUpdate, onDelete } = useContext(TodoContext);

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