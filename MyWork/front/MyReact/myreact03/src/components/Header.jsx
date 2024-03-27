import '../styles/Header.css';
import { Link } from 'react-router-dom';
import { apiCall } from '../service/apiService';

function Header({ userName, isLoggedIn, onLogout }) {

    // ** 서버연결 확인 하기
    const serverTest = () => {
        let url='/user/check-server';
        apiCall(url, 'GET', null, null)
        .then((response) => {
            alert(`** 서버 API 연결 성공 => ${response.checkData}`);
            // apiCall 에서는 response.data 값을 return 함.
        }).catch((err) => {
            alert(`** 서버 API 연결 실패 => ${err}`);
        });
    } //serverTest

    return (
        <div className="headerTop">
            <h2>** Full_Stack SpringBoot & React **</h2>
            <div className="headerLeft">
                <span onClick={serverTest} className="textlink">Server</span>&nbsp;&nbsp;
                <a href='http://localhost:8080/home' >SHome</a>&nbsp;&nbsp;
                <Link to="/">FHome</Link>
            </div>
            <div className="serviceTab">
                <ul className="serviceTabList">{ isLoggedIn ? 
                    ( <>
                        <li>{userName}님</li>
                        <li><Link to="/" onClick={onLogout}>로그아웃</Link></li>
                        <li><Link to="/myinfo/">마이페이지</Link></li>
                        </> ) : 
                    ( <>
                        <li><Link to="/login">로그인</Link></li>
                        <li><Link to="/join">회원가입</Link></li>
                        </> ) }
                    <li><Link to="/mlist">MList</Link></li>
                    <li><Link to="/blist">BList</Link></li>
                </ul>
            </div>
        </div> //headerTop
    ); //return
} //Header

export default Header;
