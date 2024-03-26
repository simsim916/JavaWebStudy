import '../styles/Main.css';
import React, { useState } from 'react';
import { Link } from 'react-router-dom';

const Login = ({onLoginSubmit}) => {
    const [userId, setUserId] = useState("");
    const [userPassword, setUserPassword] = useState("");
    
    return (
        <div>
            <hr />
            <h2 style={{color:'#708090'}}>로그인 ♻</h2>
            <div className="login_">
                <form onSubmit={ (e) => {
                    e.preventDefault();
                    onLoginSubmit(userId, userPassword)}} >
                    <input type="text" name="id" placeholder="아이디" 
                           size={20} value={userId} 
                           onChange={(e) => setUserId(e.target.value)}
                    /><br/>
                    <input type="password" name="userPassword" placeholder="비밀번호"
                           size={20} value={userPassword} 
                           onChange={(e) => setUserPassword(e.target.value)}
                    /><br/><br/>
                    <input type="submit" className="loginBtn" value="로그인" style={{width:175}}/><br/><br/>
                </form>
                
                <span>
                    <span>아직 회원이 아니신가요?</span>&nbsp;
                    <Link to="/join" style={{color:'#7547a3'}}>&nbsp;&nbsp;
                        <strong>회원가입 ✔</strong>
                    </Link>
                </span>
            </div>
        </div>
    ); //return
}; //Login

export default Login;