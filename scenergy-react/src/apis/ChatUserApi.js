import ApiUtil from "./ApiUtil";

class ChatUserApi {
  getUserInfo = async (userId) => {
    return await ApiUtil.get(`/users/${userId}`);
  };
}

const chatUserApi = new ChatUserApi();

export default chatUserApi;
