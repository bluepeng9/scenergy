import styles from "./BookMark.module.css";
import Navbar from "../components/commons/Navbar/Navbar";
import ScenergyList from "../components/JobPost/ScenergyList";
import ApiUtil from "../apis/ApiUtil";

const BookMark = () => {
  const userId = ApiUtil.getUserIdFromToken();
  return (
    <>
      <div className={styles.bookmarkGlobals}>
        <Navbar />
        <div className={styles.bookmarkBody}>
          <div className={styles.bookmarkHeader}>
            <h2>시너지</h2>
            <h3>북마크</h3>
          </div>
          <div>
            <ScenergyList userId={userId} mode="bookmarks" />
          </div>
        </div>
      </div>
    </>
  );
};
export default BookMark;
