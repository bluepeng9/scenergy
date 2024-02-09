import Navbar from "../components/commons/Navbar/Navbar";
import styles from "./ScenergyPage.module.css";
import ScenergyField from "../components/JobPost/ScenergyField";
import Dialog from "../components/commons/Dialog/Dialog";
import { useState } from "react";
const ScenergyPage = () => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const handleOpenModal = () => {
    setIsModalOpen(true);
  };

  const handleCloseModal = () => {
    setIsModalOpen(false);
  };

  return (
    <div className={styles.ScenergyGlobal}>
      <div className={styles.ScenergyNav}>
        <Navbar />
      </div>
      <div className={styles.ScenergyField}>
        <ScenergyField onOpenModal={handleOpenModal} />
      </div>
      {isModalOpen && (
        <Dialog
          title="팀 구합니다"
          showBookmarkButton={true}
          onClose={handleCloseModal}
        >
          <div className={styles.ScenergyModalHeader}>
            <p>작성자</p>
            <p>nickname</p>
          </div>
          <hr />
          <div className={styles.ScenergyContainer}>
            <div className={`${styles.Category} local`}>
              <p>지역</p>
              <p>대전</p>
            </div>
            <div className={`${styles.Category} genre`}>
              <p>장르</p>
              <p>재즈</p>
            </div>
            <div className={`${styles.Category} inst`}>
              <p>악기</p>
              <p>키보드</p>
            </div>
          </div>
          <hr />
          <div className={styles.ScenergyContainer}>
            <div className={`${styles.Category} day`}>
              <p>모집 기간</p>
              <p>날짜</p>
            </div>
            <div className={`${styles.Category} person`}>
              <p>모집 인원</p>
              <p>3</p>
            </div>
          </div>
          <hr />
          <div className={styles.ScenergyContentContainer}>
            <div className={styles.ScenergyContentHeader}>
              <p>내용</p>
            </div>
            <div className={styles.ScenergyContent}>
              <p>내용내용내용내용내용내용냉용</p>
            </div>
          </div>
          {/*내가 쓴 글이 아닐때만 보여야함*/}
          <div className={styles.ScenergyApply}>
            <button className={styles.ScenergyApplyBtn}>지원하기</button>
          </div>
        </Dialog>
      )}
    </div>
  );
};
export default ScenergyPage;
