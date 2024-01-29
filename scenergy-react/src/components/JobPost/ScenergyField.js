import styles from "./ScenergyField.module.css";
import SearchCategory from "../commons/Search/SearchCategory";
import ScenergyList from "./ScenergyList";
const ScenergyField = () => {
  return (
    <div className={styles.FieldGlobal}>
      <SearchCategory />
      <div className={styles.FieldItemsGlobal}>
        <div className={styles.FieldTitle}>
          <h2>시너지</h2>
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
