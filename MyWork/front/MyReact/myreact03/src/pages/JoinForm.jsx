//import '../../styles/JoinForm.scss';
import React, { useEffect, useState } from 'react';

import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import Popup from './Popup';
import PopupPost from './PopupPost';

function JoinForm() {
    return (
        <>
            <div id="joinform_container">
                <p className="pageTitle">회원가입</p>
                <div className="contents">
                    <JoinBox />
                </div>
            </div>
        </>
    );
}

// DB로 데이터 전달 (form의 회원 정보 db로 전송)

const JoinBox = () => {
    // 회원 전체 정보 변수
    const [userData, setUserData] = useState({});
    // 회원 전화번호 변수
    const [phoneNum, setPhoneNum] = useState({
        firstNum: '',
        secondNum: '',
        lastNum: '',
    });
    const navigate = useNavigate();

    // 각 핸드폰 번호 데이터 취합 함수 - 1/2
    const phoneFunc = (e) => {
        // 객체구조 분해 할당으로 각 input태그 별 값 저장
        const { name, value } = e.target;
        setPhoneNum((phoneData) => ({
            ...phoneData,
            [name]: value,
        }));
    };

    // 각 핸드폰 번호 데이터 취합 함수 - 2/2
    useEffect(() => {
        // 회원 전화번호 함수에 담긴 정보 통합 후 회원 전체 정보 변수에 추가
        setUserData((data) => ({
            ...data,
            phone: `${phoneNum.firstNum}-${phoneNum.secondNum}-${phoneNum.lastNum}`,
        }));
    }, [phoneNum]);

    // 유효성 검사 완성된 길이
    const [sucLeng, setSucLeng] = useState();
    const input = document.querySelectorAll('input');
    // 회원가입 완료 버튼
    const dataSubmit = async (e) => {
        e.preventDefault();

        setSucLeng(
            Object.values(completeVal).filter((value) => value != null).length
        );

        if (sucLeng >= input.length - 1) {
            try {
                console.log(userData);
                const response = await axios.post('/user/join', userData);
                alert('회원가입에 성공하셨습니다.');
                navigate('/login');
            } catch {
                alert('회원가입에 실패하셨습니다. 다시 시도하세요.11');
            }
        } else {
            alert('회원가입에 실패하셨습니다. 다시 시도하세요.22');
        }
    };

    // 유효성 검사 완료 유무 확인 변수
    const [completeVal, setCompleteVal] = useState({});
    // 비밀번호 정규식값 저장 변수
    const [passReg, setPassReg] = useState();

    // 비밀번호 정규식화
    useEffect(() => {
        setPassReg(new RegExp(userData.password));
    }, [userData]);

    // 정규식 모음
    useEffect(() => {
        setRegul((prev) => ({
            ...prev,
            rePSW: passReg,
        }));
    }, [passReg]);
    const [regul, setRegul] = useState({
        userID: /^(?=.*[a-z])(?=.*[0-9])[a-z0-9]{8,15}$/g,
        userPSW:
            /^(?=.*[a-z])(?=.*[0-9])(?=.*[!@#$%^&*])[a-z0-9!@#$%^&*]{8,15}$/g,
        rePSW: passReg,
        passRequest: /^(?!.*select).*$/,
        ckpwa: /^(?=.{3,20}$).*$/,
        userName: /^[가-힣]{2,5}$/,
        address: /^.{3,50}$/,
        firstNum: /^(?!선택$).+$/,
        secondNum: /^\d{3,4}$/,
        thirdNum: /^\d{4}$/,
        email: /^[\w.-]+@[a-zA-Z_-]+?\.[a-zA-Z]{2,3}$/,
    });

    // 정규식 조건 틀릴 시 문구 모음
    const [regulFail, setRegulFail] = useState({
        userID: '영어, 숫자 조합으로 8자 이상 15자리 이하로 입력하세요.',
        userPSW:
            '영어, 숫자, 특수문자 조합으로 8자이상 15자 이하로 입력해주세요.',
        rePSW: '비밀번호가 일치하지 않습니다.',
        passRequest: '필수 입력 정보입니다.',
        ckpwa: '3자 이상 20자 이하로 입력해 주세요.',
        userName: '한글 2자 이상 5자 이하로 입력해주세요.',
        address: '3자 이상 50자 이하로 입력해주세요.',
        firstNum: '올바르지 않은 휴대폰 번호입니다.',
        secondNum: '올바르지 않은 휴대폰 번호입니다.',
        thirdNum: '올바르지 않은 휴대폰 번호입니다.',
        email: '이메일 형식이 올바르지 않습니다.',
    });

    // 유효성 검사 문구 변수
    const [valText, setValText] = useState({
        address_1: '필수 입력사항입니다.',
        postal: '필수 입력사항입니다.',
    });
    //데이터 유효성 검사 함수
    const validation = (e) => {
        console.log(valText.id);
        const { id, name, value } = e.target;
        if (value === '') {
            setValText((prev) => ({
                ...prev,
                [id]: '필수 입력 정보입니다.',
            }));
        } else if (regul[id].test(value)) {
            setValText((prev) => ({
                ...prev,
                [id]: '올바른 입력입니다.',
            }));
            setCompleteVal((prev) => ({
                ...prev,
                [id]: '유효성 검사 완료',
            }));
            // 입력 데이터 저장
            setUserData((data) => ({
                ...data,
                [name]: value,
            }));
        } else {
            setValText((prev) => ({
                ...prev,
                [id]: regulFail[id],
            }));
        }
    };

    // ID 중복 체크
    const [counts, setCounts] = useState(false);
    const checkid = async (e) => {
        e.preventDefault();
        if (/^(?=.*[a-z])(?=.*[0-9])[a-z0-9]{8,15}$/g.test(userData.id)) {
            await axios('/user/checkid')
                .then((response) => {
                    if (response.data.includes(userData.id)) {
                        setValText((prev) => ({
                            ...prev,
                            userID: '이미 사용중인 아이디입니다.',
                        }));
                    } else {
                        setCounts(true);
                        setValText((prev) => ({
                            ...prev,
                            userID: '사용 가능한 아이디입니다.',
                        }));
                        setCompleteVal((prev) => ({
                            userID: '유효성 검사 완료',
                        }));
                    }
                })
                .catch();
        } else {
            setValText((prev) => ({
                ...prev,
                id: '영어, 숫자 조합으로 8자 이상 15자리 이하로 입력하세요.',
            }));
        }
    };

    const [isPopupOpen, setIsPopupOpen] = useState(false);
    const [popupPostData, setPopupPostData] = useState([]);
    // 팝업창 열기
    const openPostCode = (e) => {
        e.preventDefault();
        setIsPopupOpen(true);
    };

    // 팝업창 닫기
    const closePostCode = () => {
        setIsPopupOpen(false);
    };

    // 팝업에서 데이터를 전달받는 함수
    const handlePopupData = (data) => {
        setPopupPostData(data);
    };

    // DB에 전달할 객체에 주소 및 우편번호 저장
    useEffect(() => {
        setUserData((prev) => ({
            ...prev,
            postal: popupPostData[1],
            address_1: popupPostData[0],
        }));
    }, [popupPostData]);

    return (
        <>{isPopupOpen && <div style={{ width: "100vw", height: "100vh", backgroundColor: 'white', opacity: ".7", zIndex: "2", position: "absolute" }}></div>}
            <form onSubmit={dataSubmit} className="joinbox" method="post">
                <p>
                    <span>*</span> 필수 입력 사항
                </p>
                <figure>
                    <figcaption>기본정보</figcaption>
                    <table>
                        <colgroup>
                            <col />
                            <col />
                        </colgroup>

                        <tbody>
                            <tr>
                                <th>
                                    아이디 <span>*</span>
                                </th>
                                <td>
                                    <input
                                        type="text"
                                        minLength="4"
                                        maxLength="16"
                                        name="id"
                                        id="userID"
                                        onBlur={counts ? null : validation}
                                        readOnly={counts}
                                        style={
                                            counts
                                                ? { border: '2px solid black' }
                                                : null
                                        }
                                    />
                                    <button onClick={checkid} disabled={counts}>
                                        중복확인
                                    </button>
                                    {valText.userID && (
                                        <div>{valText.userID}</div>
                                    )}
                                </td>
                            </tr>
                            <tr>
                                <th>
                                    비밀번호 <span>*</span>
                                </th>
                                <td>
                                    <input
                                        type="password"
                                        minLength="6"
                                        maxLength="16"
                                        name="password"
                                        id="userPSW"
                                        onBlur={validation}
                                    />
                                    <span></span>
                                    {valText.userPSW && (
                                        <div>{valText.userPSW}</div>
                                    )}
                                </td>
                            </tr>
                            <tr>
                                <th>
                                    비밀번호 확인 <span>*</span>
                                </th>
                                <td>
                                    <input
                                        type="password"
                                        minLength="6"
                                        maxLength="16"
                                        id="rePSW"
                                        onBlur={validation}
                                    />
                                    {valText.rePSW && (
                                        <div className="confirmPSW notice">
                                            {valText.rePSW}
                                        </div>
                                    )}
                                </td>
                            </tr>
                            <tr>
                                <th>
                                    비밀번호 확인 질문 <span>*</span>
                                </th>
                                <td>
                                    <select
                                        id="passRequest"
                                        name="password_q"
                                        onBlur={validation}
                                    >
                                        <option value="select">
                                            선택하세요.
                                        </option>
                                        <option value="pswQ1">
                                            기억에 남는 추억의 장소는?
                                        </option>
                                        <option value="pswQ2">
                                            자신의 인생 좌우명은?
                                        </option>
                                        <option value="pswQ3">
                                            자신의 보물 제 1호는?
                                        </option>
                                        <option value="pswQ4">
                                            가장 기억에 남는 선생님 성함은?
                                        </option>
                                        <option value="pswQ5">
                                            타인이 모르는 자신만의 신체 비밀이
                                            있다면?
                                        </option>
                                        <option value="pswQ6">
                                            추억하고 싶은 날짜가 있다면?
                                        </option>
                                        <option value="pswQ7">
                                            받았던 선물 중 기억에 남는 독특한
                                            선물은?
                                        </option>
                                        <option value="pswQ8">
                                            유년시절 가장 생각나는 친구 이름은?
                                        </option>
                                        <option value="pswQ9">
                                            인상 깊게 읽은 책 이름은?
                                        </option>
                                        <option value="pswQ10">
                                            읽은 책 중에서 좋아하는 구절이
                                            있다면?
                                        </option>
                                        <option value="pswQ11">
                                            자신이 두 번째로 존경하는 인물은?
                                        </option>
                                        <option value="pswQ12">
                                            친구들에게 공개하지 않은 어릴 적
                                            별명이 있다면?
                                        </option>
                                        <option value="pswQ13">
                                            초등학교 때 기억에 남는 짝꿍 이름은?
                                        </option>
                                        <option value="pswQ14">
                                            다시 태어나면 되고 싶은 것은?
                                        </option>
                                        <option value="pswQ15">
                                            내가 좋아하는 캐릭터는?
                                        </option>
                                    </select>
                                    {valText.passRequest ===
                                    '올바른 입력입니다.' ? null : (
                                        <div>{valText.passRequest}</div>
                                    )}
                                </td>
                            </tr>
                            <tr>
                                <th>
                                    비밀번호 확인 답변 <span>*</span>
                                </th>
                                <td>
                                    <input
                                        type="text"
                                        name="password_a"
                                        size="70px"
                                        id="ckpwa"
                                        onBlur={validation}
                                    />
                                    {valText.ckpwa ===
                                    '올바른 입력입니다.' ? null : (
                                        <div>{valText.ckpwa}</div>
                                    )}
                                </td>
                            </tr>
                            <tr>
                                <th>
                                    이름 <span>*</span>
                                </th>
                                <td>
                                    <input
                                        type="text"
                                        minLength="2"
                                        maxLength="10"
                                        name="name"
                                        id="userName"
                                        onBlur={validation}
                                    />
                                    {valText.userName ===
                                    '올바른 입력입니다.' ? null : (
                                        <div>{valText.userName}</div>
                                    )}
                                </td>
                            </tr>
                            <tr>
                                <th>
                                    주소<span>*</span>
                                </th>
                                <td>
                                    <input
                                        type="text"
                                        name="postal"
                                        size="10px"
                                        value={popupPostData[1]}
                                        onChange={validation}
                                        readOnly
                                    />
                                    <button onClick={openPostCode}>
                                        우편번호
                                    </button>

                                    <br />
                                    <input
                                        type="text"
                                        name="address_1"
                                        size="50"
                                        value={popupPostData[0]}
                                        onChange={validation}
                                        readOnly
                                    />
                                    <span>기본주소</span>
                                    <br />
                                    <input
                                        type="text"
                                        name="address_2"
                                        size="50"
                                        id="address"
                                        onBlur={validation}
                                    />
                                    <span>상세주소</span>

                                    {valText.address ===
                                    '올바른 입력입니다.' ? null : (
                                        <div>{valText.address}</div>
                                    )}
                                </td>
                            </tr>
                            <tr>
                                <th>
                                    휴대전화 <span>*</span>
                                </th>
                                <td>
                                    <select
                                        id="firstNum"
                                        name="firstNum"
                                        onChange={phoneFunc}
                                        onBlur={validation}
                                    >
                                        <option value="선택">선택</option>
                                        <option value="010">010</option>
                                        <option value="011">011</option>
                                        <option value="016">016</option>
                                        <option value="017">017</option>
                                        <option value="018">018</option>
                                        <option value="019">019</option>
                                    </select>
                                    -
                                    <input
                                        type="text"
                                        name="secondNum"
                                        minLength="3"
                                        maxLength="4"
                                        size="5px"
                                        id="secondNum"
                                        onChange={phoneFunc}
                                        onBlur={validation}
                                    />
                                    -
                                    <input
                                        type="text"
                                        name="lastNum"
                                        minLength="4"
                                        maxLength="4"
                                        size="5px"
                                        id="thirdNum"
                                        onChange={phoneFunc}
                                        onBlur={validation}
                                    />
                                    {valText.firstNum ===
                                        '필수 입력 정보입니다.' ||
                                    valText.secondNum ===
                                        '필수 입력 정보입니다.' ||
                                    valText.secondNum ===
                                        '필수 입력 정보입니다.' ? (
                                        <div>
                                            올바르지 않은 휴대폰 번호입니다.
                                        </div>
                                    ) : valText.firstNum ===
                                          '올바르지 않은 휴대폰 번호입니다.' ||
                                      valText.secondNum ===
                                          '올바르지 않은 휴대폰 번호입니다.' ||
                                      valText.secondNum ===
                                          '올바르지 않은 휴대폰 번호입니다.' ? (
                                        <div>
                                            올바르지 않은 휴대폰 번호입니다.
                                        </div>
                                    ) : null}
                                </td>
                            </tr>
                            <tr>
                                <th>
                                    이메일 <span>*</span>
                                </th>
                                <td>
                                    <input
                                        type="email"
                                        name="email"
                                        size="30px"
                                        id="email"
                                        onBlur={validation}
                                    />
                                    {valText.email ===
                                    '올바른 입력입니다.' ? null : (
                                        <div>{valText.email}</div>
                                    )}
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </figure>
                <div className="joinButton">
                    <button type="submit" onClick={dataSubmit}>
                        회원가입
                    </button>
                </div>
            </form>
            <div id="popupDom" style={{
                zIndex: "9999", position: "fixed",
                left: "50%",
                top: "50%",
                transform: "translate(-50%, -50%)"
            }}>
                {isPopupOpen && (
                    <Popup>
                        <PopupPost
                            onPopupData={handlePopupData}
                            onClose={closePostCode}
                        />
                    </Popup>
                )}
            </div>
        </>
    );
};

export default JoinForm;
