import axios from "axios";
import { useEffect } from "react";
import { useCookies } from "react-cookie";
import { useNavigate } from "react-router-dom";
import onLogin from "./onLogin";

const RedirectPage = () => {
  const token = new URL(window.location.href).searchParams.get("token");
  const [cookies, setCookie] = useCookies();
  const navigate = useNavigate();

  useEffect(() => {
    const handleToken = async () => {
      try {
        if (token) {
          console.log("accessToken 쿠키 저장");
          setCookie("accessToken", `Bearer ${token}`);
          await axios
            .get(`http://localhost:8080/users/`, {
              /*${userId}*/
              headers: {
                Authorization: `Bearer ${token}`,
              },
            })
            .then((res) => {
              console.log(res);
            });

          console.log("onLogin 넘어가기 전");

          // onLogin 함수 호출 시 필요한 매개변수 전달
          onLogin(
            "eodms4334@email.com",
            "examplePassword",
            navigate,
            setCookie,
          );

          console.log("onLogin 갔다온 후");
        } else {
          console.error("토큰 수신 안됨");
        }
      } catch (error) {
        console.error("토큰 처리 오류:", error);
      } finally {
        console.log("로그인 성공하고 리다이렉트");
        navigate("/home2");
      }
    };
    // http://localhost:3000/oauth2/redirect?token=eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiI1IiwiZXhwIjoxNzA5ODE1MzgyfQ._NL1iHcdNi6xDGTlY86_cY18ovhZVlThuuUnvXToeFQZ00IZWR0wzJHis9m1ED8ZTYnRAME4e8TQ2aGoC1W4oA

    handleToken();
  }, [token, navigate, setCookie]);

  return null;
};

export default RedirectPage;
