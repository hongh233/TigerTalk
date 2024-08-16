import React from "react"
import {useNavigate} from 'react-router-dom';
import '../../assets/styles/AuthenticationFailPage.css';

const AuthenticationFailPage = () => {
    const navigate = useNavigate();

    const handleLogIn = () => {
        navigate('/');
    };
    return (
        <div className="authentication-fail-page">
            <div className="authentication-fail-card">
                <h1>Please log in to enter.</h1>
                <button onClick={handleLogIn}>Log In</button>
            </div>
        </div>
    );
};

export default AuthenticationFailPage;