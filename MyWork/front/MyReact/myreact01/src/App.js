import './App.css';
import MyHeader from './components01/Header';
import Body from './components01/Body04';
import Footer from './components01/Footer';
// ** import
// => 컴포넌트는 MyComp from real_File_path; 
//    내부 코드에서 MyComp 이름으로 인식

function App() {
    // Test) 객체를 정의하고 컴포넌트로 전달
    // Test1) Header로 전달
    const bestDress = {
        color: 'red',
        style: 'long',
        price: 99000,
        size: ['s', 'm', 'l']
    }

    // Test2) Body 로 전달
    const name = "GreenCompuer";


    return (
        <div className="App">
            <MyHeader className="App-header" bestDress={bestDress} />
            <Body name={name} country={'성남시'} />
            <Footer />
        </div>
    );
}

export default App;
