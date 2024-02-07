// connect to User
import { api } from "../api";
import { useState } from "react";

export const User = {
  user: (data) => {
    return {
      // 대표영상
      videoPick: "", //영상 url

      //영상목록
      videoList: "", //영상 url
    };
    return api.post("/profile", data);
  },
};
