import { useQuery } from "react-query";
import axios from "axios";

export const useChatMessages = (lastMessageId) => {
  return useQuery(
    ["chatMessages", { lastMessageId }],
    async () => {
      try {
        console.log(lastMessageId);
        const { data } = await axios.get(
          `http://localhost:8080/chatroom/load-message-room`,
          {
            params: { chatMessage_id: lastMessageId },
          },
        );
        console.log("성공");
        console.log("받아 온 메세지", data.data.messageList);
        return data.data.messageList.reverse();
      } catch (error) {
        throw error.response.data; //백에서 온 오류
      }
    },
    {
      enabled: !!lastMessageId,
      onError: (error) => {
        console.error("에러났다:", error);
      },
    },
  );
};
