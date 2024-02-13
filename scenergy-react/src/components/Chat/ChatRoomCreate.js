import axios from "axios";
import {useChatRoom} from "../../contexts/ChatRoomContext";
import {useState} from "react";
import {useNavigate} from "react-router-dom";

const ChatRoomCreate = ({ selectedUsers, isRoomCreated, setIsModalOpen }) => {
  const { addChatRoom } = useChatRoom();
  const [isCreating, setIsCreating] = useState(false);
  const navigate = useNavigate();
  const handleSubmit = async (event) => {
    event.preventDefault();
    //나중에 user 상태 받아와서 room member에 한명 자동을 넣어주고 수식 바꿔주기
    const roomStatus = selectedUsers.length > 2 ? 1 : 0; //1대1채팅, 단체채팅 상태 변경해주기
    const roomName = selectedUsers.map((user) => user.nickname).join(",");

    setIsCreating(true);
    console.log(roomName);
    console.log(selectedUsers);
    try {
      const response = await axios.post(
          process.env.REACT_APP_API_URL + "/chatroom/create-room",
        {
          chatroom_name: roomName,
          status: roomStatus,
          users: selectedUsers,
        },
        {
          headers: {
            "Content-Type": "application/json",
          },
        },
      );
      console.log(response.data.data);

      const newRoom = response.data.data;
      addChatRoom(newRoom);
      console.log(newRoom);
      console.log(newRoom.chatRoomId);
      setIsCreating(false);
      setIsModalOpen(false);
      navigate(`/chat/${newRoom.chatRoomId}`);
    } catch (error) {
      console.error("에러", error);
      setIsCreating(false);
    }
  };
  return (
    <div>
      <button
        onClick={handleSubmit}
        disabled={!selectedUsers || selectedUsers.length === 0 || isCreating}
      >
        채팅하기
      </button>
    </div>
  );
};

export default ChatRoomCreate;
