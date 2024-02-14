import styles from "./ChatInfo.module.css";
import { faTimes } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { useChatRoom } from "../../contexts/ChatRoomContext";
import { useNavigate, useParams } from "react-router-dom";
import axios from "axios";
import { useEffect, useState } from "react";
import { useQueryClient } from "react-query";
import ApiUtil from "../../apis/ApiUtil";

const ChatInfo = ({ toggleInfoMenu, isOpenInfo }) => {
  const [isModified, setIsModified] = useState(false);
  const [newRoomName, setNewRoomName] = useState("");
  const { chatRooms, setSelectedRoomId, removeChatRoom } = useChatRoom();
  const { roomId } = useParams();
  const queryClient = useQueryClient();
  const realRoomId = parseInt(roomId, 10);
  const selectedChatRoom = chatRooms.find((room) => room.id === realRoomId);
  const userId = ApiUtil.getUserIdFromToken();
  const navigate = useNavigate();

  useEffect(() => {
    const realRoomId = parseInt(roomId, 10);
    setSelectedRoomId(realRoomId);
    console.log(selectedChatRoom);
  }, [roomId, setSelectedRoomId]);
  const handleExit = async () => {
    try {
      const response = await axios.get(
        process.env.REACT_APP_API_URL + "/chatroom/exit-room",
        {
          params: {
            room_id: realRoomId,
            user_id: userId,
          },
        },
      );
      console.log(response.data);
      removeChatRoom(realRoomId);
      queryClient.invalidateQueries("chatRooms");
      setSelectedRoomId(null);
      toggleInfoMenu();
      navigate("/chat");
      console.log(chatRooms);
      console.log("안녕히계세요 여러분 저는 속세 굴레 어쩌구....");
    } catch (error) {
      console.error("응 못나감", error);
    }
  };
  const handleModified = () => {
    setIsModified(!isModified);
    setNewRoomName(selectedChatRoom.name);
  };

  const handleRename = async () => {
    try {
      const response = await axios.put(
        process.env.REACT_APP_API_URL + "/chatroom/rename-room",
        {
          room_name: newRoomName,
          room_id: realRoomId,
        },
      );
      console.log(response.data);
      setIsModified(false);
      queryClient.invalidateQueries("chatRooms"); // 채팅방 목록 쿼리 무효화
    } catch (error) {
      console.error("Rename failed", error);
    }
  };

  console.log(userId, " ", realRoomId);
  return (
    <>
      <div
        className={`${styles.infoMenuContainer} ${isOpenInfo ? styles.show : ""}`}
      >
        <div className={styles.infoMenuHeaderContainer}>
          <div className={styles.infoMenuHeader}>
            {isModified ? (
              <div className={styles.editRoomName}>
                <input
                  type="text"
                  value={newRoomName}
                  onChange={(e) => setNewRoomName(e.target.value)}
                />
                <button onClick={handleRename}>저장</button>
              </div>
            ) : (
              <div className={styles.chatRoomName}>
                <p>{selectedChatRoom.name}</p>
              </div>
            )}
            <FontAwesomeIcon
              icon={faTimes}
              onClick={(e) => {
                e.stopPropagation();
                toggleInfoMenu();
              }}
            />
          </div>
        </div>
        <div className={styles.chatUserListGlobal}>
          <div className={styles.chatUsersListContainer}>
            <div className={styles.chatUsersList}>
              <ul>
                {selectedChatRoom.users.map((user) => (
                  <li key={user.id}>{user.nickname}</li>
                ))}
              </ul>
            </div>
          </div>
          <div className={styles.chatRoomOutBtn}>
            <button onClick={handleModified}>수정하기</button>
            <button onClick={handleExit}>나가기</button>
          </div>
        </div>
      </div>
    </>
  );
};
export default ChatInfo;
