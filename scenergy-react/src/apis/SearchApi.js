import ApiUtil from "./ApiUtil";

class SearchApi {
  // 팔로잉하는 사용자들 중 검색
  static searchFollowing = async (userId, word) => {
    return await ApiUtil.post("/users/search-following", {
      userId: userId,
      word: word,
    });
  };
}

export default SearchApi;
