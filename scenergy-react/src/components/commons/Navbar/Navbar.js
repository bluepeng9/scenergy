import {Link, useLocation, useNavigate} from "react-router-dom";
import styles from "./Navbar.module.css";
import React, {useState} from "react";
import ApiUtil from "../../../apis/ApiUtil";
import {useCookies} from "react-cookie";
import NotificationModal from "./NotifiactionModal/NotificationModal";

const Navbar = () => {
  const [dropdownOpen, setDropdownOpen] = useState(false);
  const [cookies, setCookie, removeCookie] = useCookies();
  const navigate = useNavigate();
  const isLoggedIn = !!ApiUtil.getUserIdFromToken(); // 토큰을 가지고 있는지 여부로 로그인 상태 판단
  const location = useLocation();
  const {pathname} = location;
  const homePath = isLoggedIn ? "/home2" : "/home"; // 로그인 상태에 따라 다른 홈 경로 선택
  const pathSegments = pathname.split("/");

  const routerName = pathSegments[pathSegments.length - 1];
  // notificationApi.connectToNotificationServer(2, (data) => {
  //   console.log(data);
  // }, (data) => {
  //   console.log(data);
  // });

  const toggleDropdown = () => {
    setDropdownOpen(!dropdownOpen);
  };

  const handleLogout = () => {
    removeCookie("accessToken");
    localStorage.removeItem("token"); //토큰 제거
    navigate("/home");
  };

    // 알림창
    const [isNotificationOpen, setIsNotificationOpen] = useState(false);

  return (
      <nav className={styles.NavBarContainer}>
        <ul>
          <li className={styles.ScenergyTab}>
            <Link to="/" style={{fontWeight: 'bold', fontSize: '25px', color: '#000000'}}>scene:rgy</Link>
          </li>
          <li>
            <Link to={homePath}>홈</Link>
          </li>
          <li>
            <Link to="/search">검색</Link>
          </li>
          <li
              className={`${styles.NavBarDropdownGlobal} ${dropdownOpen ? styles.dropdownOpen : ""}`}
              onMouseEnter={toggleDropdown}
              onMouseLeave={toggleDropdown}
          >
            {/*<Link to="/scenergy" className={styles.NavScenergy}>*/}
            {/*  시너지*/}
            {/*</Link>*/}
            {/*<ul className={styles.NavBarDropMenu}>*/}
            <li>
              <Link to="/scenergy">시너지</Link>
            </li>
            {/*  <li>*/}
            {/*    <Link to="/scenergy/mypost">내가 쓴 글</Link>*/}
            {/*  </li>*/}
            {/*  <li>*/}
            {/*    <Link to="/scenergy/myapply">내가 지원한 글</Link>*/}
            {/*  </li>*/}
            {/*  <li>*/}
            {/*    <Link to="/scenergy/bookmark">북마크</Link>*/}
            {/*  </li>*/}
            {/*</ul>*/}
          </li>
          <li>
            <Link to="/videoupload">업로드</Link>
          </li>
          <li className={styles.NavBarNotice}>
              <Link onClick={() => {
                  setIsNotificationOpen(true)
              }}>알림</Link>
              {
                  isNotificationOpen && <NotificationModal onClose={() => setIsNotificationOpen(false)}/>
              }
          </li>
          <li>
            <Link to="/chat">메세지</Link>
          </li>
          <li>
            <Link to={`/profile/${ApiUtil.getUserIdFromToken()}`}>프로필</Link>
          </li>
        </ul>
        <button onClick={handleLogout} className={styles["logout"]}
                hidden={(routerName !== "home2") && (routerName !== "home")}>logout
        </button>
      </nav>
  );
};

export default Navbar;
