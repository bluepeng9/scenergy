import styles from "./ChatRoomReal.module.css";
import ChatConnect from "./ChatConnect";

const ChatRoomReal = () => {
  return (
    <div className={styles.ChatRoomContainer}>
      <div className={styles.ChatRoomHeader}>
        <div className={styles.ChatRoomInfo}>
          <div>
            {/*상대방 프로필 사진*/}
            <p>상대방 프로필 들어갈거임</p>
          </div>
          <div>
            {/*상대방 닉네임*/}
            <p>상대방 닉네임</p>
          </div>
        </div>
        <div className={styles.ChatRoomBtn}></div>
      </div>
      <div className={styles.ConnectContainer}>
        <ChatConnect />
      </div>
    </div>
  );
};
export default ChatRoomReal;
