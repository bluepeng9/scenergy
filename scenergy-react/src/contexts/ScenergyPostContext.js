import { createContext, useContext, useState } from "react";

const ScenergyPostContext = createContext();

export const useScenergyPost = () => useContext(ScenergyPostContext);

export const ScenergyPostProvider = ({ children }) => {
  const [scenergyPost, setScenergyPost] = useState([]);
  const [bookmarkedPost, setBookmarkedPost] = useState([]);

  const addScenergyPost = (post) => {
    setScenergyPost((prevPosts) => [...prevPosts, post]);
  };

  return (
    <ScenergyPostContext.Provider
      value={{ scenergyPost, addScenergyPost, bookmarkedPost }}
    >
      {children}
    </ScenergyPostContext.Provider>
  );
};
