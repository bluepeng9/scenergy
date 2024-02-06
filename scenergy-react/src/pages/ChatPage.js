import styles from "./ChatPage.module.css";
import Navbar from "../components/commons/Navbar/Navbar";
import ChatRoomList from "../components/Chat/ChatRoomList";
import ChatField from "../components/Chat/ChatField";
import ChatRoomReal from "../components/Chat/ChatRoomReal";
import { useLocation } from "react-router-dom";
import { useState } from "react";
import ChatInfo from "../components/Chat/ChatInfo";
const ChatPage = () => {
  const [isOpenInfo, setIsOpenInfo] = useState(false);
  const location = useLocation();
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
        <ChatRoomList sytle={{ width: "30%", flex: "1" }} />
        {location.pathname === "/chat" || location.pathname === "/chat/" ? (
          <ChatField />
        ) : (
          <ChatRoomReal
            toggleInfoMenu={toggleInfoMenu}
            className={isOpenInfo ? styles.chatContentWithSidebar : ""}
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
