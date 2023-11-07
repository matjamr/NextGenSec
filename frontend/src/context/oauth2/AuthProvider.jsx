import {createContext, useContext, useMemo} from "react";
import {useNavigate} from "react-router-dom";
import {useLocalStorage} from "./useLocalStorage";
import axios from "axios";
import { oauthSignIn } from "../../navigation/ProtectedRoute";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
    const [token, setToken] = useLocalStorage("token", null);
    const navigate = useNavigate();

    const login = async (token) => {
        setToken(token);
        verify(token)
        navigate("/home");
    };

    const logout = () => {
        setToken(null);
        navigate("/", { replace: true });
    };

    const verify = (token) => {
        axios.post("http://localhost:8080/api/user/verify", {}, {
            headers: {
                "Source":"GOOGLE",
                "Authorization": `Bearer ${token}`
            }
        }).then(res => {
            if(res.status != 200) {
                navigate("/error")
            }
        }).catch(err => {
            oauthSignIn()
        })
    }

    const verifyToken = () => {
        axios.post("http://localhost:8080/api/user/verify", {}, {
            headers: {
                "Source":"GOOGLE",
                "Authorization": `Bearer ${token}`
            }
        }).then(res => {
            if(res.status !== 200) {
                navigate("/error")
            }
        }).catch(err => {
            oauthSignIn()
        })
    }

    const value = {
        token: token,
        verify: verify,
        verifyToken: verifyToken
    }

    return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
};

export const useAuth = () => {
    return useContext(AuthContext);
};
