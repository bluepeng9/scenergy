import ChatConnect from "./ChatConnect";
import styles from "./ChatField.module.css";
import { useState } from "react";
import ChatRoomReal from "./ChatRoomReal";
import Dialog from "../commons/Dialog/Dialog";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faComments } from "@fortawesome/free-regular-svg-icons";
import { useDispatch, useSelector } from "react-redux";
import { closeModal, openModal } from "../../actions/actions";
import { useSearchParams } from "react-router-dom";

const ChatField = () => {
  const isModalOpen = useSelector((state) => state.isModalOpen);
  const dispatch = useDispatch();
  const [searchParams, setSearchParams] = useSearchParams();
  const [searchInput, setSearchInput] = useState("");
  const searchInputKey = searchParams.get("searchInput");
  /*back api랑 method명 맞추기 getSearch api연결시켜야함*/
  /*  const searchList = getSearch(searchInputKey);*/

  const searchList = [];
  const handleOpenChange = () => {
    /*setIsModalOpen(true);*/
    dispatch(openModal());
    console.log(isModalOpen);
  };
  const handleInputChange = (event) => {
    setSearchInput(event.target.value);
  };
  const handleSubmit = (event) => {
    event.preventDefault();
    /*검색 로직 추가하기*/
    setSearchParams(
      searchInput
        ? {
            searchInput,
          }
        : {},
    );
  };

  return (
    <>
      <div className={styles.FieldGlobal}>
        <div className={styles.FieldBody}>
          <FontAwesomeIcon className={styles.FieldBodyIcon} icon={faComments} />
          <p>원하는 뮤지션에게 메세지를 보내보세요.</p>
          {/*나중에 Button 컴포넌트 만들어서 바꾸기*/}
          <button onClick={handleOpenChange}>메세지 보내기</button>
        </div>
        {isModalOpen && (
          <Dialog title="메시지 보내기" onClose={() => dispatch(closeModal())}>
            <form onSubmit={handleSubmit}>
              <div className={styles.DialogSearchContainer}>
                <div className={styles.DialogSearch}>
                  <span>받는 사람 : </span>
                  <input
                    placeholder="닉네임/아이디를 입력해주세요"
                    type="text"
                    value={searchInput}
                    onChange={handleInputChange}
                  />
                </div>
              </div>
              {searchInput && searchList.length !== 0 ? (
                <div className={styles.SearchResultTrue}>
                  <div>{/*   {searchList.map((search)=>())}*/}</div>
                  <div>
                    {/*일치하는 사용자가 있을 때 뜨는 div*/}
                    <button type="submit">메세지 보내기</button>
                  </div>
                </div>
              ) : (
                <div className={styles.SearchResultFalse}>
                  <div>
                    {/*일치하는 사용자 없을 때 뜨는 div*/}
                    <p>일치하는 뮤지션이 없습니다.</p>
                  </div>
                </div>
              )}
            </form>
          </Dialog>
        )}
        <div>
          <ChatRoomReal />
        </div>
      </div>
    </>
  );
};
export default ChatField;
