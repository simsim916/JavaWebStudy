// ** Nested Routing
// 1) 자식 page 1,2,3 추가 ( Topics01 ) 
// => App.js의 라우트 path "/topics/*" 로 수정

// 2) 배열로 목록 정의 ( Topics ) : ver02  
// 3) Topic 컴포넌트 추가 : ver02 

// ** useParams()
// => 현재 URL에 포함되어 있는 파라미터를  key, value 형식의 객체형식으로 반환
// => <Route path 속성에 ":?????" 로 정의된 파라미터 에 전달된 값을 객체에 담아 전달해주는 Hook.
//   예) path /test/3 으로 이동한 경우 params의 값 {id:'3'}을 확인할 수 있고
//      path /test/something 으로 이동한 경우 params의 값 {id:'something'} 확인가능
// => "/test/:id" 라는 라우트가 있다면 useParams 로 :id 파라미터를 가져올 수 있음

// ** 관련 훅(HOOKs) 과 추가사항
// => useParams(), path 에 :id 사용
// => useSearchParams()
//    url 에 있는 쿼리 스트링의 값을 꺼내어 사용할 수 있도록 해줌.
//   ( 예. login?id=banana&password=12345! )
// => useLocation()
//    현재 라우터의 위치를 나타내는 location 객체를 return
//    현재 위치에 관한 정보가 필요할떄 이용됨.
// => location 객체의 속성 : pathname, search(쿼리문자열) 등

import { NavLink, Route, Routes, useParams } from "react-router-dom";

// 2) 배열로 목록 정의
const contents = [
  {id:1, title:'HTML', description:'HTML 은 재미있다...ㅎㅎ'},
  {id:2, title:'JavaScript', description:'JavaScript 는 더 재미있다...ㅎㅎ'},
  {id:3, title:'React', description:'React 는 흥미롭다...ㅎㅎ'}
]

// 3) Topic 컴포넌트 추가
// => 배열 contents 에서 전달된 id 에 해당하는 Data 의 description(서술/기술/묘사/표현) 출력하기
// => filter 적용 id 가 일치하는 경우에만 true
function Topic() {
  //const params = useParams(); // { topic_id:1 }
  //console.log(`** params=${params}, params.topic_id=${params.topic_id}`);
  const {topic_id} = useParams();
  // => Route path 속성의 ":topic_id" 에 전달된 url의 같은 위치의 값(파라미터 값) 을 전달해주는 Hook.
  console.log(`** topic_id=${topic_id}`);

  // 3.2) filter 적용
  // => filter 적용
  //  -> 배열의 각 요소에 대해 제공된 callbackFn 함수를 한 번씩 호출하고, 
  //    callbackFn이 참 값을 반환하는 모든 값으로 새 배열을 구성
  //    callbackFn 테스트를 통과하지 못한 배열 요소는 새 배열에 포함되지 않음 
  //    테스트를 통과한 요소가 없으면 빈 배열이 반환됨.

  // => selected_item의 기본값을 NotFound의 경우로 정의
  let selected_item = {
    title: 'Sorry',
    description: '~~ NotFound ~~'
  }
  // => filter 적용 결과
  //   Parameter 로 전달받은 id 와 일치하는 요소 return 
  //    (그러므로 결과는 Data요소 1개인 배열)
  const find_item = contents.filter( (content) => content.id==topic_id ); //filter
  selected_item = find_item.length > 0 ? find_item[0] : selected_item;

  return (
      <div>
          {/* 3.1) 
          <h3>** Topic **</h3>   
          Topic..... */}

          {/* 3.2) filter 적용  => 결과물 출력 */}
          <h3>** {selected_item.title} **</h3>
          <h3>{selected_item.description}</h3> 
      </div>
  ); // return
}; //Topic

// -------------------------------------------------------
// ** ver02
// => ver01에 map 적용
// => parameter 필요함 
function Topics() {
  const lis = contents.map((content) => 
    <li key={content.id}><NavLink to={"/topics/"+content.id}>{content.title}</NavLink></li>
  ); //map

  return (
    <div>
      <h3>** Topics: ver02 **</h3>
      <ul>
        {/* 2) map 적용 */}
        { lis }
      </ul>
      <div>
        <Routes>
          <Route path="/:topic_id" element={<Topic />} />
           {/* 2) map 적용 
            => Route 도  1개로 공유, 출력할 element는  Topic 컴포넌트로
               요청에 따른 출력내용은 Topic 컴포넌트에서 판단
            => 그러므로 주소창에서 전달되는 url 을 변수로 받아야 되고 이것을 파라미터라 함
            => 그리고 파라미터 를 구별하기위해 앞쪽에 : 을 붙여줌  
               주소창에서 전달된 :topic_id 위치의 값이 
               element 속성에 정의된 Topic 컴포넌트에 인자로 전달됨 */}
        </Routes>
      </div>
    </div>
  ); //return
} //Topics

// ** ver01
function Topics1() {
  console.log('** Topics Update !!! **');
  return (
      <div className="App">
        <h3>** Topics: ver01 **</h3>   
        {/* 1) 자식 page 1,2,3 추가 
             => 상위 컴포넌트에 /topics/* 요청에 대한 Route 를 정의 해야함  */}
        <ul>
            <li><NavLink to="/topics/1">HTML</NavLink></li>
            <li><NavLink to="/topics/2">JavaScript</NavLink></li>
            <li><NavLink to="/topics/3">React</NavLink></li>
        </ul>
        <Routes>
            <Route path="/1" element={"HTML 은 재미있다..."} />
            <Route path="/2" element={"JavaScript 는 더 재미있다..."} />
            <Route path="/3" element={"React 는 신기하다..."} />
        </Routes> 
        
      </div>
  ); // return
}; //Topics01   

export default Topics;