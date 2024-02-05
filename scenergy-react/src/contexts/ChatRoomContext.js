import { createContext, useContext, useState } from "react";

const ChatRoomContext = createContext();

export const ChatRoomProvider = ({ children }) => {
  const [selectedRoomId, setSelectedRoomId] = useState(null);
  const [chatRooms, setChatRooms] = useState([]);

  const value = {
    selectedRoomId,
    setSelectedRoomId,
    chatRooms,
    addChatRoom: (newRoom) => {
      setChatRooms((prevChatRooms) => [...prevChatRooms, newRoom]);
    },
  };
  return (
    <ChatRoomContext.Provider value={value}>
      {children}
    </ChatRoomContext.Provider>
  );
};

export const useChatRoom = () => useContext(ChatRoomContext);
