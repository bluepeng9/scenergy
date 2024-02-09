// UserToken2.js
import React, { createContext, useContext, useReducer } from "react";

// 액션 타입 정의
const SET_GLOBAL_TOKEN = "SET_GLOBAL_TOKEN";

// 리듀서 함수 정의
const userTokenReducer = (state, action) => {
  switch (action.type) {
    case SET_GLOBAL_TOKEN:
      return { ...state, globalToken: action.payload };
    default:
      return state;
  }
};

// 초기 상태
const initialState = {
  globalToken: null,
};

// createContext로 context 생성
const UserTokenContext = createContext();

// context와 reducer를 사용한 Provider 컴포넌트
const UserTokenProvider = ({ children }) => {
  const [state, dispatch] = useReducer(userTokenReducer, initialState);

  // setGlobalToken 함수 정의
  const setGlobalToken = (token) => {
    dispatch({ type: SET_GLOBAL_TOKEN, payload: token });
  };

  return (
    <UserTokenContext.Provider
      value={{
        globalToken: state.globalToken,
        setGlobalToken,
      }}
    >
      {children}
    </UserTokenContext.Provider>
  );
};

// 커스텀 훅 생성
const useUserToken = () => useContext(UserTokenContext);

export { UserTokenProvider, useUserToken };
