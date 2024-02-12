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
  const userId = ApiUtil.getUserIdFromToken();

  const checkBookmarkStatus = async () => {
    try {
      const response = await jobPostApi.getAllBookMark(userId);
      setIsSolid(
        response.data.data.some((post) => post.jobPostId === jobPostId),
      );
      console.log(response.data.data);
    } catch (error) {
      console.error("북마크 상태 몰루", error);
    }
  };

  useEffect(() => {
    checkBookmarkStatus();
  }, [jobPostId, userId]);

  const handleMarkChange = async () => {
    try {
      if (isSolid) {
        await jobPostApi.deleteBookMarkJobPost({ jobPostId, userId });
        removeBookmark(jobPostId);
      } else {
        await jobPostApi.bookMarkJobPost({ jobPostId, userId });
        addBookmark(jobPostId);
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
