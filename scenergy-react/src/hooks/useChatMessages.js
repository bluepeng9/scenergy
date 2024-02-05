import { useQuery } from "react-query";
import axios from "axios";

export const useChatMessages = (lastMessageId) => {
  return useQuery(
    ["chatMessages", { lastMessageId }],
    async () => {
      console.log(lastMessageId);
      try {
        const { data } = await axios.get(
          `http://localhost:8080/chatroom/load-message-room`,
          {
            params: { chatMessage_id: lastMessageId },
          },
        );
        console.log(data);
        console.log(data.data);
        console.log(data.data.messageList);
        console.log(lastMessageId);
        return data.data.messageList;
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
