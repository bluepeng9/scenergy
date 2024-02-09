// UserTokenProvider.js
import React from "react";
import UserTokenProvider, { UserTokenContextProvider } from "./UserToken";
import RedirectPage from "../components/User/RedirectPage";

const UserTokenProviderWrapper = ({ children }) => {
  return (
    <UserTokenContextProvider>
      {children}
      <RedirectPage />
    </UserTokenContextProvider>
  );
};

export default UserTokenProviderWrapper;
