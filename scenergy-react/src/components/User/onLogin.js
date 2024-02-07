import axios from "axios";

const onLogin = (email, password, navigate, setCookie) => {
    console.log("onLogin 넘어오나?");

    let data = {
        email,
        password,
    };

    axios.post("/", data).then((response) => {
        const { accessToken } = response.data;

        axios.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;

        if (response.status === 200) {
            // 로그인 성공 시 쿠키 설정 및 리다이렉트
            setCookie("accessToken", accessToken);
            navigate("/");
        }

    }).catch((error) => {
        console.log(error);
    });
};

export default onLogin;
