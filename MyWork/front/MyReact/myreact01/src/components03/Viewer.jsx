// ** Viewer 컴포넌트를 완성하세요. 
// => <h2> 크기로 숫자를 출력하는 컴포넌트
// => 출력할 숫자는 부모컴포넌트로부터 전달받음

const Viewer_Old = (props) => {
  return (
    <div>
      <h2>현재 Count 는</h2>
      <h2>{props.count}</h2>
    </div>
  );
} //Viewer

// => 객체 구조분해 적용
const Viewer = ({ count }) => {
  console.log('** Viewer Update !! **');
  return (
    <div>
      <h2>현재 Count 는</h2>
      <h2>{count}</h2>
    </div>
  );
} //Viewer

export default Viewer;