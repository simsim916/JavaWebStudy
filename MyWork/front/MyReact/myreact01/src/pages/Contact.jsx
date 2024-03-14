// ** useSearchParams 와 useLocation
// => useSearchParams()
//    url 에 있는 쿼리 스트링의 값을 꺼내어 사용할 수 있도록 해줌.
// => useLocation()
//    현재 라우터의 위치를 나타내는 location 객체를 return
//    현재 위치에 관한 정보가 필요할떄 이용됨.
// => location 객체의 속성 : pathname, search(쿼리문자열) 등
//
import emotion1 from "../images/img/emotion1.png";
import emotion2 from "../images/img/emotion2.png";
import emotion3 from "../images/img/emotion3.png";
import emotion4 from "../images/img/emotion4.png";
import emotion5 from "../images/img/emotion5.png";
import { useSearchParams, useLocation } from "react-router-dom";
import { useState } from "react";

// => id 로 해당하는 image 화일명 return 하는 함수
const getEmotionImgById = (emotionId) => {
  const targetEmotionId = String(emotionId);
  switch (targetEmotionId) {
    case "1":
      return emotion1;
    case "2":
      return emotion2;
    case "3":
      return emotion3;
    case "4":
      return emotion4;
    case "5":
      return emotion5;
    default:
      return null;
  } 
};

// => emotion Data List
const emotionList = [
  {
    id: 1,
    name: "완전 좋음",
    img: getEmotionImgById(1),
  },
  {
    id: 2,
    name: "좋음",
    img: getEmotionImgById(2),
  },
  {
    id: 3,
    name: "그럭저럭",
    img: getEmotionImgById(3),
  },
  {
    id: 4,
    name: "나쁨",
    img: getEmotionImgById(4),
  },
  {
    id: 5,
    name: "끔찍함",
    img: getEmotionImgById(5),
  },
];

function SelectEmotion() {
  // ** useSearchParams
  const [searchParams, setSearchParams]  = useSearchParams();
  // => useState 처럼 배열형태로 반환
  // => 첫번째 요소: 조회, 수정가능한 메서드를 포함하고있는 쿼리스트링 객체
  // => 두번째 요소: 이 객체를 업데이트하는 함수 (즉, 새로운 쿼리스트링을 설정할수있음)  
 
  // => searchParams 로 전달된 파라미터 확인하기
  const queryId = searchParams.get("id");
  console.log(`** queryId=${queryId}, name=${searchParams.get("name")}`);

  // => 새로운 url 입력시 리랜더링 을 위해 State 변수 추가하고,
  //    새롭게 전달되는 queryId 로 초기화
  const [ stateId, setStateId ] = useState(queryId);

  //  ** useLocation
  const location = useLocation();
  console.log(`** location = ${location}`);
  console.log(`** location.pathname = ${location.pathname}`);
  console.log(`** location.search = ${location.search}`);

  // => selected_item의 기본값을 NotFound의 경우로 정의
  let selected_item = {
      name: 'Sorry_NotFound',   
      img: getEmotionImgById(5)
  }
  // => filter 적용
  //  -> 배열의 각 요소에 대해 제공된 callbackFn 함수를 한 번씩 호출하고, 
  //    callbackFn이 참 값을 반환하는 모든 값으로 새 배열을 구성
  //    callbackFn 테스트를 통과하지 못한 배열 요소는 새 배열에 포함되지 않음 
  //    테스트를 통과한 요소가 없으면 빈 배열이 반환됨.

  //  -> Parameter 로 전달받은 id 와 일치하는 요소 return 
  //    (그러므로 결과는 Data요소 1개인 배열)
  const find_item = emotionList.filter( (emotion)=> parseInt(queryId)===emotion.id );
  selected_item = find_item.length > 0 ?  find_item[0] : selected_item; 

  return (
      <div>
          <h3>** {selected_item.name} **</h3>   
          <img alt="오늘의감정" src={selected_item.img} />
      </div>
  ); // return
}; //Topic

export default function Contact() {

  console.log('** Contact Update !!! **');
  return (
    <div>
      <h3>** Contact, EmotionList **</h3>

      {/* ** jsx 에서 이미지 경로 설정
        => src 하위에 존재하는경우
          -> import 방식 (위 의 import 구문과 아래 적용구문 참고)
          -> require 방식 
            문서 어디서나 파일을 불러올 수 있으며 이를 사용하면 inline으로 
            src의 이미지 파일 경로를 바로 지정할 수 있음 (아래 "감정1" 참고)
        => https://whales.tistory.com/95 (jsx 에서 이미지 경로 설정법 상세함)
      
        <img alt="감정1" src={emotion1} width={100} height={100}/> */}
      <img alt="감정1" src={require('../images/img/emotion1.png')} width={100} height={100} />
      <img alt="감정2" src={getEmotionImgById(2)} width={100} height={100}/>
      <img alt="감정3" src={getEmotionImgById(3)} width={100} height={100}/>
      <img alt="감정4" src={getEmotionImgById(4)} width={100} height={100}/>
      <img alt="감정5" src={getEmotionImgById(5)} width={100} height={100} />
      <p>당신의 기분을 주소창에 쿼리스트링으로 입력해보세요...</p>
      {/* => http://localhost:3000/contact?id=1&name=Banana */}
      <SelectEmotion />
      
    </div>
  ); //return
} //Contact