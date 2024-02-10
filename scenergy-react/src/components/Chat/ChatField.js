import styles from "./ChatField.module.css";
import { useEffect, useState } from "react";
import Dialog from "../commons/Dialog/Dialog";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faComments } from "@fortawesome/free-regular-svg-icons";
import axios from "axios";
import ChatRoomCreate from "./ChatRoomCreate";
import { useNavigate, useParams } from "react-router-dom";
import ChatRoomReal from "./ChatRoomReal";
import { useChatRoom } from "../../contexts/ChatRoomContext";
import ChatRoomList from "./ChatRoomList";
import followApi from "../../apis/FollowApi";
import searchApi from "../../apis/SearchApi";
const ChatField = () => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [searchInput, setSearchInput] = useState("");
  const [selectedUsers, setSelectedUsers] = useState([]);
  const [followingList, setFollowingList] = useState([]);
  const [searchResults, setSearchResults] = useState([]);
  const [isCreated, setIsCreated] = useState(false);
  const { roomId } = useParams();
  const realRoomId = parseInt(roomId, 10);
  const { addChatRoom, chatRooms } = useChatRoom();
  const navigate = useNavigate();
  const fromUserId = 2;
  const users = [
    { id: 1, email: "이태경", password: "이태경", name: "사용자1" },
    { id: 2, email: "김준표", password: "이태경", name: "사용자2" },
    { id: 3, email: "홍준표", password: "이태경", name: "사용자3" },
  ];

  useEffect(() => {
    const loadFollowingList = async () => {
      try {
        const response = await followApi.getAllFollowing(fromUserId);
        setFollowingList(response.data.data.findAllResponseList);
        console.log(response.data.data);
        console.log(response.data.data.findAllResponseList);
      } catch (error) {
        console.error("팔로우 목록 불러오기 실패", error);
      }
    };
    loadFollowingList();
    console.log(followingList);
  }, []);
  const handleInputChange = (event) => {
    setSearchInput(event.target.value);
  };

  const handleSearch = async () => {
    if (!searchInput.trim()) {
      setSearchResults([]);
      return;
    } // 검색어가 비어있는 경우 검색하지 않음
    try {
      // SearchApi를 사용하여 검색 실행
      const response = await searchApi.searchFollowing(fromUserId, searchInput);
      if (response.data.data && response.data.data.length > 0) {
        setSearchResults(response.data.data); // 검색 결과를 searchResults 상태에 저장
        setSearchInput("");
      } else {
        setSearchResults(null);
      }
    } catch (error) {
      console.error("검색 중 오류 발생:", error);
      // 오류 처리 로직
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

  const handleCancel = (userId) => {
    setSelectedUsers(selectedUsers.filter((user) => user.id !== userId));
  };

  return (
    <>
      <ChatRoomList sytle={{ width: "30%", flex: "1" }} />
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
              <div className={styles.dialogUserListContainer}>
                {searchInput === "" ? (
                  <>
                    <ul>
                      {followingList.map((followingUser) => (
                        <li key={followingUser.id}>{followingUser.name}</li>
                      ))}
                    </ul>
                  </>
                ) : searchResults === null ? (
                  <p>일치하는 뮤지션이 없습니다.</p>
                ) : (
                  searchResults.map((user) => (
                    <div
                      className={styles.dialogUserList}
                      key={user.id}
                      onClick={() => handleUserSelect(user)}
                    >
                      <div className={styles.dialogUserImg}>
                        <p>유저프로필</p>
                      </div>
                      <div className={styles.dialogUserNick}>
                        <p>{user.nickname}</p>
                      </div>
                    </div>
                  ))
                )}
                {/*{users.map((user) => (*/}
                {/*  <div*/}
                {/*    className={styles.dialogUserList}*/}
                {/*    key={user.id}*/}
                {/*    onClick={() => handleUserSelect(user)}*/}
                {/*  >*/}
                {/*    <div className={styles.dialogUserImg}>*/}
                {/*      <p>유저프로필</p>*/}
                {/*    </div>*/}
                {/*    <div className={styles.dialogUserNick}>*/}
                {/*      <p>{user.name}</p>*/}
                {/*    </div>*/}
                {/*  </div>*/}
                {/*))}*/}
              </div>

              {/*{selectedUsers.length !== 0 && (*/}
              {/*  <div className={styles.searchResultTrue}>*/}
              {/*    <div>*/}
              {/*      {selectedUsers.map((user) => (*/}
              {/*        <div key={user.id} onClick={() => handleUserSelect(user)}>*/}
              {/*          <p>이미지</p>*/}
              {/*          <p>{user.name}</p>*/}
              {/*        </div>*/}
              {/*      ))}*/}
              {/*    </div>*/}
              {/*  </div>*/}
              {/*)}*/}
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
