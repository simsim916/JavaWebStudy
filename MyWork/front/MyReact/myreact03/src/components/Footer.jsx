import React from 'react';
import { Link } from 'react-router-dom';

function Footer() {
    return (
        <div className="footer_cover">
            <div className="footer_customer">
                <hr></hr>
                <span>상호: <strong>성남그린컴아카데미</strong> | 주소: 경기 성남시 분당구 돌마로 46 5F</span><br />
                <span>이메일: 
                    <Link
                        to="mailto:green@naver.com"
                        title="스팸성 메일 차단"
                    >green@naver.com </Link>
                    | 사업자등록번호: 100-22-12345</span><br />
                <span>고객센터 : </span>
                <strong>031-711-8448 , 평일 09:00 - 22:00 , 주말휴무</strong>
            </div>
        </div>
    );
}

export default Footer;
