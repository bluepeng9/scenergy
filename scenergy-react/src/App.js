import Navbar from "./components/commons/Navbar/Navbar";
import router from "./router/router";
import EnterPage from "./pages/EnterPage";
import { RouterProvider } from "react-router-dom";
import { UserTokenContextProvider } from "./contexts/UserToken";
import RedirectPage from "./components/User/RedirectPage";

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
