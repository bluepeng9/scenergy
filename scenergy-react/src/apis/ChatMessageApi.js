import ApiUtil from "./ApiUtil";

class ChatMessageApi {
    getRoomInfo = async (roomId) => {
        return await ApiUtil.get(`/chatMessage/room/${roomId}`);
    };
}

const chatMessageApi = new ChatMessageApi();

export default chatMessageApi;