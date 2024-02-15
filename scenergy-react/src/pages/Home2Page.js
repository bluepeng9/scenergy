// Home.js (로그인 됐을때 화면)
import React from "react";
import {useCookies} from "react-cookie";
import {useNavigate} from "react-router-dom";
import Navbar from "../components/commons/Navbar/Navbar";
import styles from "./Home2Page.module.css";
import FollowVideo from "./FollowVideo";

const Home2Page = () => {
  const [cookies, setCookie, removeCookie] = useCookies();
  const navigate = useNavigate();

  const handleLogout = () => {
    // 로그아웃 시 쿠키에서 토큰 제거
    removeCookie("accessToken");
    localStorage.removeItem("token"); //토큰 제거
    // 로그인 화면으로 이동
    navigate("/home");
  };

  return (
    <>
      <div className={styles["content"]}>
        <div className={styles["navbar"]}>
          <Navbar/>
          <div className={styles["logout-div"]}>
            <button onClick={handleLogout}>로그아웃</button>
            {/* 로그아웃 버튼 */}
          </div>
        </div>
        <div className={styles["video"]}>
          <FollowVideo></FollowVideo>
        </div>
      </div>
    </>
  );
};

export default Home2Page;
