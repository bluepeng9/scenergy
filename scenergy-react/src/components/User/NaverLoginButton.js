import React from "react";
import { useNavigate } from "react-router-dom";
import styled from "styled-components";

const NaverLoginBtn = styled.img`
  width: 50px;
  height: 50px;
  cursor: pointer;
`;

const NaverLoginButton = () => {
    const navigate = useNavigate();

    const handleNaverLogin = () => {
        // 토큰을 받을 URL에 현재 페이지 주소를 붙여 전달
        const redirectUri = encodeURIComponent(window.location.href);
        console.log("토큰 받아지나");
        window.location.href = `http://localhost:8080/oauth2/authorization/naver?redirect_uri=${redirectUri}`;
    };

    return (
        <NaverLoginBtn onClick={handleNaverLogin} src={"/naverlogin.png"} alt="naverlogin" />
    );
};

export default NaverLoginButton;
