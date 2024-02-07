// connect to User
import { api } from "../api";

export const User = {
  user: (data) => {
    return {
      profileImage: "", //이미지 url
      videoCount: 0, //프로필 영상 수
      followersCount: 0, //프로필 팔로워 수
      followingCount: 0, //프로필 팔로우 수
    };
    return api.post("/profile", data);
  },
};
