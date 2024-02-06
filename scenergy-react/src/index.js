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
