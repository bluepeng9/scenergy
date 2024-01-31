import { useState } from "react";
import axios from "axios";

const ChatRoomCreate = ({ selectedUsers, isRoomCreated }) => {
  const handleSubmit = async (event) => {
    event.preventDefault();
    const roomStatus = selectedUsers.length > 1 ? 1 : 0; //1대1채팅, 단체채팅 상태 변경해주기
    const roomName = selectedUsers.map((user) => user.name).join(",");

    try {
      const response = await axios.post(
        "http://localhost:8080/chatroom/create-room",
        {
          "chatroom_name": roomName,
          "status": roomStatus,
          "users": selectedUsers,
        },
        {
          headers: {
            "Content-Type": "application/json",
          },
        },
      );
      isRoomCreated(true);
    } catch (error) {
      console.error("에러", error);
    }

  };
  return (
    <div>
      <button
        onClick={handleSubmit}
        disabled={!selectedUsers || selectedUsers.length === 0}
      >
        채팅하기
      </button>
    </div>
  );
};

export default ChatRoomCreate;
