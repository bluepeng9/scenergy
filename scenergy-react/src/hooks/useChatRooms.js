import axios from "axios";
import { useQuery } from "react-query";
import { useChatRoom } from "../contexts/ChatRoomContext";

export const useChatRooms = (userId) => {
  return useQuery(
    ["chatRooms", userId],
    async () => {
      try {
        const response = await axios.get(
          "http://localhost:8080/chatroom/list-mychatroom",
          {
            params: { user_id: userId },
          },
        );

        const myChatRoomList = response.data.data.myChatRoomList;
        console.log(myChatRoomList);
        return myChatRoomList;
      } catch (error) {
        console.error(error);
        throw error;
      }
    },
    {
      refetchOnWindowFocus: true, //다시 여기로 접속했을때 최신 데이터 보게 해줌
    },
  );
};
