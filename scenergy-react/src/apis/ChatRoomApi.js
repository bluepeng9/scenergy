import ApiUtil from "./ApiUtil";

class ChatRoomApi {
    getUserInfo = async (chatRoomId, userId) => {
        const data = {
            chatRoomId: chatRoomId,
            userId: parseInt(userId)
        }
        return await ApiUtil.post(`/chatroom/connect-user`, data);
    };
}

const chatRoomApi = new ChatRoomApi();

export default chatRoomApi;