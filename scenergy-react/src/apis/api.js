import axios from "axios";

const api = axios.create({
  baseURL: "http://localhost:8080",
  //서버 주소로 바꾸기 나중에~!!!!
  headers: {
    "Content-Type": "application/json",
  },
});

export default api;
