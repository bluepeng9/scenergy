import axios from "axios";

const ChatRoomCreate = ({ selectedUsers, isRoomCreated, setIsModalOpen }) => {
  const handleSubmit = async (event) => {
    event.preventDefault();
    //나중에 user 상태 받아와서 room member에 한명 자동을 넣어주고 수식 바꿔주기
    const roomStatus = selectedUsers.length > 1 ? 1 : 0; //1대1채팅, 단체채팅 상태 변경해주기
    const roomName = selectedUsers.map((user) => user.name).join(",");

    try {
      const response = await axios.post(
        "http://localhost:8080/chatroom/create-room",
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
        const chatRoomNumber = response.data.data.chatRoomId;
        console.log(chatRoomNumber)
        isRoomCreated(chatRoomNumber);
      setIsModalOpen(false)
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
