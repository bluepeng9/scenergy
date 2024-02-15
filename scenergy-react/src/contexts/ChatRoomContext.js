import { createContext, useContext, useState } from "react";

const ChatRoomContext = createContext();

export const ChatRoomProvider = ({ children }) => {
  const [selectedRoomId, setSelectedRoomId] = useState(null);
  const [chatRooms, setChatRooms] = useState([]);

  const addChatRoom = (newRoom) => {
    setChatRooms((prevChatRooms) => {
      const isExist = prevChatRooms.some((room) => room.id === newRoom.id);
      if (!isExist) {
        console.log(newRoom);
        return [newRoom, ...prevChatRooms];
      }
      return prevChatRooms;
    });
  };

  const removeChatRoom = (roomId) => {
    setChatRooms((prevRooms) => prevRooms.filter((room) => room.id !== roomId));
    console.log(chatRooms);
  };

  //채팅방 최근 메세지 추가
  const updateRecentMessage = (roomId, newMessage) => {
    setChatRooms((prevRooms) =>
      prevRooms.map((room) =>
        room.id === roomId ? { ...room, recentChatMessage: newMessage } : room,
      ),
    );
  };

  const updateLastMessageId = (roomId, newLastMessageId) => {
    setChatRooms((prevRooms) =>
      prevRooms.map((room) => {
        if (room.id === roomId) {
          return { ...room, newLastMessageId: newLastMessageId };
        }
        return room;
      }),
    );
  };

  const value = {
    selectedRoomId,
    setSelectedRoomId,
    chatRooms,
    setChatRooms,
    addChatRoom,
    removeChatRoom,
    updateRecentMessage,
    updateLastMessageId,
  };
  console.log(chatRooms);
  return (
    <ChatRoomContext.Provider value={value}>
      {children}
    </ChatRoomContext.Provider>
  );
};

export const useChatRoom = () => useContext(ChatRoomContext);
