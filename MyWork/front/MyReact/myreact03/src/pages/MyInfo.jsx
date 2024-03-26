import React, { useState, useEffect } from 'react';
import axios from 'axios';
import '../../styles/Adminpage.scss';
import { useNavigate } from 'react-router-dom';

export default function MyInfo({ setIsLoggedIn }) {
    return (
        <div id="userinfo_container">
            <p className="pageTitle">개인정보 수정</p>
            <div id="contents">
                <MyInfoForm setIsLoggedIn={setIsLoggedIn} />
            </div>
        </div>
    );
}
/////////////////////////////////////////////////////////////////////////////////////////////

export function MyInfoForm({ setIsLoggedIn }) {
    const [loginInfo, setLoginInfo] = useState(null); // db에서 받아온 정보를 담는 변수
    const navigate = useNavigate();

    // 로그인한 회원의 개인정보 가져오기
    useEffect(() => {
        fetchData();
    }, []);

    const fetchData = async () => {
        try {
            const response = await axios.get('/user/userinfo');
            setLoginInfo(response.data);
        } catch (error) {
            console.error(error);
        }
    };

    // 데이터를 받아올 때까지 로딩 표시
    if (loginInfo === null) {
        return (
            <div style={{ fontWeight: 'bold', fontSize: 30, height: 600 }}>
                Loading...
            </div>
        );
    }

    // 받아온 핸드폰번호를 -기준으로 자르기
    const firstNum = loginInfo.phone ? loginInfo.phone.substring(0, 3) : '';
    const secondNum = loginInfo.phone ? loginInfo.phone.substring(4, 8) : '';
    const lastNum = loginInfo.phone ? loginInfo.phone.substring(9) : '';

    // 각 input의 value값 수정
    const handleChange = (e) => {
        const { name, value } = e.target;
        setLoginInfo((prevLoginInfo) => ({
            ...prevLoginInfo,
            [name]: value,
        }));
    };

    // 수정한 value값을 서버에 다시 저장(개인정보 수정)
    const handleFormSubmit = async (e) => {
        e.preventDefault();
        console.log(loginInfo); // 변경된 loginInfo 값 출력
        try {
            const response = await axios.post('/user/update', loginInfo, {
                headers: {
                    'Content-Type': 'application/json',
                },
            });
            console.log(response.data); // 서버 응답 확인
            alert('개인정보 수정 성공');
            navigate('/mypage');
            // 필요한 작업 수행
        } catch (error) {
            console.error(error);
            alert('개인정보 수정 실패');
        }
    };

    // 회원탈퇴
    const withdrawal = () => {
        if (window.confirm('정말로 탈퇴하시겠습니까?')) {
            axios
                .get('/user/delete')
                .then(() => {
                    alert('회원탈퇴완료');
                    setIsLoggedIn(false);
                    navigate('/');
                })
                .catch((error) => {
                    console.log(error);
                });
        } else {
            alert('탈퇴취소');
        }
    };

    return (
        <form onSubmit={handleFormSubmit} className="forminfo" method="post">
            <table className="userinfoTable">
                <tbody>
                    <tr>
                        <th>아이디</th>
                        <td>
                            <input
                                type="text"
                                minLength="4"
                                maxLength="16"
                                name="id"
                                id="userID"
                                value={loginInfo.id}
                                onChange={handleChange}
                                required
                            />
                        </td>
                    </tr>
                    <tr>
                        <th>비밀번호</th>
                        <td>
                            <input
                                type="password"
                                minLength="6"
                                maxLength="16"
                                name="password"
                                id="userPSW"
                                value="**************"
                                readOnly
                            />
                            <button
                                type="button"
                                className="btnstyle"
                                onClick={() => {
                                    navigate('/changepw');
                                }}
                            >
                                비밀번호 변경
                            </button>
                        </td>
                    </tr>
                    <tr>
                        <th>이름</th>
                        <td>
                            <input
                                type="text"
                                minLength="2"
                                maxLength="10"
                                name="name"
                                id="userName"
                                value={loginInfo.name}
                                onChange={handleChange}
                            />
                        </td>
                    </tr>
                    <tr>
                        <th>주소</th>
                        <td className="postalinput">
                            <input
                                type="text"
                                name="postal"
                                size="10px"
                                value={loginInfo.postal}
                                onChange={handleChange}
                            />
                            <button>우편번호</button>
                            <br />
                            <input
                                type="text"
                                name="address_1"
                                size="50"
                                value={loginInfo.address_1}
                                onChange={handleChange}
                            />
                            <span>기본주소</span>
                            <br />
                            <input
                                type="text"
                                name="address_2"
                                size="50"
                                id="address"
                                value={loginInfo.address_2}
                                onChange={handleChange}
                            />
                            <span>상세주소</span>
                        </td>
                    </tr>
                    <tr>
                        <th>휴대전화</th>
                        <td className="phoneinput">
                            <input
                                type="text"
                                name="phone"
                                size="5px"
                                id="firstNum"
                                value={firstNum}
                                onChange={handleChange}
                            />
                            -
                            <input
                                type="text"
                                name="phone"
                                size="5px"
                                id="secondNum"
                                value={secondNum}
                                onChange={handleChange}
                            />
                            -
                            <input
                                type="text"
                                name="phone"
                                size="5px"
                                id="thirdNum"
                                value={lastNum}
                                onChange={handleChange}
                            />
                        </td>
                    </tr>
                    <tr>
                        <th>이메일</th>
                        <td>
                            <input
                                type="email"
                                name="email"
                                size="30px"
                                id="email"
                                value={loginInfo.email}
                                onChange={handleChange}
                            />
                        </td>
                    </tr>
                </tbody>
            </table>
            <div className="modifyinfoBtn">
                <button type="submit">정보수정</button>
                <button
                    type="button"
                    onClick={() => {
                        withdrawal();
                    }}
                >
                    회원탈퇴
                </button>
                <button
                    type="button"
                    onClick={() => {
                        navigate(-1);
                    }}
                >
                    취소
                </button>
            </div>
        </form>
    );
}
