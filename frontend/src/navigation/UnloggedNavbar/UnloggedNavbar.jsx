import React, {useState} from 'react';
import {useNavigate} from 'react-router-dom';
import './UnloggedNavbar.css'

function getItem(label, key, navigationUrl) {
    return {
        key,
        navigationUrl,
        label,
    };
}

const items = [
    getItem('Home', '1', "/home"),
    getItem('Products', '2', "/products"),
    getItem('About us', '3', "/about"),
    getItem('Log in', '4', "/login"),
];

const UnloggedNavbar = ({children}) => {
    const [collapsed, setCollapsed] = useState(false);

    const navigate = useNavigate()

    const handleClick = (e) => {
        console.log('click ', e.key);
        navigate(e.navigationUrl)
    };

    return <>
        <div className="navbar">
            <div className="logo">
                <img src="path/to/your/logo.png" alt="Logo" />
                <span>My Logo</span>
            </div>
            <div className="options">
                {items.map(item => {
                        return <div className="option" onClick={() => handleClick(item)} key={item.key}>{item.label}</div>
                    }
                )}
            </div>
        </div>
        {children}
    </>

}


export default UnloggedNavbar;
