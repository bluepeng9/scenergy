import Navbar from "../components/commons/Navbar/Navbar";
import styles from "./VideoUploadPage.module.css";
import VideoUpload from "../components/VideoUpload/VideoUpload";

function VideoUploadPage() {
    return (
        <div className={styles['VideoUpload-global']}>
            <div className={styles[`VideoUpload-global-Navbar`]}>
                <Navbar/>
            </div>
            <div className={styles['VideoUpload-global-VideoUpload']}>
                <VideoUpload/>
            </div>
        </div>
    );
}

export default VideoUploadPage;
