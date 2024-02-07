import { createContext, useContext, useState } from "react";
import ChatInfo from "../components/Chat/ChatInfo";

const ChatRoomContext = createContext();

export const ChatRoomProvider = ({ children }) => {
  const [selectedRoomId, setSelectedRoomId] = useState(null);
  const [chatRooms, setChatRooms] = useState([]);

  const addChatRoom = (newRoom) => {
    setChatRooms((prevChatRooms) => {
      const isExist = prevChatRooms.some((room) => room.id === newRoom.id);
      console.log(prevChatRooms);
      console.log(newRoom.id);
      if (!isExist) {
        console.log(newRoom);
        return [newRoom, ...prevChatRooms];
      }
      return prevChatRooms;
    });
  };

  const removeChatRoom = (roomId) => {
    console.log(chatRooms);
    console.log(roomId);
    setChatRooms((prevRooms) => prevRooms.filter((room) => room.id !== roomId));
    console.log(chatRooms);
  };

  const value = {
    selectedRoomId,
    setSelectedRoomId,
    chatRooms,
    addChatRoom,
    removeChatRoom,
  };
  console.log(chatRooms);
  return (
    <ChatRoomContext.Provider value={value}>
      {children}
    </ChatRoomContext.Provider>
  );
};

export const useChatRoom = () => useContext(ChatRoomContext);
