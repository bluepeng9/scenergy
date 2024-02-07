import React, { useState } from "react";
import SignUp from "./SignUp";
import NaverLoginButton from "./NaverLoginButton";
import { Routes, Route } from 'react-router-dom';
import RedirectPage from './RedirectPage';
import ErrorBoundary from './ErrorBoundary';
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
    <h1 className="login-title">LOGIN</h1>
      <form className="login-input">
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
        <button type="submit" className="login-btn">로그인</button>
      </form>
      <p >
              아직 계정이 없으신가요?
              <button onClick={handleSignUpClick} className="register-btn">
                  회원가입
        </button>
          </p>
          {/* {showSignUp && <SignUp />} */}
          {/*<NaverLoginButton />*/}

        <Routes>
            <Route path="oauth2/redirect/:token" element={<RedirectPage />} />
            <Route path="oauth2/redirect" element={<ErrorBoundary><RedirectPage /></ErrorBoundary>} />
        </Routes>
        {/* NaverLoginButton은 여기서 렌더링 */}
        <NaverLoginButton />
    </div>
  );
};

export default Login;

