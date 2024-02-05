import { createContext, useContext, useState } from "react";

const ChatMessageContext = createContext();

export const useChatMessageContext = () => useContext(ChatMessageContext);

export const ChatMessageProvider = ({ children }) => {
  const [chatMessages, setChatMessages] = useState([]);

  const addChatMessage = (newMessage) => {
    setChatMessages((prevMessages) => {
      if (Array.isArray(prevMessages)) {
        return [...prevMessages, newMessage];
      } else {
        console.error("prev는 배열이 아니었습니다");
        return [newMessage];
      }
    });
  };

  return (
    <ChatMessageContext.Provider value={{ chatMessages, addChatMessage }}>
      {children}
    </ChatMessageContext.Provider>
  );
};
