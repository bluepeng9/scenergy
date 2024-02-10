class NotificationApi {

    #sse;

    notificationUrl = process.env.REACT_APP_NOTIFICATION_API_URL

    connectToNotificationServer = (userId, onConnected, onNotification) => {
        if (this.#sse !== undefined) {
            return;
        }
        this.#sse = new EventSource(`${this.notificationUrl}/${userId}`);

        this.#sse.addEventListener("notification", (event) => {
            onNotification(JSON.parse(event.data));
        })

        this.#sse.addEventListener("connect", (event) => {
            onConnected(event.data);
        })

    }
}

const notificationApi = new NotificationApi();


export default notificationApi;