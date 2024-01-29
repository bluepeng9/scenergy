import styles from "./ScenergyField.module.css";
import SearchCategory from "../commons/Search/SearchCategory";
import ScenergyList from "./ScenergyList";
import Dialog from "../commons/Dialog/Dialog";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faFeather } from "@fortawesome/free-solid-svg-icons";
import { useDispatch, useSelector } from "react-redux";
import { closeModal, openModal } from "../../actions/actions";
import { useState } from "react";
const ScenergyField = () => {
  const isModalOpen = useSelector((state) => state.isModalOpen);
  const dispatch = useDispatch();
  const [modalContent, setModalContent] = useState(null);
  const handleOpenChange = () => {
      setModalContent({
          title:"시너지 만들기",
          children:(
              <div>
                  <div>
                      <p>작성자</p>
                      <input type="text"/>
                  </div>
                  <div>
                      <div>
                          <p>지역</p>
                          <select>
                              <option>서울</option>
                          </select>
                      </div>
                  </div>
              </div>
          )
      })
    dispatch(openModal());
    console.log("글쓰끼");
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
            <Dialog
              title={modalContent.title}
              onClose={() => dispatch(closeModal())}
            >
                {modalContent.children}
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
        <ScenergyList />
      </div>
    </div>
  );
};
export default ScenergyField;
