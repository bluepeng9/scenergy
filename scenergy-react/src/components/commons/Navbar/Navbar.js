import { Link } from "react-router-dom";
import styles from "./Navbar.module.css";
function Navbar() {
  return (
    <div className={styles.NavBarContainer}>
      <div className={styles.ScenergyTab}>
        <Link to="/main">scenergy</Link>
      </div>
      <Link to="/home">홈</Link>
      <Link to="/search">검색</Link>
      <div className={styles.NavBarDropdownGlobal}>
        <Link to="/scenergy" className={styles.NavScenergy}>
          시너지
        </Link>
        <div className={styles.NavBarDropMenu}>
          <Link to="/scenergy">시너지</Link>
          <Link to="/scenergy/mypost">
            <div>내가 쓴 글</div>
          </Link>
          <Link to="/scenergy/myapply">
            <div>내가 지원한 글</div>
          </Link>
          <Link to="/scenergy/bookmark">
            <div>북마크</div>
          </Link>
        </div>
      </div>
      <Link to="/videoupload">업로드</Link>
      <Link to="/notice">알림</Link>
      <Link to="/chat">메세지</Link>
      <Link to="/profile">프로필</Link>
    </div>
  );
}

export default Navbar;
