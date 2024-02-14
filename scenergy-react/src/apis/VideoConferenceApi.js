import ApiUtil from "./ApiUtil";

class VideoConferenceApi {

    savePeerId = async (chatRoomId, userId, peerId) => {
        return await ApiUtil.post("/video-conference/save/peerId", {
            chatRoomId: chatRoomId,
            userId: userId,
            peerId: peerId,
        });
    };

    getPeerId = async (chatRoomId, userId) => {
        return await ApiUtil.post("/video-conference/get/peerId", {
            chatRoomId: chatRoomId,
            userId: userId,
        });
    };

}

const videoConferenceApi = new VideoConferenceApi();
export default videoConferenceApi;
