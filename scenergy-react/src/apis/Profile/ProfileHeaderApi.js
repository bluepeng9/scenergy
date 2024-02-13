// connect to User
import { api } from "../ChatUserApi";
import ApiUtil from "../ApiUtil";
import FollowApi from "../FollowApi";
import VideoPostApi from "../VideoPostApi";
import UserApi from "../User/UserApi";

export const User = {
  // user: (data) => {
  //   return {
  //     profileImage: "", //이미지 url
  //     nickname: "", //닉네임
  //     bio: "", //한줄 소개
  //     videoCount: 0, //프로필 영상 수
  //     followersCount: 0, //프로필 팔로워 수
  //     followingCount: 0, //프로필 팔로우 수
  //   };
  //   return api.post("/profile", data);
  // },

  user: async (data) => {
    console.log(data);
    console.log("유저아이디", ApiUtil.getUserIdFromToken());
    let userIdFromToken = ApiUtil.getUserIdFromToken();
    const followings = await FollowApi.getAllFollowing(userIdFromToken);
    const followingCount = followings.data.data.findAllResponseList.length;
    // console.log("팔로잉 수", followings.data.data.findAllResponseList.length);
    const followers = await FollowApi.getAllFollowers(userIdFromToken);
    const followerCount = followers.data.data.findAllResponseList.length;
    // console.log("팔로워 수", followers.data.data.findAllResponseList.length);
    const videos = await VideoPostApi.getMyVideoPosts(userIdFromToken);
    const videoCount = videos.data.data.list.length;

    // console.log("영상 수", videos.data.data.list.length);
    // followingsCount: followings.data.length,
    // followersCount: followers.data.length,
    // videoCount: videos.data.length,
  },
};
