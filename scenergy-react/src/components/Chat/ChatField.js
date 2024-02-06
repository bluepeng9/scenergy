import styles from "./ChatField.module.css";
import { useEffect, useState } from "react";
import Dialog from "../commons/Dialog/Dialog";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faComments } from "@fortawesome/free-regular-svg-icons";
import axios from "axios";
import ChatRoomCreate from "./ChatRoomCreate";
import { useNavigate, useParams } from "react-router-dom";
import ChatRoomReal from "./ChatRoomReal";
import {useChatRoom} from "../../contexts/ChatRoomContext";

const ChatField = () => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [searchInput, setSearchInput] = useState("");
  const [selectedUsers, setSelectedUsers] = useState([]);
  const [searchResults, setSearchResults] = useState([]);
  const [isCreated, setIsCreated] = useState(false);
  const { roomId } = useParams();
  const realRoomId = parseInt(roomId, 10)
  const {addChatRoom, chatRooms}=useChatRoom()
  const navigate = useNavigate();
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
    if(newRoom){
      addChatRoom(newRoom);
    }
    navigate(`/chat/${realRoomId}`);
  };

  const handleCancel = (userId) => {
    setSelectedUsers(selectedUsers.filter((user) => user.id !== userId));
  };

  return (
      <>
        <div className={styles.fieldGlobal}>
          <div className={styles.fieldBody} onClick={handleOpenModal}>
            <FontAwesomeIcon className={styles.fieldBodyIcon} icon={faComments} />
            <p>원하는 뮤지션에게 메세지를 보내보세요.</p>
            <button>뮤지션 찾기</button>
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
                              <div key={user.id} onClick={() => handleUserSelect(user)}>
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
        {isCreated && <ChatRoomReal />}
      </>
  );
};

export default ChatField;
