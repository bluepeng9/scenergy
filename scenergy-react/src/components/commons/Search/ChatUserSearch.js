import Dialog from "../Dialog/Dialog";
import styles from "../../Chat/ChatField.module.css";
import ChatRoomCreate from "../../Chat/ChatRoomCreate";
import followApi from "../../../apis/FollowApi";
import searchApi from "../../../apis/SearchApi";
import { useEffect, useState } from "react";
import { useChatRoom } from "../../../contexts/ChatRoomContext";
import { useNavigate, useParams } from "react-router-dom";
import ApiUtil from "../../../apis/ApiUtil";
import axios from "axios";

const ChatUserSearch = ({
  isOpen,
  onClose,
  onUserSelect,
  fromUserId,
  isCreatingNewRoom,
}) => {
  const [searchInput, setSearchInput] = useState("");
  const [searchResults, setSearchResults] = useState([]);
  const [followingList, setFollowingList] = useState([]);
  const [selectedUsers, setSelectedUsers] = useState([
    { id: parseInt(ApiUtil.getUserIdFromToken()) },
  ]);
  const [isCreated, setIsCreated] = useState(false);
  const [isModalOpen, setIsModalOpen] = useState(false);
  const { addChatRoom, chatRooms } = useChatRoom();
  const navigate = useNavigate();
  const { roomId } = useParams();
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

  const handleSearch = async () => {
    if (!searchInput.trim()) {
      setSearchResults([]);
      return;
    }
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
    const isSelected = selectedUsers.some(
      (selectedUser) => selectedUser.id === user.userId,
    );
    if (isSelected) {
      // 이미 선택된 사용자를 선택 해제
      setSelectedUsers(
        selectedUsers.filter((selectedUser) => selectedUser.id !== user.userId),
      );
    } else {
      // 선택되지 않은 사용자를 선택
      setSelectedUsers([
        ...selectedUsers,
        { id: user.userId, nickname: user.nickname },
      ]);
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

  const handleInvite = async () => {
    if (selectedUsers.length > 0) {
      try {
        const userIds = selectedUsers.map((user) => user.id); // 사용자 ID만 추출
        const response = await axios.post(
          `${process.env.REACT_APP_API_URL}/chatroom/invite-room`,
          {
            room_id: roomId,
            users: userIds, // 수정: users 필드에 userIds 배열 전달
          },
        );
        if (response.data) {
          console.log("초대 완료", response.data);
          setIsModalOpen(false); // 모달 닫기
          setSelectedUsers([]); // 선택된 사용자 목록 초기화
          onClose();
        }
      } catch (error) {
        console.error("초대 중 에러 발생", error);
      }
    }
  };

  return (
    <Dialog isOpen={isOpen} title="뮤지션 검색" onClose={handleClose}>
      <form className={styles.chatUserSearchBody} onSubmit={handleSubmit}>
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
          {searchInput === "" || searchResults?.length === 0 ? (
            <>
              {followingList.length > 0 ? (
                followingList.map((followingUser) => (
                  <div
                    key={followingUser.userId}
                    onClick={() => handleUserSelect(followingUser)}
                    className={`${styles.dialogUserList} ${selectedUsers.some((user) => user.id === followingUser.userId) ? styles.selectedUser : ""}`} // 스타일 적용
                  >
                    <div className={styles.dialogUserNick}>
                      {followingUser.nickname}
                    </div>
                  </div>
                ))
              ) : (
                <p>팔로우하는 뮤지션이 없습니다.</p> // 팔로잉 목록이 비었을 때의 메시지
              )}
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
          {isCreatingNewRoom ? (
            <ChatRoomCreate
              selectedUsers={selectedUsers}
              isRoomCreated={handleRoomCreate}
              setIsModalOpen={setIsModalOpen}
            />
          ) : (
            <button onClick={handleInvite}>초대하기</button>
          )}
        </div>
      </form>
    </Dialog>
  );
};
export default ChatUserSearch;
