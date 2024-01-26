import styles from "./Dialog.module.css";
const Dialog = ({ title, onClose, children }) => {
  return (
    <div className={styles.DialogBack} onClick={onClose}>
      <div
        className={styles.DialogContent}
        onClick={(e) => e.stopPropagation()}
      >
        <div className={styles.DialogHeader}>
          <h2>{title}</h2>
          <button onClick={onClose}>닫기</button>
        </div>
        <div className={styles.DialogBody}>{children}</div>
      </div>
    </div>
  );
};
export default Dialog;
