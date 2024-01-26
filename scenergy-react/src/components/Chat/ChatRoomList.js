import styles from "./ChatRoomList.module.css";
const ChatRoomList = () => {
  /*list 불러올 함수 작성*/
  const user_nickname = "닉네임";
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
          {/*내가 참여하는 채팅방 list 불러오기*/}
          <div className={styles.ListContainer}>
            <div className="ListUserProfileImg">
              {/*user profile 사진
          <img>*/}
              <h3>이미지</h3>
            </div>
            <div className="ListContentContainer">
              <div className="ListRoomTitle">
                {/*상대방 nickname*/}
                <p>상대방 nickname</p>
              </div>
              <div className="ListChatLatestContent">
                <p>마지막 채팅</p>
              </div>
            </div>
          </div>
        </div>
      </div>
    </>
  );
};
export default ChatRoomList;
