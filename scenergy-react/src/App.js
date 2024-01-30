import { Route, Router, Routes } from "react-router-dom";

/*
import Navbar from "./components/commons/Navbar/Navbar";
*/
import HomePage from "./pages/HomePage";
/*import PortfolioPage from "./pages/PortfolioPage";*/
import SearchPage from "./pages/SearchPage";
import ScenergyPage from "./pages/ScenergyPage";
import VideoUploadPage from "./pages/VideoUploadPage";
import NoticePage from "./pages/NoticePage";
import ChatPage from "./pages/ChatPage";
import ProfilePage from "./pages/ProfilePage";
import Portfolio from "./components/Portfolio/Portfolio";
function App() {
  return (
    <div>
      <EnterPage />
      {/*<Navbar />*/}
      <Routes>
        <Route path="/home" element={<HomePage />} />
        <Route path="/search" element={<SearchPage />} />
        <Route path="/scenergy/*" element={<ScenergyPage />}>
          <Route path="mypost" element={<MyScenergyPost />} />
        </Route>
        <Route path="/videoupload" element={<VideoUploadPage />} />
        <Route path="/notice" element={<NoticePage />} />
        <Route path="/chat" element={<ChatPage />} />
        <Route path="/profile" element={<ProfilePage />} />
      </Routes>
    </div>
  );
}

export default App;
