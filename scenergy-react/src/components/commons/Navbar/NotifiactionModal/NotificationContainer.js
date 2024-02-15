import styles from './NotificationContainer.module.css';
import UserApi from "../../../../apis/User/UserApi";
import basicProfile from "../../../../assets/basic_profile.png";
import {useState} from "react";

const NotificationContainer = ({notification}) => {
    // 팔로우
    let message = notification.message
    let userNickname = notification.senderNickname;
    const [profileImage, setProfileImage] = useState()
    let init = () => {
        UserApi.getUserById(parseInt(notification.sender))
            .then(response => {
                setProfileImage(response.url)
            })
    }
    init()
    if (message === undefined) {
        message = `${userNickname}님이 팔로우 하셨습니다.`
    }


    return <>
        {/*{JSON.stringify(notification)}*/}
        <div className={`${styles.mainNotiCon} ${notification.status === "UNREAD" ? styles.unread : null}`}>
            <div style={{
                display: "flex",
                justifyContent: "center",
                alignItems: "center",
                width: "50px",
                height: "50px",
                marginRight: "10px"
            }}>
                <img style={{
                    width: "40px",
                    height: "40px",
                    borderRadius: "50%",
                }} src={profileImage === undefined ? basicProfile : profileImage} alt=""/>
            </div>
            <div>
                <div className={styles.topText}>
                    {userNickname}
                </div>
                <div>
                    {message}
                </div>
            </div>
        </div>

    </>
}

export default NotificationContainer;