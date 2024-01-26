import ChatRoomList from "../components/Chat/ChatRoomList";
import ChatField from "../components/Chat/ChatField";
import styles from "./ChatPage.module.css";
import Navbar from "../components/commons/Navbar/Navbar";
function ChatPage() {
  return (
    <>
      <div className={styles.ChatPageGlobal}>
        <Navbar />
        <ChatRoomList />
        <ChatField />
      </div>
    </>
  );
}
export default ChatPage;
