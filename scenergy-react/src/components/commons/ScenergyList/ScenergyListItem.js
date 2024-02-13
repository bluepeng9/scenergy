import styles from "./ScenergyListItem.module.css";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import {
  faBookmark,
  faCalendar,
  faFileLines,
} from "@fortawesome/free-regular-svg-icons";
const ScenergyListItem = ({
  jobPostId,
  jobstate,
  nickname,
  title,
  content,
  peopleRecruited,
  totalCount,
  onOpenModal,
  expirationDate,
  writerId,
}) => {
  const handleItemClick = () => {
    onOpenModal();
  };
  const formatDate = (dateString) => {
    const date = new Date(dateString);
    const year = date.getFullYear();
    const month = ("0" + (date.getMonth() + 1)).slice(-2);
    const day = ("0" + date.getDate()).slice(-2);
    return `${year}.${month}.${day}`;
  };

  const formattedExpirationDate = formatDate(expirationDate);

  return (
    <div onClick={handleItemClick} className={styles.ItemGlobalContainer}>
      <div className={styles.ItemGlobal}>
        <div className={styles.ItemHeader}>
          <div className={styles.ItemState}>{jobstate}</div>
          <div className={styles.ItemName}>{nickname}</div>
        </div>
        <div className={styles.ItemBody}>
          <div className={styles.ItemTitle}>
            <strong>{title}</strong>
          </div>
          <div className={styles.ItemContent}>
            <p>{content}</p>
          </div>
        </div>
        <hr className={styles.hrline} />
        <div className={styles.ItemFooter}>
          <div className={styles.scenergyApply}>
            <FontAwesomeIcon icon={faFileLines} />
            <p>{totalCount}</p>
          </div>
          <div className={styles.scenergyExp}>
            <FontAwesomeIcon icon={faCalendar} />
            <p>{formattedExpirationDate}</p>
          </div>
          <div className={styles.scenergyBookmark}>
            <FontAwesomeIcon icon={faBookmark} />
          </div>
        </div>
      </div>
      <div></div>
    </div>
  );
};
export default ScenergyListItem;
