import React, {useState} from "react";
import SignUp from "./SignUp";
import NaverLoginButton from "./NaverLoginButton";
import "./Login.css";

const Login = () => {
  const [redirectToSignUp, setRedirectToSignUp] = useState(false);

  const handleSignUpClick = () => {
    setRedirectToSignUp(true);
  };

  if (redirectToSignUp) {
    return <SignUp />;
  }

  return (
      <div className="login-background-img">
          <div className="login-background-filter">
              <div className="loginup-container">
                  <br/>
                  <h1 className="login-title">S C E N E : R G Y</h1>
                  <br/>
                  <br/>
                  <br/>
                  <NaverLoginButton/>
              </div>
          </div>
      </div>
  );
};

export default Login;
