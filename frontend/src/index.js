import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';
import reportWebVitals from './reportWebVitals';
import {BrowserRouter, createBrowserRouter, RouterProvider} from "react-router-dom";
import ErrorPage from "./screens/ErrorPageScreen/ErrorPageScreen";
import { AuthProvider } from './context/oauth2/AuthProvider';
import { UserProvider } from './context/foods/UserProvider';



const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
      <BrowserRouter>
        <AuthProvider>
          <UserProvider>
            <App />
          </UserProvider>
        </AuthProvider>
      </BrowserRouter>
  </React.StrictMode>
);


reportWebVitals();
