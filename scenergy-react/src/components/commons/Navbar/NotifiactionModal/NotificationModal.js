import styles from './NotificationModal.module.css';
import Header from "./Header";
import {useNotification} from "../../../../contexts/NotificationContext";
import NotificationContainer from "./NotificationContainer";

const NotificationModal = ({onClose}) => {

    const notificationContext = useNotification();
    const notifications = notificationContext.notifications;

    return <>
        <div className={styles.modalBack} onClick={onClose}>
            <div className={styles.modal} onClick={(e) => e.stopPropagation()}>
                <Header></Header>
                {
                    notifications.map((notification, index) => {
                        return <NotificationContainer key={index} notification={notification}></NotificationContainer>
                    })
                }
            </div>
        </div>
    </>
}

export default NotificationModal;