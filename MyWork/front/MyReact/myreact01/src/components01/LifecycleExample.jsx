import React from 'react';

// ** Lifecycle Test
// 1. 생성
// => App 실행, 호출
//    생성시작 - constructor - componentDidMount -> 생성완료

// 2. 변경 Test
// => test1) shouldComponentUpdate() : return false 추가 비교
// => test2) shouldComponentUpdate() : return false 추가 후
//           componentDidMount() :  1), 2) 비교 

class LifecycleExample extends React.Component {
  static getDerivedStateFromProps() {
    console.log('getDerivedStateFromProps 호출');
    return {};
  }
  constructor(props) {
    super(props);
    // getDerivedStateFromProps를 사용하기 때문에
    // 경고 메세지를 건너뛰기위해 초기 상태를 설정합니다.
    this.state = {};
    console.log('constructor 호출');
  }
  componentDidMount() {
    console.log('componentDidMount 호출');
    // => 1) , 2)  를 번갈아 추가하며 비교
    // => shouldComponentUpdate() 의 return 값과 무관하게 동기화 진행
    // this.setState({ updated: true});  // 1) 상태값 변경
    // this.forceUpdate();  // 2) 
    // => forceUpdate() 메서드
    //    클래스형 컴포넌트에서는 공식적으로 리렌더를 강제하는 API.
    //    this.forceUpdate()를 호출하면 shouldComponentUpdate()를 건너뛰고 
    //    render() 메서드가 호출되므로 React가 가상 DOM과 DOM의 상태를 재비교하도록 강제하여 리랜더를 강제함.
  }
  componentDidUpdate() {
    console.log('componentDidUpdate 호출');
  }
  componentWillUnmount() {
    console.log('componentWillUnmount 호출');
  }
  getSnapshotBeforeUpdate() {
    console.log('getSnapshotBeforeUpdate 호출');
    return {};
  }
  shouldComponentUpdate() {
    console.log('shouldComponentUpdate 호출');
    // 강제로 false 를 return 해본다.
    // return false;
  }
  render() {
    console.log('render 호출');
    return null;
  }
}

export default LifecycleExample;
