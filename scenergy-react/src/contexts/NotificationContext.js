import {createContext, useContext, useEffect, useState} from "react";
import NotificationApi from "../apis/NotificationApi";
import ApiUtil from "../apis/ApiUtil";

const NotificationContext = createContext([]);

export const NotificationProvider = ({children}) => {
    const [notifications, setNotifications] = useState([]);
    const [isFecthingMoreData, setIsFecthingMoreData] = useState(false);


    useEffect(() => {
        const initNoti = async () => {
            let allNotification = await NotificationApi.getAllNotification();
            allNotification.reverse()
            setNotifications(allNotification)
            NotificationApi.connectToNotificationServer(
                ApiUtil.getUserIdFromToken(),
                (data) => {
                    console.log(data);
                },
                (data) => {
                    setIsFecthingMoreData(true);
                }
            )
        }
        initNoti()
    }, []);
    useEffect(() => {
        if (isFecthingMoreData) {
            const fetchMoreData = async () => {
                let allNotification = await NotificationApi.getAllNotification();
                allNotification.reverse()
                setNotifications(allNotification)
                setIsFecthingMoreData(false);
            }
            fetchMoreData();
        }
    }, [isFecthingMoreData]);

    return (
        <NotificationContext.Provider value={{
            notifications
        }}>
            {children}
        </NotificationContext.Provider>
    );
};

export const useNotification = () => useContext(NotificationContext);
