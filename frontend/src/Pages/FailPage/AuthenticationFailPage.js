import React from "react"
import '../../assets/styles/Pages/FailPage/AuthenticationFailPage.css';
import {useNavigate} from 'react-router-dom';


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