
// ** Controller 컴포넌트를 완성하세요. 
// => 버튼이 6개 있는 컴포넌트
//    ( -1, -10, -100, +100, +10, +1 )

// 방법1)
function Controller_Old() {
  return(
    <div>
        <button>-1</button>
        <button>-10</button>
        <button>-100</button>
        <button>+100</button>
        <button>+10</button>
        <button>+1</button>
    </div>
  );
}//Controller

const Controller= ({ onChangeState }) => {

  // ** 이벤트 핸들러
  // => State 가 정의된 부모 컴포넌트에 정의해야하고
  //    이를 전달 받아 사용 
  
  // ** onClick 이벤트 핸들러
  // => 이벤트 핸들러 {  } 내부에는 함수명만 사용가능
  //    즉, 매개변수를 정의 할 수 없음
  //    그래서 아래와 같이 이벤트핸들러를 call 하는 함수를 직접작성 
  //    onClick={ ()=> onChangeState(-1) }

  // => 비교
  //    아래처럼 함수를 별도로 정의하더라도
  //    어차피 매개변수 사용못함, 그러므로 직접 코드 작성
  //    -> 주로 화살표함수로 표기
  /*    function buttonClick(n) {
            onChangeState(n);
        }
        => onClick 에 buttonClick(n) 을 call 하는 코드
           onClick={ buttonClick } 
  */
  //console.log('** Controller Update !! **');      
  return(
    <div>
        <button onClick={ ()=> onChangeState(-1) }>-1</button>
        {/* <button onClick={ onChangeState }>-1</button> 
          => 매개변수를 전달하기위해 onClick 이벤트핸들러를 작성해서 
            전달받은 함수를 call(호출) 하지만, 이대로는 인자를 전달 할 수 없음
            그래서 전달받은 이벤트핸들러를 호출하는 함수를 정의하는방식으로 구현 
        */}

        <button onClick={ ()=> onChangeState(-10) }>-10</button>
        <button onClick={ ()=> onChangeState(-100) }>-100</button>
        <button onClick={ ()=> onChangeState(100) }>+100</button>
        <button onClick={ ()=> onChangeState(10) }>+10</button>
        <button onClick={ ()=> onChangeState(1) }>+1</button>

    </div>
  );
}//Controller

export default Controller;