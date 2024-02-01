import styles from "./ChatRoomReal.module.css";
import ChatConnect from "./ChatConnect";
import { faCircleInfo, faPlus } from "@fortawesome/free-solid-svg-icons";
import { faVideo } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { useState } from "react";
import Dialog from "../commons/Dialog/Dialog";
import axios from "axios";

const ChatRoomReal = () => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [selectedUsers, setSelectedUsers] = useState([]);
  const realRoomId = 1;
  //더미데이터
  const users = [
    { id: 1, email: "user1@test.com", password: "password1", nickname: "nickname1" },
    { id: 2, email: "user2@test.com", password: "password2", nickname: "nickname2" },
    { id: 3, email: "user3@test.com", password: "password3", nickname: "nickname3" },
    { id: 4, email: "user4@test.com", password: "password4", nickname: "nickname4" },
  ];
  const handleModalOpen = () => {
    setIsModalOpen(!isModalOpen);
    console.log(isModalOpen);
  };

  const handleUserSelect = (user) => {
    if (!selectedUsers.some((selectedUser) => selectedUser.id === user.id))
      setSelectedUsers([...selectedUsers, user]);
    console.log(selectedUsers);
  };

  const handleInvite = async (event) => {
    event.preventDefault();

    if (!selectedUsers.length === 0) {
      console.log("사용자 없는디?");
      return;
    }
    try {
      const response = await axios.post(
        "http://localhost:8080/chatroom/invite-room",
        {
          room_id: realRoomId,
          users: selectedUsers,
        },
      );
      if (response.data) {
        console.log("초대 완", response.data);
        setIsModalOpen(false);
        setSelectedUsers([]);
      }
    } catch (error) {
      console.error("에러남", error);
    }
  };

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
            {/*누르면 회원 초대*/}
            <div className={styles.UserInvite} onClick={handleModalOpen}>
              <FontAwesomeIcon icon={faPlus} />
            </div>
            <div className={styles.DoRtc}>
              <FontAwesomeIcon icon={faVideo} />
            </div>
            <div>
              <FontAwesomeIcon icon={faCircleInfo} />
            </div>
          </div>
          {isModalOpen && (
            <div className={styles.DialogContainer}>
              <form onSubmit={handleInvite}>
                <Dialog
                  isModalOpen={isModalOpen}
                  onClose={() => setIsModalOpen(false)}
                  title="초대하기"
                >
                  <div>
                    <div className={styles.RoomInviteContainer}>
                      <span>받는 사람</span>
                      <input type="text" />
                    </div>
                    <div className={styles.RoomInviteGlobal}>
                      <div className={styles.InviteListContainer}>
                        {users.map((user) => (
                          <div
                            key={user.id}
                            className={styles.InviteList}
                            onClick={() => handleUserSelect(user)}
                          >
                            <div className={styles.InviteImg}>이미지</div>
                            <div className={styles.InviteUserName}>
                              <p>{user.nickname}</p>
                            </div>
                          </div>
                        ))}
                      </div>
                      <div className={styles.InviteBtn}>
                        <button>초대하기</button>
                      </div>
                    </div>
                  </div>
                </Dialog>
              </form>
            </div>
          )}
        </div>
      </div>
      <div className={styles.ConnectContainer}>
        <ChatConnect />
      </div>
    </div>
  );
};
export default ChatRoomReal;
