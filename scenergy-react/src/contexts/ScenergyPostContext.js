import { createContext, useContext, useState } from "react";

const ScenergyPostContext = createContext();

export const useScenergyPost = () => useContext(ScenergyPostContext);

export const ScenergyPostProvider = ({ children }) => {
  const [scenergyPosts, setScenergyPosts] = useState([]);
  const [bookmarkedPosts, setBookmarkedPosts] = useState(new Set());

  const addScenergyPost = (post) => {
    setScenergyPosts((prevPosts) => [...prevPosts, post]);
  };

  // 북마크 추가
  const addBookmark = (postId) => {
    setBookmarkedPosts((prevBookmarks) => new Set(prevBookmarks).add(postId));
  };

  // 북마크 제거
  const removeBookmark = (postId) => {
    setBookmarkedPosts((prevBookmarks) => {
      const updatedBookmarks = new Set(prevBookmarks);
      updatedBookmarks.delete(postId);
      return updatedBookmarks;
    });
  };

  return (
    <ScenergyPostContext.Provider
      value={{
        scenergyPosts,
        addScenergyPost,
        bookmarkedPosts,
        addBookmark,
        removeBookmark,
      }}
    >
      {children}
    </ScenergyPostContext.Provider>
  );
};
