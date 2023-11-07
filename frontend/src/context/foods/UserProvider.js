import {createContext, useContext, useMemo, useState} from "react";
import {useNavigate} from "react-router-dom";
import axios from "axios";
import { oauthSignIn } from "../../navigation/ProtectedRoute";
import { useLocalStorage } from "../oauth2/useLocalStorage";
import { useAuth } from "../oauth2/AuthProvider";

const UsersContext = createContext();

export const UserProvider = ({ children }) => {
    const navigate = useNavigate();
    const [token, setToken] = useLocalStorage("token");
    const { verify } = useAuth()

    const [user, setUser] = useState([])

    const fetchAllUser = async () => {
        verify(token)

        axios.get("http://localhost:8080/api/user", {
            headers: {
                "Source":"GOOGLE",
                "Authorization": `Bearer ${token}`
            }
        }).then(res => {
            if(res.status != 200) {
                navigate("/error")
            } else {
                setUser(res.data)
            }
        }).catch(err => {
            // oauthSignIn()
        })
    };

    const updateUser = async (user_) => {
        verify(token)

        axios.put("http://localhost:8080/api/user", user_, {
            headers: {
                "Source":"GOOGLE",
                "Authorization": `Bearer ${token}`
            }
        }).then(res => {
            if(res.status != 200) {
                navigate("/error")
            } else {
                // fetchAllUser()
            }
        }).catch(err => {
            // oauthSignIn()
        })
    };

    const value = {
        fetchAllUser: fetchAllUser,
        user: user,
        updateUser: updateUser
    }

    return <UsersContext.Provider value={value}>{children}</UsersContext.Provider>;
};

export const useUser = () => {
    return useContext(UsersContext);
};
