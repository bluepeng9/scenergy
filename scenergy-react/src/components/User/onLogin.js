import axios from "axios";

const onLogin = (email, password, navigate, setCookie) => {
  console.log("onLogin 넘어옴");
  // console.log(email);
  // console.log(password);
  // console.log(navigate);
  // console.log(setCookie);

  let data = {
    email,
    password,
  };

  axios
    .post("/login", data)
    .then((response) => {
      const { accessToken } = response.data;

      axios.defaults.headers.common["Authorization"] = `Bearer ${accessToken}`;

      if (response.status === 200) {
        // 로그인 성공 시 쿠키 설정 및 리다이렉트
        console.log("엑세스 토큰 : " + accessToken);
        setCookie("accessToken", accessToken);
        navigate("/");
      }
    })
    .catch((error) => {
      console.log(error);
    });
};

export default onLogin;
