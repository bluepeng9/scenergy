import styles from "./ChatRoomList.module.css";
import { useEffect, useState } from "react";
import axios from "axios";

const ChatRoomList = () => {
  /*list 불러올 함수 작성*/
  const [chatRooms, setChatRooms] = useState([]);
  const [loading, setLoading] = useState(true);
  const user_nickname = "uniquenickname2";
  const userId = 2;

  useEffect(() => {
    axios
      .get("http://localhost:8080/chatroom/list-mychatroom", {
        params: { user_id: userId },
      })
      .then((response) => {
        // const chatRoomData = response.data.myChatRoomList || [];
        const chatRoomData = response.data.data.myChatRoomList || [];
        setChatRooms(chatRoomData);
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
    console.log(chatRooms.length > 0);
  }, [chatRooms]);
  if (!chatRooms) {
    return null;
  }

  return (
    <>
      <div className={styles.roomListGlobal}>
        <div className={styles.roomListHeader}>
          <div className={styles.ListUserNickname}>
            <h3>{user_nickname}</h3>
          </div>
          <div className={styles.Message}>
            <h4>메시지</h4>
          </div>
        </div>
        <div className={styles.roomListContainer}>
          {chatRooms.length > 0 ? ( // 데이터가 있을 때만 렌더링
            chatRooms.map((room) => (
              <div key={room.id} className={styles.ListContainer}>
                <div className={styles.ListItemContainer}>
                  <div className={styles.ListUserProfileImg}>
                    {/*user profile 사진*/}
                    {/* <img> */}
                    <h3>이미지</h3>
                  </div>
                  <div className={styles.ListContentContainer}>
                    <div className={styles.ListRoomTitle}>
                      <p>{room.name}</p>
                    </div>

                    <div className={styles.ListChatLatestContent}>
                      <p>
                        {room.chatMessages.length > 0 && (
                          <p>
                            {/* 마지막 채팅 */}
                            {
                              room.chatMessages[room.chatMessages.length - 1]
                                .content
                            }
                          </p>
                        )}
                      </p>
                    </div>
                  </div>
                </div>
              </div>
            ))
          ) : (
            <p>로딩 중...</p> // 데이터가 로드되기 전에 표시할 내용
          )}
        </div>
      </div>
    </>
  );
};
export default ChatRoomList;
