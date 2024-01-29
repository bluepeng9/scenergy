import { RouterProvider, createBrowserRouter } from "react-router-dom";

import App from "../App";
import ChatPage from "../pages/ChatPage";
/*import EnterPage from "../pages/EnterPage";*/
import HomePage from "../pages/HomePage";
import NoticePage from "../pages/NoticePage";
import ProfilePage from "../pages/ProfilePage";
import ScenergyPage from "../pages/ScenergyPage";
import SearchPage from "../pages/SearchPage";
import VideoUploadPage from "../pages/VideoUploadPage";
import MyScenergyPost from "../pages/MyScenergyPost";
import MyApplyPage from "../pages/MyApplyPage";
import BookMark from "../pages/BookMark";
import EnterPage from "../pages/EnterPage";
import Portfolio from "../components/Portfolio/Portfolio";

const routes = [
  { path: "*", element: <App /> },
  { path: "/main", element: <EnterPage /> },
  { path: "/chat", element: <ChatPage /> },
  { path: "/home", element: <HomePage /> },
  { path: "/notice", element: <NoticePage /> },
  { path: "/profile", element: <ProfilePage /> },
  { path: "/profile/portfolio", element: <Portfolio /> },
  { path: "/scenergy", element: <ScenergyPage /> },
  { path: "/scenergy/mypost", element: <MyScenergyPost /> },
  { path: "/scenergy/myapply", element: <MyApplyPage /> },
  { path: "/scenergy/bookmark", element: <BookMark /> },
  { path: "/search", element: <SearchPage /> },
  { path: "/videoupload", element: <VideoUploadPage /> },
];

const router = createBrowserRouter(routes);

const AppRouter = () => {
  return <RouterProvider router={router} />;
};

export default AppRouter;
