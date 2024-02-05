import axios from "axios";
import { useQuery } from "react-query";

export const useChatRooms = (userId) => {
  return useQuery(["chatRooms", userId], async () => {
    try {
      const response = await axios.get(
          "http://localhost:8080/chatroom/list-mychatroom",
          {
            params: { user_id: userId },
          }
      );
      console.log(response.data)
      console.log(response.data.data)
      return response.data.data.myChatRoomList;
    } catch (error) {
      console.error(error);
      throw error;
    }
  });
};
