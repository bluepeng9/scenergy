import styles from "./ChatInfo.module.css";
import { faTimes } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { useChatRoom } from "../../contexts/ChatRoomContext";
import { useParams } from "react-router-dom";
const ChatInfo = ({ toggleInfoMenu, isOpenInfo }) => {
  const { chatRooms, setSelectedRoomId } = useChatRoom();
  const { roomId } = useParams();
  const realRoomId = parseInt(roomId, 10);
  setSelectedRoomId(realRoomId);
  const selectedChatRoom = chatRooms.find((room) => room.id === realRoomId);
  console.log(selectedChatRoom);
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
      <body>
        <div className={styles.chatUsersListContainer}>
          <div className={styles.chatUsersList}>
            <ul>
              {selectedChatRoom.users.map((user) => (
                <li key={user.id}>{user.nickname}</li>
              ))}
            </ul>
          </div>
        </div>
      </body>
      <section>
        <button>나가기</button>
      </section>
    </>
  );
};
export default ChatInfo;
