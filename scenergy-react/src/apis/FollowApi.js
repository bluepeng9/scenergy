import ApiUtil from "./ApiUtil";

class FollowApi {
    followUser = async (toUserId) => {
        return await ApiUtil.post("/follow", {
            toUserId: toUserId
        });
    };

    unFollowUser = async (toUserId) => {
        return await ApiUtil.delete(`/follow`, {
            toUserId: toUserId
        });
    };

    getAllFollowing = async (fromUserId) => {
        return await ApiUtil.get(`/follow/followings/${fromUserId}`);
    };

    getAllFollowers = async (toUserId) => {
        return await ApiUtil.get(`/follow/followers/${toUserId}`);
    };

    getFollow = async (fromUserId, toUserId) => {
        let isFollow = (await ApiUtil.get(`/follow`, {
            fromUserId: fromUserId,
            toUserId: toUserId
        })).data.data;
        return isFollow;
    };
}

let followApi = new FollowApi();

export default followApi;
