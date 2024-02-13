import ApiUtil from "./ApiUtil";

class FollowApi {
  followUser = async (toUserId) => {
    return await ApiUtil.post("/follow", {
      toUserId: toUserId
    });
  };

  unFollowUser = async (followId) => {
    return await ApiUtil.delete(`/follow/${followId}`);
  };

  getAllFollowing = async (fromUserId) => {
    return await ApiUtil.get(`/follow/followings/${fromUserId}`);
  };
}

let followApi = new FollowApi();

export default followApi;
