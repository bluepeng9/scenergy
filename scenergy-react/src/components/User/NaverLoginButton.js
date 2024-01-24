import React from "react";

const NaverLoginButton = () => {
    const hanleNaverLogin = () => {
        //네이버 로그인 로직
        const clientId = process.env.React_APP_NAVER_CLIENT_ID;
        // const redirectUri = encodeURIComponent(window.location.origin);
        const callbackUrl = "http://localhost:8002/daebbang/naver/redirect";

        //클라이언트 ID, 리다이렉트 URI 등 네이버 개발자 센터서 발급받은 정보 사용

        // 네이버 로그인 API를 호출해 소셜 로그인 기능 구현
        // window.location.href = 'https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=${clientId}&redirect_uri=${redirectUri}';
        window.location.href = 'http://localhost:8002/daebbang/naver/redirect';


        // https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=UEFBSKaIlZzXBi31korg&state=1234&redirect_uri=http://localhost:3000/successNaverLogin
    };

    return (
        <button onClick={hanleNaverLogin}>
            Naver 로그인
        </button>
    );
}

export default NaverLoginButton;