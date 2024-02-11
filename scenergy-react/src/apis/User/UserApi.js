// connect to User

import api from "../ChatUserApi";

// data
// {
//   userId: "danny1234@naver.com", // 아이디
//       userPassword: "abcdefg1234!", //비밀번호
//     userName: "강대니", //이름
//     userNickname: "대니", //닉네임
//     userBirth: "19980711" /*생년월일, 숫자 8자리*/,
//     userGender: "female" /*남:1, 여:2*/,
// };
// data

// const getPost = function () {};

//토큰 사용
const getUser = () => {
  return {
    userId: "danny1234@naver.com", // 아이디
    userPassword: "abcdefg1234!", //비밀번호
    userName: "강대니", //이름
    userNickname: "대니", //닉네임
    userBirth: "19980711" /*생년월일, 숫자 8자리*/,
    userGender: "female" /*남:1, 여:2*/,
  };
  // return api.post("user/getUser", data);
};

export { getUser };
