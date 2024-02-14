import React, {useEffect, useState} from "react";
import axios from "axios";
import {useLocation, useParams} from "react-router-dom";
import VideoConference from "./VideoConference";
import styles from "./ChatRoomReal.module.css";
import ChatConnect from "./ChatConnect";
import {faCircleInfo, faPlus, faVideo,} from "@fortawesome/free-solid-svg-icons";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import ChatRoomList from "./ChatRoomList";
import {useChatRooms} from "../../hooks/useChatRooms";
import ChatUserSearch from "../commons/Search/ChatUserSearch";
import ApiUtil from "../../apis/ApiUtil";
import ChatRoomApi from "../../apis/ChatRoomApi";

const ChatRoomReal = ({ toggleInfoMenu, userId }) => {
  const [chatRoomUsers, setChatRoomUsers] = useState([]);
  const [chatRoomUsersSeq, setChatRoomUsersSeq] = useState([]);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isRtcConnect, setIsRtcConnect] = useState(false);
  const [selectedUsers, setSelectedUsers] = useState([]);
  const location = useLocation();
  const lastMessageId = location.state?.lastMessageId;
  const lastMessage = location.state?.lastMessage;
  const { roomId } = useParams();
  const realRoomId = parseInt(roomId, 10);
  const { refetch } = useChatRooms(userId);
  const [connectUserId, setConnectUserId] = useState(0);


  useEffect(() => {
    const getConnectUserId = async () => {
      try {
        const response = await ChatRoomApi.getUserInfo(realRoomId, userId);
        setConnectUserId(response.data.data.userId);
      } catch (error) {
        console.error("Connect user info fetching error:", error);
      }
    };

    getConnectUserId();
  }, [realRoomId, userId]);

  const handleModalOpen = () => setIsModalOpen(!isModalOpen);

  const handleUserSelect = (user) => {
    if (!selectedUsers.some((selectedUser) => selectedUser.id === user.id))
      setSelectedUsers((prevUsers) => [...prevUsers, user]);
    console.log(selectedUsers);
  };

  const getChatUsers = async (event) => {
    event.preventDefault();

    if (!selectedUsers.length === 0) {
      console.log("사용자 없는디?");
      return;
    }
    try {
      //room_id useParam으로 받아온거 고쳐주기
      const response = await axios.post(
          process.env.REACT_APP_API_URL + "/chatroom/invite-room",
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

  const handleInvite = async (event) => {
    event.preventDefault();

    if (!selectedUsers.length === 0) {
      console.log("사용자 없는디?");
      return;
    }
    try {
      //room_id useParam으로 받아온거 고쳐주기
      const response = await axios.post(
          process.env.REACT_APP_API_URL + "/chatroom/invite-room",
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
  console.log(lastMessageId);

  useEffect(() => {
  }, []);

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
            <VideoConference
                chatRoomId={realRoomId}
                chatRoomUsers={chatRoomUsers}
                chatRoomUsersSeq={chatRoomUsersSeq}
                userId={ApiUtil.getUserIdFromToken()}
                connectUserId={connectUserId}
            />
            <div className={styles.chatRoomIcon}>
              {/*누르면 회원 초대*/}
              <div className={styles.userInvite} onClick={handleModalOpen}>
                <FontAwesomeIcon icon={faPlus} />
              </div>

              <div className={styles.doRtc} onClick={handleConnectRtc}>
                <FontAwesomeIcon icon={faVideo} />
                {isRtcConnect && <div></div>}
              </div>
              <div className={styles.RoomInfo} onClick={toggleInfoMenu}>
                <FontAwesomeIcon icon={faCircleInfo} />
              </div>
            </div>
            {/*현재 채팅방에 사용자 초대=>초대되는 사람 포함 3명 이상이면 roomstate 바꿔주고 새로 방 만들기*/}
            {isModalOpen && (
              <ChatUserSearch
                isOpen={isModalOpen}
                onClose={() => setIsModalOpen(false)}
                fromUserId={userId}
                onUserSelect={handleUserSelect}
              />
            )}
          </div>
        </div>
        <div className={styles.connectContainer}>
          <ChatConnect
            lastMessageId={lastMessageId}
            refetchChatRooms={refetch}
            lastMessage={lastMessage}
          />
        </div>
      </div>
    </>
  );
};
export default ChatRoomReal;
