import React, {useEffect, useState} from "react";
import Navbar from "../components/commons/Navbar/Navbar";
import styles from "./NotificationPage.module.css";
import NotificationApi from "../apis/NotificationApi";

const NotificationContainer = (notification) => {
    const userNickname = notification.notification.senderNickname;
    const message = notification.notification.chatMessage;
    //db 유저 id
    const userId = notification.notification.sender;
    // 팔로우
    if (message === undefined) {
        return (
            <>
                <div>
                    {userNickname}
                </div>
                <div>
                    {message} 님이 팔로우 하셨습니다.
                </div>
            </>
        );
    }

    //채팅방 ID
    const roomId = notification.notification.roomId;

    // 채팅
    return (
        <>
            <div>
                {userNickname}
            </div>
            <div>
                {message}
            </div>
        </>
    );
}

const NotificationPage = () => {
    const [notifications, setNotifications] = useState([]);

    useEffect(() => {
        const init = async () => {
            const response = await NotificationApi.getAllNotification();
            setNotifications(response);
        };

        init();
    }, []);

    return (
        <>
            <div className={styles.notificationMain}>
                <Navbar/>
                <div className={styles.rightMainBox}>
                    <h2>알림</h2>
                    <h3>내가 받은 알림</h3>
                    {
                        notifications.map((notification, index) => (
                            <div key={index}>
                                <NotificationContainer notification={notification}/>
                            </div>
                        ))
                    }
                </div>
            </div>
        </>
    );
}


export default NotificationPage;