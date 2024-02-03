import styles from "./Dialog.module.css";
import { useDispatch, useSelector } from "react-redux";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faTimes,
  faBookmark as solidBookmark,
} from "@fortawesome/free-solid-svg-icons";
import { faBookmark as regularBookmark } from "@fortawesome/free-regular-svg-icons";

import { useState } from "react";

const Dialog = ({
  title,
  onClose,
  children,
  showBookmarkButton,
  isModalOpen,
}) => {
  const [isSolid, setIsSolid] = useState(false);
  // const isModalOpen = useSelector((state) => state.isModalOpen);
  const dispatch = useDispatch();

  const handleMarkChange = () => {
    /*나중에 back에서 코드 짜면 bookmark api랑 연결시키기*/
    setIsSolid(!isSolid);
  };

  return (
    <div className={styles.DialogBack} onClick={onClose}>
      <div
        className={styles.DialogContent}
        onClick={(e) => e.stopPropagation()}
      >
        <div className={styles.DialogHeader}>
          <p>{title}</p>
          <div className={styles.DialogButtonContainer}>
            {showBookmarkButton && (
              <button
                className={styles.DialogBookmarkButton}
                onClick={handleMarkChange}
              >
                <FontAwesomeIcon
                  icon={isSolid ? solidBookmark : regularBookmark}
                />
              </button>
            )}
            <button className={styles.DialogCloseButton} onClick={onClose}>
              <FontAwesomeIcon icon={faTimes} />
            </button>
          </div>
        </div>
        <hr />
        <div className={styles.DialogBody}>{children}</div>
      </div>
    </div>
  );
};
export default Dialog;
