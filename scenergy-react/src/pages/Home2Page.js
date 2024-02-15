// Home.js (로그인 됐을때 화면)
import React from "react";
import Navbar from "../components/commons/Navbar/Navbar";
import styles from "./Home2Page.module.css";
import FollowVideo from "./FollowVideo";

const Home2Page = () => {

  return (
    <>
      <div className={styles["content"]}>
        <div className={styles["navbar"]}>
          <Navbar/>

        </div>
        <div className={styles["video"]}>
          <FollowVideo></FollowVideo>
        </div>
      </div>
    </>
  );
};

export default Home2Page;
