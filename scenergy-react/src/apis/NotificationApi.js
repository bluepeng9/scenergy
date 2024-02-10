class NotificationApi {

    #sse;

    connectToNotificationServer = (userId, onConnected, onNotification) => {
        if (this.#sse !== undefined) {
            return;
        }
        this.#sse = new EventSource(`http://localhost:9001/connect/${userId}`);

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