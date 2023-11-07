import React, {useEffect, useState} from 'react';
import {
    DesktopOutlined,
    FileOutlined,
    PieChartOutlined,
    TeamOutlined,
    UserOutlined,
} from '@ant-design/icons';
import {Breadcrumb, Layout, Menu, Switch, theme} from 'antd';
import UnloggedNavbar from "./navigation/UnloggedNavbar";
import {Route, Routes} from "react-router-dom";
import {ProtectedRoute} from "./navigation/ProtectedRoute";
import FinishLogin from "./assets/FinishLogin";
import ActiveOrders from './screens/ActiveOrders/ActiveOrders';
import Foods from './screens/Foods/Foods';

const {Header, Content, Footer, Sider} = Layout;

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
                <Route path="/home" render={(props) => <ProtectedRoute {...props} />}>
                    <Route path=""  element={<HomePage/>}/>
                </Route>

                <Route path="/login" render={(props) => <ProtectedRoute {...props} />}>
                    <Route path=""  element={<LoginPage/>}/>
                </Route>

                <Route path="/finishLogin" element={<FinishLogin/>}/>
            </Routes>
        </UnloggedNavbar>
    </>
};

const LoginPage = () => (
   <div>
       <h1>This is login Page</h1>
   </div>
);


const HomePage = () => {
    return <div>
    <h1>This is the Home Page</h1>
</div>;
};

export default App;
