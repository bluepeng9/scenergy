import ApiUtil from "./ApiUtil";
import apiUtil from "./ApiUtil";

class SearchApi {
  // 팔로잉하는 사용자들 중 검색
  static searchFollowing = async (userId, word) => {
    return await ApiUtil.post("/users/search-following", {
      userId: userId,
      word: word,
    });
  };

  searchJobPost = async (data) => {
    return await ApiUtil.post("/jobPost/search", {
      name: data.name,
      gt: data.gt,
      it: data.it,
      lt: data.lt,
    });
  };

  searchUser = async (word) => {
    try {
      const response = await ApiUtil.get("/search", {
        word: word,
      });
      const searchData = response.data;
      if (searchData.success) {
        return searchData.data;
      } else {
        throw new Error(searchData.message);
      }
    } catch (error) {
      console.error("Error while searching user:", error);
      throw error;
    }
  };

  searchFollowingAll = async (userId) => {
    try {
      const response = ApiUtil.get("/search-following", {
        userId: userId,
      });
      const searchData = (await response).data;
      if (searchData.success) {
        return searchData.data;
      } else {
        throw new Error(searchData.message);
      }
    } catch (error) {
      console.error("Error while searching following user", error);
      throw error;
    }
  };
}

export default SearchApi;
