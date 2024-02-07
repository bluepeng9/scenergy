import axios from "axios";

const apiClient = axios.create({
  baseURL: "http://localhost:8080",
  //서버 주소로 바꾸기 나중에~!!!!
  headers: {
    "Content-Type": "application/json",
  },
});

export const fetchMyFollowings = (fromUserId) => {
  return apiClient.get("/follow/followings", { params: fromUserId });
};
