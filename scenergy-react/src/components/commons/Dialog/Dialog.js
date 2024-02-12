import styles from "./Dialog.module.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faTimes,
  faBookmark as solidBookmark,
} from "@fortawesome/free-solid-svg-icons";
import { faBookmark as regularBookmark } from "@fortawesome/free-regular-svg-icons";

import { useEffect, useState } from "react";
import jobPostApi from "../../../apis/JobPost/JobPostApi";
import { useScenergyPost } from "../../../contexts/ScenergyPostContext";
import apiUtil from "../../../apis/ApiUtil";
import ApiUtil from "../../../apis/ApiUtil";

const Dialog = ({
  title,
  onClose,
  children,
  showBookmarkButton,
  jobPostId,
  nickname,
}) => {
  const [isSolid, setIsSolid] = useState(false);
  const { bookmarkedPosts, addBookmark, removeBookmark } = useScenergyPost();
  // const isModalOpen = useSelector((state) => state.isModalOpen);
  const userId = ApiUtil.getUserIdFromToken();

  useEffect(() => {
    // 컴포넌트가 마운트되면 bookmarkedPosts 확인하여 isSolid 상태 업데이트
    setIsSolid(bookmarkedPosts.has(jobPostId));
  }, [bookmarkedPosts, jobPostId]);
  const handleMarkChange = async () => {
    try {
      const data = {
        jobPostId: jobPostId,
        userId: userId,
      };
      console.log(data);
      await jobPostApi.bookMarkJobPost(data);
      console.log("북마크 성공", data);
      if (bookmarkedPosts.has(jobPostId)) {
        removeBookmark(jobPostId);
        setIsSolid(false);
      } else {
        addBookmark(jobPostId);
        setIsSolid(true);
      }
      setIsSolid(!isSolid);
    } catch (error) {
      console.error("북마크 처리 실패", error);
    }
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
