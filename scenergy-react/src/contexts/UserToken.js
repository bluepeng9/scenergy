// UserToken.js
import { createContext, useContext, useState, useEffect } from "react";

const UserTokenContext = createContext();

function UserTokenContextProvider({ children }) {
  const [globalToken, setGlobalToken] = useState(null);

  useEffect(() => {
    const storedToken = localStorage.getItem("accessToken");
    if (storedToken) {
      setGlobalToken(storedToken);
    }
  }, []);

  const setToken = function (token) {
    setGlobalToken(token);
    localStorage.setItem("accessToken", token);
  };

  return (
    <UserTokenContext.Provider
      value={{
        globalToken,
        setToken,
      }}
    >
      {children}
    </UserTokenContext.Provider>
  );
}

export const useUserToken = () => useContext(UserTokenContext);
export { UserTokenContext, UserTokenContextProvider };
