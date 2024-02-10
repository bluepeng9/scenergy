import {Link} from "react-router-dom";
import styles from "./Navbar.module.css";
import {useState} from "react";
import notificationApi from "../../../apis/NotificationApi";

const Navbar = () => {
    const [dropdownOpen, setDropdownOpen] = useState(false);

    notificationApi.connectToNotificationServer(2, (data) => {
        console.log(data);
    }, (data) => {
        console.log(data);
    });

    const toggleDropdown = () => {
        setDropdownOpen(!dropdownOpen);
    };
    return (
        <nav className={styles.NavBarContainer}>
            <ul>
                <li className={styles.ScenergyTab}>
                    <Link to="/">scenergy</Link>
                </li>
                <li>
                    <Link to="/home">홈</Link>
                </li>
                <li>
                    <Link to="/search">검색</Link>
                </li>
                <li
                    className={`${styles.NavBarDropdownGlobal} ${dropdownOpen ? styles.dropdownOpen : ""}`}
                    onMouseEnter={toggleDropdown}
                    onMouseLeave={toggleDropdown}
                >
                    <Link to="/scenergy" className={styles.NavScenergy}>시너지</Link>
                    <ul className={styles.NavBarDropMenu}>
                        <li><Link to="/scenergy">시너지</Link></li>
                        <li><Link to="/scenergy/mypost">내가 쓴 글</Link></li>
                        <li><Link to="/scenergy/myapply">내가 지원한 글</Link></li>
                        <li><Link to="/scenergy/bookmark">북마크</Link></li>
                    </ul>
                </li>
                <li>
                    <Link to="/videoupload">업로드</Link>
                </li>
                <li className={styles.NavBarNotice}>
                    <p>알림</p>
                </li>
                <li>
                    <Link to="/chat">메세지</Link>
                </li>
                <li>
                    <Link to="/profile">프로필</Link>
                </li>
            </ul>
        </nav>
    );
};

export default Navbar;
