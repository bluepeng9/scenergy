import {useQuery} from "react-query";
import axios from "axios";

export const useChatMessages = (lastMessageId, lastMessage) => {
  return useQuery(
    ["chatMessages", { lastMessageId }],
    async () => {
      try {
        const { data } = await axios.get(
            process.env.REACT_APP_API_URL + `/chatroom/load-message-room`,
          {
            params: { chatMessage_id: lastMessageId },
          },
        );
        console.log("성공");
        console.log("받아 온 메세지", data.data.messageList);

        let messages = data.data.messageList;

        if (lastMessage && !messages.find((m) => m.id === lastMessage.id)) {
          messages = [lastMessage, ...messages];
        }
        return messages.reverse();
      } catch (error) {
        throw error.response.data; //백에서 온 오류
      }
    },
    {
      enabled: !!lastMessageId,
      keepPreviousData: true,
      onError: (error) => {
        console.error("에러났다:", error);
      },
    },
  );
};
