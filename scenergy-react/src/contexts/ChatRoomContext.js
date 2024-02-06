import { createContext, useContext, useState } from "react";
import ChatInfo from "../components/Chat/ChatInfo";

const ChatRoomContext = createContext();

export const ChatRoomProvider = ({ children }) => {
  const [selectedRoomId, setSelectedRoomId] = useState(null);
  const [chatRooms, setChatRooms] = useState([]);

  const value = {
    selectedRoomId,
    setSelectedRoomId,
    chatRooms,
    addChatRoom: (newRoom) => {
      setChatRooms((prevChatRooms) => [newRoom, ...prevChatRooms]);
    },
  };
  return (
    <ChatRoomContext.Provider value={value}>
      {children}
    </ChatRoomContext.Provider>
  );
};

export const useChatRoom = () => useContext(ChatRoomContext);
