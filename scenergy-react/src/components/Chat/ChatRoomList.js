import styles from "./ChatRoomList.module.css";
import { useEffect, useState } from "react";
import axios from "axios";
import { Link, useNavigate } from "react-router-dom";

const ChatRoomList = () => {
  const [chatRooms, setChatRooms] = useState([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();
  const userNickname = "uniquenickname2";
  const userId = 2;

  useEffect(() => {
    axios
        .get("http://localhost:8080/chatroom/list-mychatroom", {
          params: { user_id: userId },
        })
        .then((response) => {
          const chatRoomData = response.data.data || [];
          console.log(response.data.data.myChatRoomList);
          setChatRooms(chatRoomData.myChatRoomList);
        })
        .catch((error) => {
          console.error("에러남", error);
        })
        .finally(() => {
          console.log(loading);
          setLoading(false);
        });
  }, [userId]);

  useEffect(() => {
    console.log(chatRooms);
  }, []);

  if (!chatRooms) {
    return null;
  }

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
          {chatRooms.length > 0 ? (
              chatRooms.map((room) => (
                  <li key={room.id} className={styles.listContainer}>
                    <Link to={`/chat/${room.id}`} className={styles.listItemContainer}>
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
                            {room.firstChatMessage.messageText}
                          </p>
                        </div>
                      </div>
                    </Link>
                  </li>
              ))
          ) : (
              <p>로딩 중...</p>
          )}
        </div>
      </div>
  );
};

export default ChatRoomList;