// ** 1컴포넌트 1화일
// => 그러므로  export default 많이 사용됨.
export default function Header(props) {
  return (
    <header>
      <h1>** Header **</h1>
      <p>금주의 Best_Dress : color={props.bestDress.color}, style={props.bestDress.style}, price={props.bestDress.price}</p>
      {/* <p>비교 : Best_Dress = {props.bestDress}</p> =>  오류 */}
      <p>size 는 {props.bestDress.size.length} 개의 종류가 있습니다.</p>
    </header>
  );
};