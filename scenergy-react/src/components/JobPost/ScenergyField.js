import styles from "./ScenergyField.module.css";
const ScenergyField = () => {
  return (
    <div className={styles.FieldGlobal}>
      <div className={styles.FieldInput}>
        <input type="text" placeholder="검색어를 입력해주세요." />
      </div>
        <div className={styles.FieldCategoryGlobal}>

        </div>
    </div>
  );
};
export default ScenergyField;
