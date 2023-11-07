import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
function getItem(label, key, icon, children) {
    return {
        key,
        icon,
        children,
        label,
    };
}
const items = [
    getItem('Statistics', '1', <div></div>),
];

const UnloggedNavbar = ({children}) => {
    const [collapsed, setCollapsed] = useState(false);

    const navigate = useNavigate()

    const onClick = (e) => {
        console.log('click ', e.key);
        switch(e.key) {
            case 1:
                navigate("/home");
                break;
        }
    };

    return <div></div>
}


export default UnloggedNavbar;
