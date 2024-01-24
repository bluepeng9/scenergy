import { RouterProvider, createBrowserRouter } from "react-router-dom";

import App from "../App";
import ChatPage from "../pages/ChatPage";
import EnterPage from "../pages/EnterPage";
import HomePage from "../pages/HomePage";
import NoticePage from "../pages/NoticePage";
import PortfolioPage from "../pages/PortfolioPage";
import ProfilePage from "../pages/ProfilePage";
import ScenergyPage from "../pages/ScenergyPage";
import SearchPage from "../pages/SearchPage";
import VideoUploadPage from "../pages/VideoUploadPage";

const router = createBrowserRouter([
  {
    path: "*",
    element: <App />
  },
  {
    path: "/chat",
    element: <ChatPage />
  },
  {
    path: "/main",
    element: <EnterPage />
  },
  {
    path: "/home",
    element: <HomePage />
  },
  {
    path: "/notice",
    element: <NoticePage />
  },
  {
    path: "/portfolio",
    element: <PortfolioPage />
  },
  {
    path: "/profile",
    element: <ProfilePage />
  },
  {
    path: "/scenergy",
    element: <ScenergyPage />
  },
  {
    path: "/search",
    element: <SearchPage />
  },
  {
    path: "/videoupload",
    element: <VideoUploadPage />
  }
]);

const AppRouter = () => {
  return <RouterProvider router={router} />;
};

export default AppRouter;
