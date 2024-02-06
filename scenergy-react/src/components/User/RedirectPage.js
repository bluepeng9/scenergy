import axios from "axios";
import { useEffect } from "react";
import { useCookies } from "react-cookie";
import { useNavigate } from "react-router-dom";

const RedirectPage = () => {
    const token = new URL(window.location.href).searchParams.get("token"); //URL 코드 추출
    const [cookies, setCookie] = useCookies();
    const navigate = useNavigate();

    useEffect(() => {
        const handleToken = async () => {
            try {
                if (token) {
                    // 토큰이 존재하면 쿠키에 저장
                    console.log("accessToken 쿠키 저장");
                    setCookie("accessToken", token);
                    // 여기서 필요에 따라 추가적인 작업 수행 가능
                } else {
                    console.error("토큰 수신 안됨");
                    // 토큰이 없는 경우에 대한 처리 (예: 사용자가 취소한 경우)
                }
            } catch (error) {
                console.error("토큰 처리 오류:", error);
                // 토큰 처리 중 에러가 발생한 경우에 대한 처리
            } finally {
                // 로그인이 성공했다는 화면으로 리다이렉트
                console.log("로그인 성공하고 리다이렉트");
                navigate("/home");
            }
        };

        handleToken();
    }, [token, navigate, setCookie]);

    return null;
};

export default RedirectPage;
