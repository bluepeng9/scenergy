import axios from "axios";
import { useQuery } from "react-query";
import { useChatRoom } from "../contexts/ChatRoomContext";

export const useChatRooms = (userId) => {
  const { addChatRoom } = useChatRoom();

  return useQuery(["chatRooms", userId], async () => {
    try {
      const response = await axios.get(
        "http://localhost:8080/chatroom/list-mychatroom",
        {
          params: { user_id: userId },
        },
      );

      const myChatRoomList = response.data.data.myChatRoomList;
      console.log(myChatRoomList);
      myChatRoomList.forEach((roomData) => {
        addChatRoom({
          id: roomData.id,
          name: roomData.name,
          users: roomData.users, //배열
        });
      });
      console.log(response.data);
      console.log(response.data.data);
      console.log(myChatRoomList);
      return myChatRoomList;
    } catch (error) {
      console.error(error);
      throw error;
    }
  });
};
