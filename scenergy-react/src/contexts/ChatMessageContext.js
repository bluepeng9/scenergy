import { createContext, useContext, useState } from "react";

const ChatMessageContext = createContext();

export const useChatMessageContext = () => useContext(ChatMessageContext);

export const ChatMessageProvider = ({ children }) => {
  const [chatMessages, setChatMessages] = useState([]);
  const [recentChatMessage, setRecentChatMessage] = useState(null);

  const addChatMessage = (newMessage) => {
    setChatMessages((prevMessages) => {
      const isDuplicate = prevMessages.some((msg) => msg.id === newMessage.id);
      if (!isDuplicate) {
        return [...prevMessages, newMessage];
      }
      return prevMessages; // 중복 메시지인 경우 이전 상태 반환
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
