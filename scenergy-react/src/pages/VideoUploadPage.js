import Navbar from "../components/commons/Navbar/Navbar";
import styles from "./VideoUploadPage.module.css";
import VideoUpload from "../components/VideoUpload/VideoUpload";

function VideoUploadPage() {
    return (
        <>
            <div className={styles['VideoUpload-global']}>
                <Navbar/>
                <VideoUpload/>
            </div>
        </>
    );
}

export default VideoUploadPage;
