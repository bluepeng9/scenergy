import ApiUtil from "./ApiUtil";
import baseAxios from "axios";

class NotificationApi {

    #sse;

    axios = baseAxios.create({});

    notificationUrl = process.env.REACT_APP_NOTIFICATION_API_URL

    connectToNotificationServer = (userId, onConnected, onNotification) => {
        if (this.#sse !== undefined) {
            return;
        }
        this.#sse = new EventSource(`${this.notificationUrl}/connect/${userId}`);

        this.#sse.addEventListener("notification", (event) => {
            onNotification(JSON.parse(event.data));
        })

        this.#sse.addEventListener("connect", (event) => {
            onConnected(event.data);
        })

    }

    getAllNotification = async () => {
        let userIdFromToken = ApiUtil.getUserIdFromToken();

        return (await this.axios.get(`${this.notificationUrl}/users/${userIdFromToken}/notification`)).data.notifications;

    }
}

const notificationApi = new NotificationApi();


export default notificationApi;