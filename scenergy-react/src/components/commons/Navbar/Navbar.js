import {Link} from "react-router-dom";
import styles from "./Navbar.module.css";
import {useState} from "react";
import ApiUtil from "../../../apis/ApiUtil";

const Navbar = () => {
  const [dropdownOpen, setDropdownOpen] = useState(false);

  const isLoggedIn = !!ApiUtil.getUserIdFromToken(); // 토큰을 가지고 있는지 여부로 로그인 상태 판단

  const homePath = isLoggedIn ? "/home2" : "/home"; // 로그인 상태에 따라 다른 홈 경로 선택

  // notificationApi.connectToNotificationServer(2, (data) => {
  //   console.log(data);
  // }, (data) => {
  //   console.log(data);
  // });

  const toggleDropdown = () => {
    setDropdownOpen(!dropdownOpen);
  };
  return (
    <nav className={styles.NavBarContainer}>
      <ul>
        <li className={styles.ScenergyTab}>
          <Link to="/" style={{fontWeight: 'bold', fontSize: '25px'}}>scenergy</Link>
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
          <Link to="/scenergy" className={styles.NavScenergy}>
            시너지
          </Link>
          <ul className={styles.NavBarDropMenu}>
            <li>
              <Link to="/scenergy">시너지</Link>
            </li>
            <li>
              <Link to="/scenergy/mypost">내가 쓴 글</Link>
            </li>
            <li>
              <Link to="/scenergy/myapply">내가 지원한 글</Link>
            </li>
            <li>
              <Link to="/scenergy/bookmark">북마크</Link>
            </li>
          </ul>
        </li>
        <li>
          <Link to="/videoupload">업로드</Link>
        </li>
        <li className={styles.NavBarNotice}>
          <Link to="/notification">알림</Link>
        </li>
        <li>
          <Link to="/chat">메세지</Link>
        </li>
        <li>
          <Link to={`/profile/${ApiUtil.getUserIdFromToken()}`}>프로필</Link>
        </li>
      </ul>
    </nav>
  );
};

export default Navbar;
