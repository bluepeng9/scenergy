import React from "react";
import { Route, Routes } from "react-router-dom";
import Navbar from "./components/commons/Navbar/Navbar";
import Login from "./components/User/Login";
import HomePage from "./pages/HomePage";
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
            <Login />
            <Navbar />
            <Routes>
                <Route path="/home" element={<HomePage />} />
                <Route path="/search" element={<SearchPage />} />
                <Route path="/scenergy" element={<ScenergyPage />} />
                <Route path="/videoupload" element={<VideoUploadPage />} />
                <Route path="/notice" element={<NoticePage />} />
                <Route path="/chat" element={<ChatPage />} />
                <Route path="/profile" element={<ProfilePage />} />
                <Route path="/component/Portfolio/Portfolio" element={<Portfolio />} />
                <Route path="/pages/ProfilePage" element={<ProfilePage />} />
            </Routes>
        </div>
    );
}

export default App;