// connect to User
import { api } from "../api";

export const User = {
  user: (data) => {
    return {
      profileImage: "", //이미지 url
      nickname: "", //닉네임
      bio: "", //한줄 소개
      videoCount: 0, //프로필 영상 수
      followersCount: 0, //프로필 팔로워 수
      followingCount: 0, //프로필 팔로우 수
    };
    return api.post("/profile", data);
  },
};