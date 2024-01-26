import { Link } from "react-router-dom";
import styles from "./Navbar.module.css"
function Navbar() {
  return (
    <div className={styles.NavBarContainer}>
      <Link to="/main">scenergy</Link>
      <Link to="/home">홈</Link>
      <Link to="/search">검색</Link>
      <Link to="/scenergy">시너지</Link>
      <Link to="/videoupload">업로드</Link>
      <Link to="/notice">알림</Link>
      <Link to="/chat">메세지</Link>
      <Link to="/profile">프로필</Link>
    </div>
  );
}

export default Navbar;
