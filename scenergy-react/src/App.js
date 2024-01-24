import { Route, Router, Routes } from "react-router-dom";

import Navbar from "./components/commons/Navbar/Navbar";
import HomePage from "./pages/HomePage";
import PortfolioPage from "./pages/PortfolioPage";
import SearchPage from "./pages/SearchPage";
import ScenergyPage from "./pages/ScenergyPage";
import VideoUploadPage from "./pages/VideoUploadPage";
import NoticePage from "./pages/NoticePage";
import ChatPage from "./pages/ChatPage";
import ProfilePage from "./pages/ProfilePage";
import ChatConnect from "./components/Chat/ChatConnect";

function App() {
  return (
    <div>
      <Navbar />
      <Routes>
        <Route path="/home" component={HomePage} />
        <Route path="/portfolio" component={PortfolioPage} />
        <Route path="/search" component={SearchPage} />
        <Route path="/scenergy" component={ScenergyPage} />
        <Route path="/videoupload" component={VideoUploadPage} />
        <Route path="/notice" component={NoticePage} />
        <Route path="/chat" component={ChatPage} />
        <Route path="/profile" component={ProfilePage} />
      </Routes>
    </div>
  );
}

export default App;
