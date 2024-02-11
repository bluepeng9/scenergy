import styles from "./ScenergyField.module.css";
import SearchCategory from "../commons/Search/SearchCategory";
import ScenergyList from "./ScenergyList";
import Dialog from "../commons/Dialog/Dialog";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faFeather } from "@fortawesome/free-solid-svg-icons";
import { useState } from "react";
import { createJobPost } from "../../apis/JobPost/JobPostApi";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";

const ScenergyField = () => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [modalContent, setModalContent] = useState(null);
  const [endDate, setEndDate] = useState(new Date());
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [peopleRecruited, setPeopleRecruited] = useState(0);

  const handleOpenChange = () => {
    setIsModalOpen(true);
  };

  const handleCloseModal = () => {
    setIsModalOpen(false);
    setEndDate(new Date()); // 모달이 닫힐 때 endDate를 초기화합니다.
  };

  const handleSubmit = () => {
    const postData = {
      userId: 2,
      title,
      content,
      expirationDate: endDate,
      isActive: true,
      peopleRecruited,
      genreTags: [],
      instrumentTags: [],
      locationTags: [],
    };

    createJobPost(postData)
      .then((response) => {
        console.log("글작성 성공", response);
        handleCloseModal();
      })
      .catch((error) => {
        console.error("글 작성 실패", error);
      });
  };
  return (
    <div className={styles.FieldGlobal}>
      <div className={styles.FieldHeader}>
        <div className={styles.FieldTitle}>
          <h2>시너지</h2>
        </div>
      </div>
      <SearchCategory />
      <div className={styles.FieldItemsGlobal}>
        <div className={styles.FieldWrite}>
          <button className={styles.FieldWriteBtn} onClick={handleOpenChange}>
            <FontAwesomeIcon icon={faFeather} />
            글쓰기
          </button>
          {isModalOpen && (
            <Dialog title="글 작성하기" onClose={handleCloseModal}>
              <div className={styles.scenergyModalGlobal}>
                <div className={styles.scenergyPostHeader}>
                  <p>제목</p>
                  <input
                    type="text"
                    value={title}
                    onChange={(e) => setTitle(e.target.value)}
                  />
                </div>
                <div className={styles.scenergyPostSelect}>
                  <div className={styles.scenergyPostItem}>
                    <div className={styles.scenergyPostRegion}>
                      <p>지역</p>
                      <select>
                        <option>서울</option>
                      </select>
                    </div>
                  </div>
                  <div className={styles.scenergyPostItem}>
                    <div className={styles.scenergyPostGenre}>
                      <p>장르</p>
                      <select>
                        <option>재즈</option>
                      </select>
                    </div>
                  </div>
                  <div className={styles.scenergyPostItem}>
                    <div className={styles.scenergyPostInst}>
                      <p>악기</p>
                      <select>
                        <option>피아노</option>
                      </select>
                    </div>
                  </div>
                </div>
                <div className={styles.scenergyPostNumber}>
                  <div className={styles.scenergyPostItem}>
                    <div className={styles.scenergyPostExp}>
                      <p>모집 기간</p>
                      <DatePicker
                        className={styles.customDatePicker}
                        selected={endDate}
                        dateFormat="yyyy.MM.dd"
                        onChange={(date) => setEndDate(date)}
                        minDate={new Date()}
                        maxDate={
                          new Date(
                            new Date().setFullYear(
                              new Date().getFullYear() + 1,
                            ),
                          )
                        }
                      />
                    </div>
                  </div>
                  <div>
                    <p>모집 인원</p>
                    <input
                      type="number"
                      value={peopleRecruited}
                      min="1"
                      onChange={(e) =>
                        setPeopleRecruited(parseInt(e.target.value))
                      }
                    />
                  </div>
                </div>
                <div className={styles.scenergyContentContainer}>
                  <div className={styles.scenergyContent}>
                    <p>내용 작성</p>
                    <textarea
                      value={content}
                      onChange={(e) => setContent(e.target.value)}
                    />
                  </div>
                </div>
                <div className={styles.scenergyPostBtn}>
                  <button onClick={handleSubmit}>작성하기</button>
                </div>
              </div>
            </Dialog>
          )}
        </div>
        <div className={styles.FieldSelectContainer}>
          <select className={styles.FiledStateSelect}>
            <option>전체</option>
            <option>모집</option>
            <option>종료</option>
          </select>
        </div>
      </div>
      <div className={styles.FieldList}>
        <ScenergyList onOpenModal={handleOpenChange} />
      </div>
    </div>
  );
};
export default ScenergyField;
