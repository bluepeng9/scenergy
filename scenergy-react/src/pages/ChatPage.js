import styles from "./ChatPage.module.css";
import Navbar from "../components/commons/Navbar/Navbar";
import ChatRoomList from "../components/Chat/ChatRoomList";
import ChatField from "../components/Chat/ChatField";
import ChatRoomReal from "../components/Chat/ChatRoomReal";
import { useLocation, useNavigate } from "react-router-dom";
const ChatPage = () => {
  const location = useLocation();

  return (
    <>
      <div className={styles.chatPageGlobal}>
          <Navbar/>
        <ChatRoomList sytle={{width:"30%", flex:"1"}}/>
        {location.pathname === "/chat"||location.pathname === "/chat/" ? <ChatField /> : <ChatRoomReal />}
      </div>
    </>
  );
};


export default ChatPage;
