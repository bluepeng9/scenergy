import styles from "./ChatRoomList.module.css";
import { Link } from "react-router-dom";
import { useChatRooms } from "../../hooks/useChatRooms";

//userId나중에 {userId}로 넣어줘야됨
const userId = 2;
const ChatRoomList = () => {
  const { data: chatRooms, isLoading, isError, error } = useChatRooms(userId);
  const userNickname = "uniquenickname2";
  // const userId = 2;
  if (isLoading) return <div>로딩중..</div>;
  if (isError) return <div>Error:{error?.message}</div>;
  return (
    <div className={styles.roomListGlobal}>
      <div className={styles.roomListHeader}>
        <div className={styles.listUserNickname}>
          <h3>{userNickname}</h3>
        </div>
        <div className={styles.message}>
          <h4>메시지</h4>
        </div>
      </div>
      <div className={styles.roomListContainer}>
        {chatRooms.map((room) => (
          <li key={room.id} className={styles.listContainer}>
            <Link
              state={{
                lastMessageId: room.firstChatMessage
                  ? room.firstChatMessage.id
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
                    {room.firstChatMessage
                      ? room.firstChatMessage.messageText
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
