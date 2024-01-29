import React, { useState } from "react";
import SignUp from "./SignUp";
import NaverLoginButton from "./NaverLoginButton";
import './Login.css';

const Login = () => {
    const [redirectToSignUp, setRedirectToSignUp] = useState(false);

    const handleSignUpClick = () => {
        setRedirectToSignUp(true);
    };

    if (redirectToSignUp) {
        return <SignUp />;
    }

  return (
    <div className="loginup-container">
    <h1>로그인 화면</h1>
      <form>
        <label>
          {/* 이메일: */}
          <input type="email" placeholder="이메일을 입력하세요" />
        </label>
        <br />
        <label>
          {/* 비밀번호: */}
          <input type="password" placeholder="비밀번호를 입력하세요" />
        </label>
        <br />
        <button type="submit">로그인</button>
      </form>
      <p>
              아직 계정이 없으신가요? 
              <button onClick={handleSignUpClick}>
                  회원가입
        </button>
          </p>
          {/* {showSignUp && <SignUp />} */}
          <NaverLoginButton />
    </div>
  );
};

export default Login;