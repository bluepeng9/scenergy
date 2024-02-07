import styles from "./ChatRoomList.module.css";
import { Link, useNavigate } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPenToSquare } from "@fortawesome/free-solid-svg-icons";
import { useEffect, useState } from "react";
import Dialog from "../commons/Dialog/Dialog";
import ChatRoomCreate from "./ChatRoomCreate";
import axios from "axios";
import { useChatRoom } from "../../contexts/ChatRoomContext";
import { useChatRooms } from "../../hooks/useChatRooms";

//userId나중에 {userId}로 넣어줘야됨
const userId = 2;
const ChatRoomList = () => {
  const { data: chatRooms, isLoading, isError, error } = useChatRooms(userId);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [searchInput, setSearchInput] = useState("");
  const [selectedUsers, setSelectedUsers] = useState([]);
  const [searchResults, setSearchResults] = useState([]);
  const [isCreated, setIsCreated] = useState(false);
  const { addChatRoom, chatRooms: contextChatRooms } = useChatRoom();
  const navigate = useNavigate();
  const userNickname = "uniquenickname2";
  const users = [
    { id: 1, email: "이태경", password: "이태경", name: "사용자1" },
    { id: 2, email: "김준표", password: "이태경", name: "사용자2" },
    { id: 3, email: "홍준표", password: "이태경", name: "사용자3" },
  ];

  const handleInputChange = (event) => {
    setSearchInput(event.target.value);
  };

  const handleSearch = async () => {
    try {
      const response = await axios.get(`/api/search?query=${searchInput}`);
      setSearchResults(response.data);
    } catch (error) {
      console.error("에러", error);
    }
  };

  const handleUserSelect = (user) => {
    if (!selectedUsers.some((selectedUser) => selectedUser.id === user.id)) {
      setSelectedUsers([...selectedUsers, user]);
    }
  };

  const handleSubmit = (event) => {
    event.preventDefault();
  };

  const handleOpenModal = () => {
    setIsModalOpen(true);
  };

  const handleRoomCreate = async (realRoomId) => {
    setIsCreated(true);
    setIsModalOpen(false);
    const newRoom = chatRooms.find((room) => room.id === realRoomId);
    if (newRoom) {
      addChatRoom(newRoom);
    }
    navigate(`/chat/${realRoomId}`);
  };

  useEffect(() => {
    console.log("채팅방 목록 업데이트", chatRooms);
  }, [chatRooms]);

  // const userId = 2;
  if (isLoading) return <div>로딩중..</div>;
  if (isError) return <div>Error:{error?.message}</div>;
  return (
    <div className={styles.roomListGlobal}>
      <div className={styles.roomListHeader}>
        <div className={styles.roomUserContainer}>
          <div className={styles.listUserNickname}>
            <h3>{userNickname}</h3>
          </div>
          <div className={styles.roomCreateContainer} onClick={handleOpenModal}>
            <p className={styles.roomCreateBtn}>
              <FontAwesomeIcon icon={faPenToSquare} />
            </p>
          </div>
          {isModalOpen && (
            <Dialog title="뮤지션 검색" onClose={() => setIsModalOpen(false)}>
              <form onSubmit={handleSubmit}>
                <div className={styles.dialogSearchGlobal}>
                  <div className={styles.dialogSearchContainer}>
                    <span>받는 사람 : </span>
                    <input
                      className={styles.dialogSearchInput}
                      placeholder="닉네임/아이디를 입력해주세요"
                      type="text"
                      value={searchInput}
                      onChange={handleInputChange}
                    />
                    <button onClick={handleSearch}>검색</button>
                  </div>
                </div>
                <hr className={styles.hrLine} />
                {searchInput === "" ? null : (
                  <div className={styles.dialogUserListContainer}>
                    {users.map((user) => (
                      <div
                        className={styles.dialogUserList}
                        key={user.id}
                        onClick={() => handleUserSelect(user)}
                      >
                        <div className={styles.dialogUserImg}>
                          <p>유저프로필</p>
                        </div>
                        <div className={styles.dialogUserNick}>
                          <p>{user.name}</p>
                        </div>
                      </div>
                    ))}
                  </div>
                )}
                {searchInput && selectedUsers.length !== 0 ? (
                  <div className={styles.searchResultTrue}>
                    <div>
                      {selectedUsers.map((user) => (
                        <div
                          key={user.id}
                          onClick={() => handleUserSelect(user)}
                        >
                          <p>이미지</p>
                          <p>{user.name}</p>
                        </div>
                      ))}
                    </div>
                  </div>
                ) : (
                  <div className={styles.searchResultFalse}>
                    <div>
                      <p>일치하는 뮤지션이 없습니다.</p>
                    </div>
                  </div>
                )}
                <div className={styles.createBtn}>
                  {!isCreated ? (
                    <ChatRoomCreate
                      selectedUsers={selectedUsers}
                      isRoomCreated={handleRoomCreate}
                      setIsModalOpen={setIsModalOpen}
                    />
                  ) : null}
                </div>
              </form>
            </Dialog>
          )}
        </div>
        <div className={styles.message}>
          <h4>메시지</h4>
        </div>
      </div>
      <div className={styles.roomListContainer}>
        {contextChatRooms.map((room) => (
          <li key={room.id} className={styles.listContainer}>
            <Link
              state={
                ({
                  lastMessageId: room.recentChatMessage
                    ? room.recentChatMessage.id
                    : null,
                },
                { RoomDetail: room })
              }
              to={`/chat/${room.id}`}
              className={styles.listItemContainer}
            >
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
                    {room.recentChatMessage
                      ? room.recentChatMessage.messageText
                      : "No messages"}
                  </p>
                </div>
              </div>
            </Link>
          </li>
        ))}
      </div>
    </div>
  );
};

export default ChatRoomList;
