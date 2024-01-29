import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import {Provider} from "react-redux";
import store from "./store/store";
import AppRoutes from "./router/router";

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
    <React.StrictMode>
        <Provider store={store}>
                <AppRoutes/>
        </Provider>
    </React.StrictMode>,
);
