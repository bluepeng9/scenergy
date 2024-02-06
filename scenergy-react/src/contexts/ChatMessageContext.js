import { createContext, useContext, useState } from "react";

const ChatMessageContext = createContext();

export const useChatMessageContext = () => useContext(ChatMessageContext);

export const ChatMessageProvider = ({ children }) => {
  const [chatMessages, setChatMessages] = useState([]);
  const [recentChatMessage, setRecentChatMessage] = useState("null");

  const addChatMessage = (newMessage) => {
    setChatMessages((prevMessages) => {
      // if (Array.isArray(prevMessages)) {
      //   return [...prevMessages, newMessage];
      // } else {
      //   console.error("prev는 배열이 아니었습니다");
      //   return [newMessage];
      // }
      const updatedMessages = Array.isArray(prevMessages)
        ? [...prevMessages, newMessage]
        : [newMessage];
      console.log(newMessage);
      console.log(updatedMessages);
      return updatedMessages;
    });
    if (!recentChatMessage) {
      setRecentChatMessage(newMessage);
    }
  };

  return (
    <ChatMessageContext.Provider
      value={{
        chatMessages,
        addChatMessage,
        recentChatMessage: recentChatMessage,
        setRecentChatMessage: setRecentChatMessage,
      }}
    >
      {children}
    </ChatMessageContext.Provider>
  );
};
