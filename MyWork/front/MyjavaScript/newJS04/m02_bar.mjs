let menu='짜장';
console.log(`** bar.mjs menu=${menu}`);
console.log(`** bar.js menu=${window.menu}`); //undefined

// ** export : 외부에 공개
// => 주의사항
//  -> 클래스나 함수를 내보낼 땐 세미콜론을 붙이지 않는다.
//  -> import/export 문은 블록 {...}안에선 동작하지 않는다.
//     이들은 최상위 레벨에 위치해야 함.
//    if (something) { import {sayHi} from "./say.js"; // 에러: }

// 변수의 공개
export const pi = Math.PI;
// 함수의 공개
export function square(x) {
  return x * x;
} // ; 세미콜론을 붙이지 않는다.

// 클래스의 공개
export class Person {
  constructor(name) {
    this.name = name;
  }
}

//export { pi, square, Person };
// => 변수, 함수 클래스를 하나의 객체로 구성하여 공개

// ** export default
// => '해당 모듈엔 개체가 하나만 있다’는 사실을 명확히 나타낼 수 있음
// => 모듈은 크게 두 종류로 나눔
//  -> 복수의 함수가 있는 라이브러리 형태의 모듈
//  -> 개체 하나만 선언되어있는 전용모듈
//  -> 대부분 두번째 방식으로 모듈을 만드는 걸 선호하기 때문에 
//     함수, 클래스, 변수 등의 개체는 전용 모듈 안에 구현됨. 

