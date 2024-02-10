import styles from "./ChatRoomList.module.css";
import { Link, useNavigate } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPenToSquare } from "@fortawesome/free-solid-svg-icons";
import { useEffect, useState } from "react";
import Dialog from "../commons/Dialog/Dialog";
import ChatRoomCreate from "./ChatRoomCreate";
import axios from "axios";
import { useChatRoom } from "../../contexts/ChatRoomContext";
import { useChatRooms } from "../../hooks/useChatRooms";
import ChatUserSearch from "../commons/Search/ChatUserSearch";

//userId나중에 {userId}로 넣어줘야됨
const ChatRoomList = () => {
  const userId = 2;
  const { data: chatRooms, isLoading, isError, error } = useChatRooms(userId);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [searchInput, setSearchInput] = useState("");
  const [selectedUsers, setSelectedUsers] = useState([]);
  const [searchResults, setSearchResults] = useState([]);
  const [isCreated, setIsCreated] = useState(false);
  const {
    addChatRoom,
    chatRooms: contextChatRooms,
    setChatRooms,
  } = useChatRoom();
  const navigate = useNavigate();
  const userNickname = "uniquenickname2";
  const users = [
    { id: 1, email: "이태경", password: "이태경", name: "사용자1" },
    { id: 2, email: "김준표", password: "이태경", name: "사용자2" },
    { id: 3, email: "홍준표", password: "이태경", name: "사용자3" },
  ];

  const handleCloseModal = () => {
    setIsModalOpen(false);
  };

  const handleInputChange = (event) => {
    setSearchInput(event.target.value);
  };

  const handleSearch = async () => {
    try {
      const response = await axios.get(`/api/search?query=${searchInput}`);
      setSearchResults(response.data);
    } catch (error) {
      console.error("에러", error);
    }
  };

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

  const handleRoomCreate = async (realRoomId) => {
    setIsCreated(true);
    setIsModalOpen(false);
    const newRoom = chatRooms.find((room) => room.id === realRoomId);
    if (newRoom) {
      addChatRoom(newRoom);
    }
    navigate(`/chat/${realRoomId}`);
  };

  useEffect(() => {
    if (chatRooms) {
      setChatRooms(chatRooms);
    }
  }, [chatRooms, setChatRooms]);

  if (isLoading) return <div>로딩중..</div>;
  if (isError) return <div>Error:{error?.message}</div>;

  return (
    <div className={styles.roomListGlobal}>
      <div className={styles.roomListHeader}>
        <div className={styles.roomUserContainer}>
          <div className={styles.listUserNickname}>
            <h3>{userNickname}</h3>
          </div>
          <div className={styles.roomCreateContainer} onClick={handleOpenModal}>
            <p className={styles.roomCreateBtn}>
              <FontAwesomeIcon icon={faPenToSquare} />
            </p>
          </div>
          {isModalOpen && (
            <ChatUserSearch
              isOpen={isModalOpen}
              onClose={handleCloseModal}
              onUserSelect={handleUserSelect}
              fromUserId={userId}
            />
          )}
        </div>
        <div className={styles.message}>
          <h4>메시지</h4>
        </div>
      </div>
      <div className={styles.roomListContainer}>
        {contextChatRooms.map((room) => (
          <li key={room.id} className={styles.listContainer}>
            <Link
              state={{
                lastMessageId: room.recentChatMessage
                  ? room.recentChatMessage.id
                  : null,
              }}
              to={`/chat/${room.id}`}
              className={styles.listItemContainer}
            >
              <div className={styles.listUserProfileImg}>
                {/* user profile 사진 */}
                {/* <img> */}
                <p>이미지</p>
              </div>
              <div className={styles.listContentContainer}>
                <div className={styles.listRoomTitle}>
                  <p>{room.name}</p>
                </div>
                <div className={styles.listChatLatestContent}>
                  <p className={styles.chatLastMsg}>
                    {room.recentChatMessage
                      ? room.recentChatMessage.messageText
                      : "No messages"}
                  </p>
                </div>
              </div>
            </Link>
          </li>
        ))}
      </div>
    </div>
  );
};

export default ChatRoomList;
