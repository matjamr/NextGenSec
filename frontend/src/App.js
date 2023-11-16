import React, {useEffect, useState} from 'react';
import UnloggedNavbar from "./navigation/UnloggedNavbar/UnloggedNavbar";
import {Route, Routes} from "react-router-dom";
import {ProtectedRoute} from "./navigation/ProtectedRoute";
import FinishLogin from "./assets/FinishLogin";
import HomeScreen from "./screens/HomeScreen/HomeScreen";
import LoginScreen from "./screens/LoginScreen/LoginScreen";
import ProductsScreen from "./screens/Products/ProductsScreen";
import AboutUsScreen from "./screens/AboutUs/AboutUsScreen";

const App = () => {

    const [isAuthenticated, userHasAuthenticated] = useState(false);

    useEffect(() => {
        onLoad();
    }, []);

    async function onLoad() {
        try {
            userHasAuthenticated(true);
        } catch (e) {
            alert(e);
        }
    }

    return <>
        <UnloggedNavbar>
            <Routes>
                <Route path="/home" element={<HomeScreen/> } />

                <Route path="/products" element={<ProductsScreen/> } />

                <Route path="/about" element={<AboutUsScreen/> } />

                <Route path="/login" render={(props) => <ProtectedRoute {...props} />}>
                    <Route path=""  element={<LoginScreen/>}/>
                </Route>

                <Route path="/finishLogin" element={<FinishLogin/>}/>
            </Routes>
        </UnloggedNavbar>
    </>
};

export default App;
