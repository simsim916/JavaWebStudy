import '../styles/Main.css';
import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import { apiCall } from '../service/apiService';
import { API_BASE_URL } from "../service/app-config";

export default function MyInfo({ token }) {
    
    const [userInfo, setUserInfo] = useState(null); // 서버에서 받아온 UserInfo를 담는 변수
    const navigate = useNavigate();

    // => 서버에서 로그인한 UserInfo 가져오기   
    useEffect(() => { fetchData(); }, []);

    const fetchData = () => {
        let url = "/auth/userdetail";
        apiCall(url, 'GET', null, token)
        .then((response) => {
            alert(`** myInfo 성공 id=${response.id}`);
            setUserInfo(response);
        }).catch((err) => {
            setUserInfo('');
            if (err===502) { alert("** userID 오류, 로그인 후 다시하세요 ~~");
            }else { alert(`** onLoginSubmit 시스템 오류, err=${err}`); }
            navigate("/login");
        }); //apiCall
      }; //fetchData

// => 데이터를 받아올 때까지 로딩 표시
if (userInfo === null) {
    return (
        <div style={{ fontWeight: 'bold', fontSize: 30, height: 600 }}>
            Loading...
        </div>
    );
}else 
    return (
        <div className="body_container">
          <div className="contents">  
            <p className="pageTitle">** MyInformaion **</p>
            <table className="userinfoTable">
              <tbody>
                <tr><th style={{backgroundColor:'AliceBlue', height:'30px'}}>I D</th>
                    <td>{userInfo.id}</td></tr>
                <tr><th style={{backgroundColor:'Snow', height:'30px'}}>Name</th>
                    <td>{userInfo.name}</td></tr>
                <tr><th style={{backgroundColor:'AliceBlue', height:'30px'}}>AGE</th>
                    <td>{userInfo.age}</td></tr>
                <tr><th style={{backgroundColor:'AliceBlue', height:'30px'}}>JNO</th>
                    <td>{userInfo.jno}</td></tr>
                <tr><th style={{backgroundColor:'AliceBlue', height:'30px'}}>Info</th>
                    <td>{userInfo.info}</td></tr>
                <tr><th style={{backgroundColor:'AliceBlue', height:'30px'}}>Point</th>
                    <td>{userInfo.point}</td></tr>
                <tr><th style={{backgroundColor:'AliceBlue', height:'30px'}}>Birthday</th>
                    <td>{userInfo.birthday}</td></tr>
                <tr><th style={{backgroundColor:'AliceBlue', height:'30px'}}>추천인</th>
                    <td>{userInfo.rid}</td></tr>  
                <tr><th style={{backgroundColor:'AliceBlue'}}>Image</th>
                    <td><img alt="MyImage" 
                            src={`${API_BASE_URL}/resources/uploadImages/${userInfo.uploadfile}`} width={50} height={70} />
                        {/* Front public/uploadImages: src={'../uploadImages/'+userInfo.uploadfile} 
                            Server: http://localhost:8080/resources/uploadImages/apple.png 
                                    src={API_BASE_URL+'/resources/uploadImages/'+userInfo.uploadfile}
                            */}
                    </td></tr>
              </tbody>
            </table>
          </div>  
        </div>
    ); //return
} //MyInfo
