// connect to User
import { api } from "../ChatUserApi";

export const User = {
  user: (data) => {
    return {
      video: "", //비디오 URL
      thumbnail: "", //썸네일
      genre: "", // 장르
      instrument: "", //악기
      title: "" /*제목*/,
      musicTitle: "" /*연주곡 또는 원제목*/,
      artist: "" /*연주곡 원작자*/,
      description: "" /*설명*/,
    };
    return api.post("/videoDetail", data);
  },
};
