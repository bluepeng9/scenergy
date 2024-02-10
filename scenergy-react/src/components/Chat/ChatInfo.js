import styles from "./ChatInfo.module.css";
import { faTimes } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { useChatRoom } from "../../contexts/ChatRoomContext";
import { useNavigate, useParams } from "react-router-dom";
import axios from "axios";
import { useEffect } from "react";
import { useQueryClient } from "react-query";

const ChatInfo = ({ toggleInfoMenu, isOpenInfo }) => {
  const { chatRooms, setSelectedRoomId, removeChatRoom } = useChatRoom();
  const { roomId } = useParams();
  const queryClient = useQueryClient();

  const realRoomId = parseInt(roomId, 10);
  const selectedChatRoom = chatRooms.find((room) => room.id === realRoomId);
  const userId = 2;
  const navigate = useNavigate();

  useEffect(() => {
    const realRoomId = parseInt(roomId, 10);
    setSelectedRoomId(realRoomId);
  }, [roomId, setSelectedRoomId]);
  const handleExit = async () => {
    try {
      const response = await axios.get(
        "http://localhost:8080/chatroom/exit-room",
        {
          params: {
            room_id: realRoomId,
            user_id: userId,
          },
        },
      );
      console.log(response.data);
      console.log(chatRooms);
      removeChatRoom(realRoomId);
      queryClient.invalidateQueries("chatRooms");
      setSelectedRoomId(null);
      toggleInfoMenu();
      navigate("/chat");
      console.log(chatRooms);
      console.log("안녕히계세요 여러분 저는 속세 굴레 어쩌구....");
    } catch (error) {
      console.error("응 못나감", error);
    }
  };

  console.log(userId, " ", realRoomId);
  return (
    <>
      <div
        className={`${styles.infoMenuContainer} ${isOpenInfo ? styles.show : ""}`}
      >
        <header
          className={styles.infoMenuHeaderContainer}
          onClick={toggleInfoMenu}
        >
          <div className={styles.infoMenuHeader}>
            <p>{selectedChatRoom.name}</p>
            <FontAwesomeIcon icon={faTimes} />
          </div>
        </header>
      </div>
      <div>
        <div className={styles.chatUsersListContainer}>
          <div className={styles.chatUsersList}>
            <ul>
              {selectedChatRoom.users.map((user) => (
                <li key={user.id}>{user.nickname}</li>
              ))}
            </ul>
          </div>
        </div>
      </div>
      <section>
        <button onClick={handleExit}>나가기</button>
      </section>
    </>
  );
};
export default ChatInfo;
