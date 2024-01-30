import Navbar from "../components/commons/Navbar/Navbar";
import styles from "./ProfilePage.module.css";
import ProfileHeader from "../components/Profile/ProfileHeader";
import ProfileMiddle from "../components/Profile/ProfileMiddle";
import ProfileVideoList from "../components/Profile/ProfileVideoList"

function ProfilePage() {
    return (
        <>
            <div className={styles['ProfilePage-global']}>
                <Navbar/>
                {/*<h2 className={styles['portfolio-link']}>프로필 페이지</h2>*/}

                <div>
                    <hr/>
                    <ProfileHeader/>
                    <ProfileMiddle/>
                    <ProfileVideoList/>

                </div>


            </div>

        </>
    );
}

export default ProfilePage;
