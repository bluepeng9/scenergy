import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8080",
  //서버 주소로 바꾸기 나중에~!!!!
  headers: {
    "Content-Type": "application/json",
  },
});

export default api;

//리덕스로 상태관리하자
//api를 레퍼클래스로 만들기
//getPost할 때 토큰 있으면 넣고, 아니면 넣지 말기
//authrization header에
//대충 이런식으로 api.get
