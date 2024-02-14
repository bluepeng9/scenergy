// connect to User
import ApiUtil from "../ApiUtil";
import FollowApi from "../FollowApi";
import VideoPostApi from "../VideoPostApi";
import UserApi from "../User/UserApi";

class ProfileHeaderApi {
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

  user = async (userId) => {
    console.log("유저아이디", ApiUtil.getUserIdFromToken());
    const followings = await FollowApi.getAllFollowing(userId);
    const followingCount = followings.data.data.findAllResponseList.length;
    console.log("팔로잉 수", followings.data.data.findAllResponseList.length);
    const followers = await FollowApi.getAllFollowers(userId);
    const followerCount = followers.data.data.findAllResponseList.length;
    console.log("팔로워 수", followers.data.data.findAllResponseList.length);
    const videos = await VideoPostApi.getMyVideoPosts(userId);
    const videoCount = videos.data.data.list.length;
    console.log("비디오 수", videoCount);
    const user = await UserApi.getUser();
    const nickname = user.userName;
    const url = user.url;
    const bio = user.bio;
    console.log("유저", user.url);
    return {
      followingCount: followingCount,
      followerCount: followerCount,
      videoCount: videoCount,
      nickname: nickname,
      bio: bio,
      url: url,
    };
  };
}

const profileHeaderApi = new ProfileHeaderApi();

export default profileHeaderApi;
