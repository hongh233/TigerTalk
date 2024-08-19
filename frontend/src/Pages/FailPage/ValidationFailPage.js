import React from "react"
import '../../assets/styles/Pages/FailPage/ValidationFailPage.css';
import {useNavigate} from 'react-router-dom';


const ValidationFailPage = () => {
    const navigate = useNavigate();
    const handleLogIn = () => {
        navigate('/');
    };
    return (
        <div className="authentication-fail-page">
            <div className="authentication-fail-card">
                <h1>Waiting for admin approval.</h1>
                <button onClick={handleLogIn}>Go to log In</button>
            </div>
        </div>
    );
};

export default ValidationFailPage;