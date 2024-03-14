import { useEffect } from "react";
function Even() {

  // => 클린업 함수
  //    - useEffect 의 콜백함수에서 return 하는 함수
  //    - 콜백함수를 재호출하기 전에 실행됨.

  useEffect(() => {
    return () => {
      console.log("Even 컴포넌트 언마운트");
    };
  },[]);  
  // => 두번째 인자는 [] 또는 없어도 됨
  //   ( count 가 짝수일때만 랜더링 되기 때문 )

  // => 최초실행 : count=0 (짝수) -> 랜더링 (출력) ->
  //              count=1 (홀수) -> 언마운트 (console 출력) 

  return <div>현재 카운트는 짝수입니다</div>;
}
export default Even;
