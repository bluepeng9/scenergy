import Navbar from "../components/commons/Navbar/Navbar";
import styles from "./ScenergyPage.module.css"
import ScenergyField from "../components/JobPost/ScenergyField";
function ScenergyPage() {
  return (
    <div className={styles.ScenergyGlobal}>
        <div className={styles.ScenergyNav}>
            <Navbar/>
        </div>
        <div className={styles.ScenergyField}>
            <ScenergyField/>
        </div>
    </div>
  );
}
export default ScenergyPage;
