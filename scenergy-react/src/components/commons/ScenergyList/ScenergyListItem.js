import styles from "./ScenergyListItem.module.css";
import { useDispatch } from "react-redux";
import { openModal } from "../../../actions/actions";
const ScenergyListItem = ({ jobstate, nickname, title, content }) => {
  const dispatch = useDispatch();
  const handleItemClick = () => {
    dispatch(openModal());
  };
  /*나중에 백이랑 연결해서 받아올것*/
  /*내맘대로*/
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
        <hr className={styles.hrline}/>
        <div className={styles.ItemFooter}>
          <div>
            <p>지원자 수</p>
          </div>
          <div>
            <p>마감 날짜</p>
          </div>
        </div>
      </div>
      <div></div>
    </div>
  );
};
export default ScenergyListItem;
