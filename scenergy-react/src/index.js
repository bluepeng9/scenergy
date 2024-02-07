import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import { Provider } from "react-redux";
import store from "./store/store";
import App from "./App";
import { createBrowserRouter, RouterProvider } from "react-router-dom";
import { routes } from "./router/router";
import { ChatRoomProvider } from "./contexts/ChatRoomContext";
import { ChatMessageProvider } from "./contexts/ChatMessageContext";
import { QueryClient, QueryClientProvider } from "react-query";
import axios from "axios"; //danny 추가 (네이버 유저 정보 가져올때)

/*danny 추가 (네이버 유저정보 가져올떄)*/
axios.defaults.baseURL = "http://localhost:3000/";
axios.defaults.withCredentials = true;

const router = createBrowserRouter(routes);
const queryClient = new QueryClient();
const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <Provider store={store}>
    <ChatRoomProvider>
      <ChatMessageProvider>
        <QueryClientProvider client={queryClient}>
          <RouterProvider router={router}>
            <App />
          </RouterProvider>
        </QueryClientProvider>
      </ChatMessageProvider>
    </ChatRoomProvider>
  </Provider>,
);
