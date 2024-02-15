import styles from "./HomePage.module.css";
import Navbar from "../components/commons/Navbar/Navbar";

function HomePage() {
  return (
      <div className={styles.homeContainer}>
          <div style={{width: '17%'}}>
              <Navbar/>
          </div>
      </div>
  );
}

export default HomePage;
