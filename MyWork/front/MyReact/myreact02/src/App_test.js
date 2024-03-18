import logo from './logo.svg';
import './App.css';
import { useEffect, useMemo, useState } from 'react';

function App() {
  let [state, setState] = useState(0);

  useEffect(() => {
    console.log(`useEffect : state = ${state}`)
  }, [state]);

  useMemo(() => {
    console.log(`useMemo : state = ${state}`)
  }, [state]);
  
console.log('랜더링 직전')  

  const changeState = () => setState(state++)

  return (
    <div>
      <h3>랜더링 완료</h3>
      <h4>{state}</h4>
      <button onClick={() => changeState()}>+</button>
    </div>
  );
}

export default App;
