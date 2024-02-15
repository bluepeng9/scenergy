import styles from "./ChatRoomList.module.css";
import { Link, useNavigate } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPenToSquare } from "@fortawesome/free-solid-svg-icons";
import { useEffect, useState } from "react";
import { useChatRoom } from "../../contexts/ChatRoomContext";
import { useChatRooms } from "../../hooks/useChatRooms";
import ChatUserSearch from "../commons/Search/ChatUserSearch";
import ApiUtil from "../../apis/ApiUtil";
import UserApi from "../../apis/User/UserApi";

const ChatRoomList = () => {
  const userId = ApiUtil.getUserIdFromToken();
  const { data: chatRooms, isLoading, isError, error } = useChatRooms(userId);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [selectedUsers, setSelectedUsers] = useState([]);
  const { chatRooms: contextChatRooms, setChatRooms } = useChatRoom();
  const [userNickname, setUserNickname] = useState("");
  const [clickedRoomId, setClickedRoomId] = useState(null); // 클릭된 방의 ID를 저장하는 상태 추가

  const handleCloseModal = () => {
    setIsModalOpen(false);
  };

  const handleUserSelect = (user) => {
    if (!selectedUsers.some((selectedUser) => selectedUser.id === user.id)) {
      setSelectedUsers([...selectedUsers, user]);
    }
  };

  const handleRoomClick = (roomId) => {
    setClickedRoomId(roomId); // 클릭된 방의 ID를 상태에 저장
  };

  const handleOpenModal = () => {
    setIsModalOpen(true);
  };

  useEffect(() => {
    const getUserData = async () => {
      try {
        const userData = await UserApi.getUser();
        userData.userNickname
          ? setUserNickname(userData.userNickname)
          : setUserNickname("닉네임을 설정해주세요");
      } catch (error) {
        console.error("유저 정보 불러오기 실패", error);
      }
    };
    getUserData();
  }, [userId]);

  useEffect(() => {
    if (chatRooms) {
      setChatRooms(chatRooms);
    }
  }, [chatRooms, setChatRooms]);
  console.log(contextChatRooms);

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
                lastMessage: room.recentChatMessage,
              }}
              to={`/chat/${room.id}`}
              className={`${styles.listItemContainer} ${room.id === clickedRoomId ? styles.active : ""}`} // 클릭된 방에 active 클래스 추가
              onClick={() => handleRoomClick(room.id)} // 클릭 이벤트 추가
            >
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
