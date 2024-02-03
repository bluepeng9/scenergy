import styles from "./ChatConnect.module.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faArrowUp } from "@fortawesome/free-solid-svg-icons";

const ChatInput = ({chat, handleChange, handleSubmit}) => {
  return (
    <form onSubmit={(event) => handleSubmit(event, chat)}>
      <div className={styles.chatInput}>
        <textarea
          tabIndex="0"
          rows={1}
          name={"chatInput"}
          onChange={handleChange}
          value={chat}
          placeholder="메세지 보내기"
          onKeyDown={(e) => {
            if (e.keyCode === 13) {
              e.preventDefault();
              handleSubmit(e, chat);
            }
          }}
        />
        <div className={styles.chatSendBtn}>
          <button>
            <FontAwesomeIcon icon={faArrowUp} />
          </button>
        </div>
      </div>
    </form>
  );
};

export default ChatInput;
