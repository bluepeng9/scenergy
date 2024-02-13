import Dialog from "../Dialog/Dialog";
import styles from "../../Chat/ChatField.module.css";
import ChatRoomCreate from "../../Chat/ChatRoomCreate";
import followApi from "../../../apis/FollowApi";
import searchApi from "../../../apis/SearchApi";
import {useEffect, useState} from "react";
import {useChatRoom} from "../../../contexts/ChatRoomContext";
import {useNavigate} from "react-router-dom";
import ApiUtil from "../../../apis/ApiUtil";

const ChatUserSearch = ({ isOpen, onClose, onUserSelect, fromUserId }) => {
  const [searchInput, setSearchInput] = useState("");
  const [searchResults, setSearchResults] = useState([]);
  const [followingList, setFollowingList] = useState([]);
  const [selectedUsers, setSelectedUsers] = useState([
    {id: parseInt(ApiUtil.getUserIdFromToken())}
  ]);
  const [isCreated, setIsCreated] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const { addChatRoom, chatRooms } = useChatRoom();
  const navigate = useNavigate();

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

  const handleClose = () => {
    onClose();
    setSearchInput("");
  };

  const handleCloseProp = (user) => {
    handleUserSelect(user);
    handleClose();
  };

  const handleRoomCreateProp = async (realRoomId) => {
    handleClose();
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
        console.log(searchResults);

        setSearchInput("");
      } else {
        setSearchResults(null);
      }
    } catch (error) {
      console.error("검색 중 오류 발생:", error);
      // 오류 처리 로직
    }
  };

  useEffect(() => {
    console.log(searchResults);
    console.log(selectedUsers);
  }, [searchResults, selectedUsers]);

  const handleUserSelect = (user) => {
    console.log(user);
    if (!selectedUsers.some((selectedUser) => selectedUser === user.userId)) {
      setSelectedUsers([...selectedUsers, {id: user.userId}]);
    }
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

  const handleSubmit = (event) => {
    event.preventDefault();
  };

  return (
    <Dialog isOpen={isOpen} title="뮤지션 검색" onClose={handleClose}>
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
              {followingList.map((followingUser) => (
                <div
                  key={followingUser.userId}
                  onClick={() => handleUserSelect(followingUser)}
                >
                  {followingUser.nickname}
                </div>
              ))}
            </>
          ) : searchResults === null ? (
            <p>일치하는 뮤지션이 없습니다.</p>
          ) : (
            searchResults.map((user) => (
              <div
                className={styles.dialogUserList}
                key={user.userId}
                onClick={() => handleUserSelect(user)}
              >
                <div className={styles.dialogUserImg}>
                  <p>{user.url}</p>
                </div>
                <div className={styles.dialogUserNick}>
                  <p>{user.nickname}</p>
                </div>
              </div>
            ))
          )}
        </div>
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
  );
};
export default ChatUserSearch;
