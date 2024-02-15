import styles from "./HomePage.module.css";
import Navbar from "../components/commons/Navbar/Navbar";
import Login from "../components/User/Login";

function HomePage() {
  return (
      <div className={styles.homeContainer}>
        <Navbar className={styles.homeNavbar} />
        <Login></Login>
      </div>
  );
}

export default HomePage;
