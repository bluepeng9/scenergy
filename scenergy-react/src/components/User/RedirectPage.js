// RedirectPage.js
import React, { useEffect } from "react";
import { useCookies } from "react-cookie";
import { useNavigate } from "react-router-dom";

import onLogin from "./onLogin";
import { useUserToken, UserTokenProvider } from "../../contexts/UserToken2";

const RedirectPage = () => {
  const token = new URL(window.location.href).searchParams.get("token");
  const [cookies, setCookie] = useCookies();
  const navigate = useNavigate();
  const { globalToken, setGlobalToken } = useUserToken();

  useEffect(() => {
    setGlobalToken(token);

    const handleToken = async () => {
      try {
        if (token) {
          console.log("accessToken 쿠키 저장");
          console.log("토큰: " + `${token}`);
          localStorage.setItem("token", `${token}`);
          localStorage.getItem("token");
          setCookie("accessToken", `Bearer ${token}`);

          // Perform API call or any other necessary actions
          // const userData = await api.post("http://localhost:8080");

          // Call onLogin function
          onLogin(
            "eodms4334@email.com",
            "examplePassword",
            navigate,
            setCookie,
          );
        } else {
          console.error("토큰 수신 안됨");
        }
      } catch (error) {
        console.error("토큰 처리 오류:", error);
      } finally {
        console.log("로그인 성공하고 리다이렉트");
        navigate("/home2"); //여기로 리다이렉트
      }
    };

    handleToken();
  }, [token, navigate, setCookie]);

  return (
    <>
      <h1></h1>
    </>
  );
};

const RedirectPageWithProvider = () => (
  <UserTokenProvider>
    <RedirectPage />
  </UserTokenProvider>
);

export default RedirectPageWithProvider;
