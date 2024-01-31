import styles from "./ChatRoomReal.module.css";
import ChatConnect from "./ChatConnect";
import { faPlus } from "@fortawesome/free-solid-svg-icons";
import { faVideo } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";

const ChatRoomReal = () => {
  return (
    <div className={styles.ChatRoomContainer}>
      <div className={styles.ChatRoomHeader}>
        <div className={styles.ChatRoomInfo}>
          <div className={styles.chatRoomProfileContainer}>
            <div className={styles.ChatRoomProfile}>
              {/*상대방 프로필 사진*/}
              <p>프로필</p>
            </div>
            <div className={styles.ChatRoomUserName}>
              {/*상대방 닉네임*/}
              <p>상대방 닉네임</p>
            </div>
          </div>
          <div className={styles.ChatRoomIcon}>
            <div className={styles.UserInvite}>
              <FontAwesomeIcon icon={faPlus} />
            </div>
            <div className={styles.DoRtc}>
              <FontAwesomeIcon icon={faVideo} />
            </div>
          </div>
        </div>
      </div>
      <div className={styles.ConnectContainer}>
        <ChatConnect />
      </div>
    </div>
  );
};
export default ChatRoomReal;
