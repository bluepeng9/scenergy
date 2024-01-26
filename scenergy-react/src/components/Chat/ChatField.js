import ChatConnect from "./ChatConnect";
import styles from "./ChatField.module.css";
import { useState } from "react";
import ChatRoomReal from "./ChatRoomReal";
import Dialog from "../commons/Dialog/Dialog";
const ChatField = () => {
  const [isDialogOpen, setIsDialogOpen] = useState(false);
  const [searchInput, setSearchInput] = useState("");
  const handleOpenChange = () => {
    setIsDialogOpen(true);
  };
  const handleInputChange = (event) => {
    setSearchInput(event.target.value);
  };

  return (
    <>
      <div className={styles.FieldGlobal}>
        <div className={styles.FieldBody}>
          <p>비디오 이미지 들어갈거야</p>
          <p>원하는 뮤지션에게 메세지를 보내보세요.</p>
          {/*나중에 Button 컴포넌트 만들어서 바꾸기*/}
          <button onClick={handleOpenChange}>메세지 보내기</button>
        </div>
        {isDialogOpen && (
          <Dialog title="메시지 보내기" onClose={() => setIsDialogOpen(false)}>
            <div>
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
            {/*일치하는 사용자가 있을 때 뜨는 div*/}
            <div>
              <button>메세지 보내기</button>
            </div>
            {/*일치하는 사용자 없을 때 뜨는 div*/}
            <div>일치하는 뮤지션이 없습니다.</div>
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
