import React, { useState } from "react";
import axios from "axios";
import Dialog from "../commons/Dialog/Dialog";
import { useLocation, useParams } from "react-router-dom";
// import VideoConference from "./VideoConference";
import styles from "./ChatRoomReal.module.css";
import ChatConnect from "./ChatConnect";
import {
  faCircleInfo,
  faPlus,
  faVideo,
} from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import ChatRoomList from "./ChatRoomList";

const ChatRoomReal = ({ toggleInfoMenu }) => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isRtcConnect, setIsRtcConnect] = useState(false);
  const [selectedUsers, setSelectedUsers] = useState([]);
  // 나중에 user까지 다 받아오면
  const location = useLocation();
  const lastMessageId = location.state?.lastMessageId;
  const { roomId } = useParams();
  const realRoomId = parseInt(roomId, 10);

  const usernickname = "사용자2";
  const users = [
    {
      id: 1,
      email: "user1@test.com",
      password: "password1",
      nickname: "nickname1",
    },
    {
      id: 2,
      email: "user2@test.com",
      password: "password2",
      nickname: "nickname2",
    },
    {
      id: 3,
      email: "user3@test.com",
      password: "password3",
      nickname: "nickname3",
    },
    {
      id: 4,
      email: "user4@test.com",
      password: "password4",
      nickname: "nickname4",
    },
  ];
  const handleModalOpen = () => setIsModalOpen(!isModalOpen);

  const handleUserSelect = (user) => {
    if (!selectedUsers.some((selectedUser) => selectedUser.id === user.id))
      setSelectedUsers((prevUsers) => [...prevUsers, user]);
    console.log(selectedUsers);
  };

  const handleInvite = async (event) => {
    event.preventDefault();

    if (!selectedUsers.length === 0) {
      console.log("사용자 없는디?");
      return;
    }
    try {
      //room_id useParam으로 받아온거 고쳐주기
      const response = await axios.post(
        "http://localhost:8080/chatroom/invite-room",
        {
          room_id: roomId,
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

  const handleConnectRtc = () => {
    setIsRtcConnect(!isRtcConnect);
  };

  return (
    <>
      <ChatRoomList />
      <div className={styles.chatRoomContainer}>
        <div className={styles.chatRoomHeader}>
          <div className={styles.chatRoomInfo}>
            <div className={styles.chatRoomProfileContainer}>
              <div className={styles.chatRoomProfile}>
                {/*상대방 프로필 사진*/}
                <p>프로필</p>
              </div>
              <div className={styles.chatRoomUserName}>
                {/*상대방 닉네임*/}
                <p>상대방 닉네임</p>
              </div>
            </div>
            <div className={styles.chatRoomIcon}>
              {/*누르면 회원 초대*/}
              <div className={styles.userInvite} onClick={handleModalOpen}>
                <FontAwesomeIcon icon={faPlus} />
              </div>
              <div className={styles.doRtc} onClick={handleConnectRtc}>
                <FontAwesomeIcon icon={faVideo} />
                {/*{isRtcConnect && (*/}
                {/*  <div>*/}
                {/*    <VideoConference*/}
                {/*      mySessionId={roomId}*/}
                {/*      myUserName={usernickname}*/}
                {/*    />*/}
                {/*  </div>*/}
                {/*)}*/}
              </div>
              <div className={styles.RoomInfo} onClick={toggleInfoMenu}>
                <FontAwesomeIcon icon={faCircleInfo} />
              </div>
            </div>
            {/*현재 채팅방에 사용자 초대=>초대되는 사람 포함 3명 이상이면 roomstate 바꿔주고 새로 방 만들기*/}
            {isModalOpen && (
              <div className={styles.dialogContainer}>
                <form onSubmit={handleInvite}>
                  <Dialog
                    isModalOpen={isModalOpen}
                    onClose={() => setIsModalOpen(false)}
                    title="초대하기"
                  >
                    <div>
                      <div className={styles.roomInviteContainer}>
                        <span>받는 사람</span>
                        <input type="text" />
                      </div>
                      <div className={styles.roomInviteGlobal}>
                        <div className={styles.inviteListContainer}>
                          {users.map((user) => (
                            <div
                              key={user.id}
                              className={styles.inviteList}
                              onClick={() => handleUserSelect(user)}
                            >
                              <div className={styles.inviteImg}>이미지</div>
                              <div className={styles.inviteUserName}>
                                <p>{user.nickname}</p>
                              </div>
                            </div>
                          ))}
                        </div>
                        <div className={styles.inviteBtn}>
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
        <div className={styles.connectContainer}>
          <ChatConnect lastMessageId={lastMessageId} />
        </div>
      </div>
    </>
  );
};
export default ChatRoomReal;
