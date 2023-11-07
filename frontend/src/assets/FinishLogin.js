import {useNavigate, useParams, useSearchParams} from "react-router-dom";
import { useAuth } from "../context/oauth2/AuthProvider";
import { useLocalStorage } from "../context/oauth2/useLocalStorage";
import { useEffect } from "react";

const FinishLogin = () => {
    const regex = /id_token=([^&]*)/;
    const match = window.location.href.match(regex);
    const [token, setToken] = useLocalStorage("token");

    const navigate = useNavigate();

    useEffect(() => {
        if (match && match[1]) {
            const accessToken = match[1].replace("\"", "")
            setToken(accessToken)
            navigate("/home")
        } else {
            console.log("Access Token not found.");
        }
        
    }, [])
    return <></>
    
}

export default FinishLogin;