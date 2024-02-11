import ApiUtil from "./ApiUtil";

class LikeApi {
  getLikeList = async (post_id) => {
    return await ApiUtil.get("/like/get-likelist", {
      post_id: post_id,
    });
  };

  getLikeCount = async (post_id) => {
    return await ApiUtil.get("/like/get-like-count", {
      post_id: post_id,
    });
  };

  postUnlike = async (post_id, user_id) => {
    return await ApiUtil.post("/like/post-unlike", {
      post_id: post_id,
      user_id: user_id,
    });
  };

  postLike = async (post_id, user_id) => {
    return await ApiUtil.post("/like/post-like", {
      post_id: post_id,
      user_id: user_id,
    });
  };
}

const likeApi = new LikeApi();
export default likeApi;
