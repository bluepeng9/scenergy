import styles from "./MyApplyPage.module.css";
import Navbar from "../components/commons/Navbar/Navbar";
import ScenergyList from "../components/JobPost/ScenergyList";
import ApiUtil from "../apis/ApiUtil";
const MyApplyPage = () => {
  const userId = ApiUtil.getUserIdFromToken();
  console.log(userId);
  return (
    <>
      <div className={styles.myapplyGlobal}>
        <Navbar />
        <div className={styles.myapplyBody}>
          <div className={styles.myapplyHeaderContainer}>
            <div className={styles.myapplyHeader}>
              <h2>시너지</h2>
              <h3>내가 지원한 글</h3>
            </div>
          </div>
          <ScenergyList userId={userId} mode="myApplies" />
        </div>
      </div>
    </>
  );
};
export default MyApplyPage;
