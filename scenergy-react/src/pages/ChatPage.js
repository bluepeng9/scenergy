import styles from "./ChatPage.module.css";
import Navbar from "../components/commons/Navbar/Navbar";
import ChatRoomList from "../components/Chat/ChatRoomList";
import ChatField from "../components/Chat/ChatField";
import ChatRoomReal from "../components/Chat/ChatRoomReal";
import { useLocation } from "react-router-dom";
import { useState } from "react";
import ChatInfo from "../components/Chat/ChatInfo";
import ApiUtil from "../apis/ApiUtil";
const ChatPage = () => {
  const [isOpenInfo, setIsOpenInfo] = useState(false);
  const location = useLocation();
  const userId = ApiUtil.getUserIdFromToken();
  const toggleInfoMenu = () => {
    setIsOpenInfo(!isOpenInfo);
    console.log(isOpenInfo);
  };

  return (
    <>
      <div
        className={`${styles.chatPageGlobal} ${isOpenInfo ? styles.slideLeft : ""}`}
      >
        <Navbar />
        {location.pathname === "/chat" || location.pathname === "/chat/" ? (
          <ChatField userId={userId} />
        ) : (
          <ChatRoomReal
            toggleInfoMenu={toggleInfoMenu}
            className={isOpenInfo ? styles.chatContentWithSidebar : ""}
            userId={userId}
          />
        )}
        {isOpenInfo && (
          <ChatInfo toggleInfoMenu={toggleInfoMenu} isOpenInfo={isOpenInfo} />
        )}
      </div>
    </>
  );
};

export default ChatPage;
