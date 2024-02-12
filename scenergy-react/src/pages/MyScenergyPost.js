import styles from "./MyScenergyPost.module.css";
import ScenergyField from "../components/JobPost/ScenergyField";
import ScenergyList from "../components/JobPost/ScenergyList";
import Navbar from "../components/commons/Navbar/Navbar";
import ApiUtil from "../apis/ApiUtil";
import { useEffect } from "react";
const MyScenergyPost = () => {
  const userId = ApiUtil.getUserIdFromToken();

  useEffect(() => {}, []);
  return (
    <>
      <div className={styles.myScenergyGlobal}>
        <Navbar />
        <div className={styles.myScenergyBody}>
          <div className={styles.myScenergyHeaderContainer}>
            <div className={styles.myScenergyHeaders}>
              <h2>시너지</h2>
              <h3>내가 쓴 글</h3>
            </div>
          </div>
          <ScenergyList userId={userId} mode="myPosts" />
        </div>
      </div>
    </>
  );
};
export default MyScenergyPost;
