import styles from "./ChatField.module.css";
import { useEffect, useState } from "react";
import Dialog from "../commons/Dialog/Dialog";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faComments } from "@fortawesome/free-regular-svg-icons";
import axios from "axios";
import ChatRoomCreate from "./ChatRoomCreate";
import { useNavigate, useParams } from "react-router-dom";
import ChatRoomReal from "./ChatRoomReal";
import { useChatRoom } from "../../contexts/ChatRoomContext";
import ChatRoomList from "./ChatRoomList";
import followApi from "../../apis/FollowApi";
import searchApi from "../../apis/SearchApi";
import ChatUserSearch from "../commons/Search/ChatUserSearch";
const ChatField = () => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [selectedUsers, setSelectedUsers] = useState([]);
  const [isCreated, setIsCreated] = useState(false);
  const { roomId } = useParams();
  const realRoomId = parseInt(roomId, 10);
  const { addChatRoom, chatRooms } = useChatRoom();
  const navigate = useNavigate();
  const fromUserId = 2;
  const users = [
    { id: 1, email: "이태경", password: "이태경", name: "사용자1" },
    { id: 2, email: "김준표", password: "이태경", name: "사용자2" },
    { id: 3, email: "홍준표", password: "이태경", name: "사용자3" },
  ];

  const handleUserSelect = (user) => {
    if (!selectedUsers.some((selectedUser) => selectedUser.id === user.id)) {
      setSelectedUsers([...selectedUsers, user]);
    }
  };

  const handleSubmit = (event) => {
    event.preventDefault();
  };

  const handleOpenModal = () => {
    setIsModalOpen(true);
  };

  const handleCloseModal = () => {
    setIsModalOpen(false);
  };

  const handleCancel = (userId) => {
    setSelectedUsers(selectedUsers.filter((user) => user.id !== userId));
  };

  return (
    <>
      <ChatRoomList sytle={{ width: "30%", flex: "1" }} />
      <div className={styles.fieldGlobal}>
        <div className={styles.fieldBody} onClick={handleOpenModal}>
          <FontAwesomeIcon className={styles.fieldBodyIcon} icon={faComments} />
          <p>원하는 뮤지션에게 메세지를 보내보세요.</p>
          <button>뮤지션 찾기</button>
        </div>
        {isModalOpen && (
          <ChatUserSearch
            isOpen={isModalOpen}
            onClose={handleCloseModal}
            onUserSelect={handleUserSelect}
            fromUserId={fromUserId}
          />
        )}
      </div>
      {isCreated && <ChatRoomReal />}
    </>
  );
};

export default ChatField;
