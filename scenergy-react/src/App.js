import Navbar from "./components/commons/Navbar/Navbar";
import router from "./router/router";
import EnterPage from "./pages/EnterPage";
import {RouterProvider} from "react-router-dom";

function App() {
    return (
        <div>
            <Navbar />
            <RouterProvider router={router}>
                <EnterPage/>
            </RouterProvider>
        </div>
    );
}

export default App;
