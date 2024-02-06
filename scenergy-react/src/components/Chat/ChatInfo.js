import styles from "./ChatInfo.css";
import { faTimes } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
const ChatInfo = ({ toggleInfoMenu }) => {
  return (
    <>
      <div className={styles.infoMenuContainer}>
        <div className={styles.infoMenuHeader} onClick={toggleInfoMenu}>
          <p>방 정보</p>
          <FontAwesomeIcon icon={faTimes} />
        </div>
      </div>
    </>
  );
};
export default ChatInfo;
