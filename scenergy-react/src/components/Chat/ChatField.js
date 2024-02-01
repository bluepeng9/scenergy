import ChatConnect from "./ChatConnect";
import styles from "./ChatField.module.css";
import {useState} from "react";
import ChatRoomReal from "./ChatRoomReal";
import Dialog from "../commons/Dialog/Dialog";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faComments } from "@fortawesome/free-regular-svg-icons";
import { useDispatch, useSelector } from "react-redux";
import { closeModal, openModal } from "../../actions/actions";
import axios from "axios";
import ChatRoomCreate from "./ChatRoomCreate";

const ChatField = () => {
  const isModalOpen = useSelector((state) => state.isModalOpen);
  const dispatch = useDispatch();
  const [searchInput, setSearchInput] = useState("");
  const [selectedUsers, setSelectedUsers] = useState([]); //나중에 본인도 기본값으로 넣어야함 user끝나고 상태 완성되면 넣기
  const [searchResults, setSearchResults] = useState([]);
  const [isCreated, setIsCreated] = useState(false); //채팅방 만들어졌는지!
  const users = [
    { id: 1, email: "이태경", password: "이태경", name: "사용자1" },
    { id: 2, email: "김준표", password: "이태경", name: "사용자2" },
    { id: 3, email: "홍준표", password: "이태경", name: "사용자3" },
  ];

  const handleRoomCreated = (created) => {
    setIsCreated(created);
  };
  const handleOpenChange = () => {
    /*setIsModalOpen(true);*/
    dispatch(openModal());
    console.log(isModalOpen);
  };
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
    //중복 선택 안된다
    if (!selectedUsers.some((selectedUser) => selectedUser.id === user.id)) {
      setSelectedUsers([...selectedUsers, user]);
    }
  };

  const handleSubmit = (event) => {
    event.preventDefault();
  };

  /*
  const handleRoomCreated = () => {
    setIsCreated(false);
    //나중에 채팅방 목록 업데이트 시키는 로직 추가하기
  };
*/

  return (
    <>
      {isCreated ? (
        <div className={styles.FieldGlobal}>
          <div className={styles.FieldBody}>
            <FontAwesomeIcon
              className={styles.FieldBodyIcon}
              icon={faComments}
            />
            <p>원하는 뮤지션에게 메세지를 보내보세요.</p>
            {/*나중에 Button 컴포넌트 만들어서 바꾸기*/}
            <button onClick={handleOpenChange}>뮤지션 찾기</button>
          </div>
          {isModalOpen && (
            //default로 팔로우한 뮤지션 목록들 다 불러올것
            <Dialog title="뮤지션 검색" onClose={() => dispatch(closeModal())}>
              <form onSubmit={handleSubmit}>
                <div className={styles.DialogSearchGlobal}>
                  <div className={styles.DialogSearchContainer}>
                    <span>받는 사람 : </span>
                    <input
                      className={styles.DialogSearchInput}
                      placeholder="닉네임/아이디를 입력해주세요"
                      type="text"
                      value={searchInput}
                      onChange={handleInputChange}
                    />
                    <button onClick={handleSearch}>검색</button>
                  </div>
                </div>
                <hr className={styles.hrline} />
                {/*검색어 입력되기 전, 유저 선택하기 전에 */}
                {searchInput === "" ? null : (
                  <div className={styles.DialogUserListContainer}>
                    {/*map으로 리스트 쏴주기*/}
                    {users.map((user) => (
                      <div
                        className={styles.DialogUserList}
                        key={user.id}
                        onClick={() => handleUserSelect(user)}
                      >
                        <div className={styles.DialogUserImg}>
                          {/* 유저 프로필 이미지 */}
                          <p>유저프로필</p>
                        </div>
                        <div className={styles.DialogUserNick}>
                          {/* 유저 닉네임 */}
                          <p>{user.name}</p>
                        </div>
                      </div>
                    ))}
                    {/*<div className={styles.DialogUserList}>*/}
                    {/*  <div className={styles.DialogUserImg}>*/}
                    {/*    <p>유저프로필</p>*/}
                    {/*  </div>*/}
                    {/*  <div className={styles.DialogUserNick}>*/}
                    {/*    <p>유저닉네임</p>*/}
                    {/*  </div>*/}
                    {/*</div>*/}
                  </div>
                )}
                {/*{searchInput && searchResults.length !== 0 ? (*/}
                {/*{searchInput || searchResults.length !== 0 ? (*/}
                {/*  <div className={styles.SearchResultTrue}>*/}
                {/*    <div>*/}
                {/*      {searchResults.map((user) => (*/}
                {/*        <div*/}
                {/*          key={user.nickname}*/}
                {/*          onClick={() => handleUserSelect(user)}*/}
                {/*        >*/}
                {/*          <p>이미지</p>*/}
                {/*          <p>{user.nickname}</p>*/}
                {/*        </div>*/}
                {/*      ))}*/}
                {/*    </div>*/}
                {/*  </div>*/}
                {/*) : (*/}
                {/*  <div className={styles.SearchResultFalse}>*/}
                {/*    <div>*/}
                {/*      <p>일치하는 뮤지션이 없습니다.</p>*/}
                {/*    </div>*/}
                {/*  </div>*/}
                {/*)}*/}
                {searchInput || selectedUsers.length !== 0 ? (
                  <div className={styles.SearchResultTrue}>
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
                  <div className={styles.SearchResultFalse}>
                    <div>
                      <p>일치하는 뮤지션이 없습니다.</p>
                    </div>
                  </div>
                )}

                <div className={styles.CreateBtn}>
                  <ChatRoomCreate
                    selectedUsers={selectedUsers}
                    isRoomCreated={handleRoomCreated}
                  />
                </div>
              </form>
            </Dialog>
          )}
        </div>
      ) : (
        <ChatRoomReal />
      )}
    </>
  );
};
export default ChatField;
