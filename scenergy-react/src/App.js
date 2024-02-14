import Navbar from "./components/commons/Navbar/Navbar";
import router from "./router/router";
import EnterPage from "./pages/EnterPage";
import {RouterProvider} from "react-router-dom";
import NotificationApi from "./apis/NotificationApi";
import ApiUtil from "./apis/ApiUtil";

NotificationApi.connectToNotificationServer(
    ApiUtil.getUserIdFromToken(),
    (data) => {
        console.log(data);
    },
    (data) => {
        console.log(data);
    }
)

function App() {
  return (
    // <UserTokenProvider>
    //   <RedirectPage />
    <div>
      <Navbar />

      <RouterProvider router={router}>
        <EnterPage />
      </RouterProvider>
    </div>
    // {/*</UserTokenProvider>*/}
  );
}

export default App;
