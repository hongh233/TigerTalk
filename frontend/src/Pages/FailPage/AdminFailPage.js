import React from "react"
import '../../assets/styles/Pages/FailPage/AdminFailPage.css';
import {useNavigate} from 'react-router-dom';


const AdminFailPage = () => {
    const navigate = useNavigate();
    const handleLogIn = () => {
        navigate('/');
    };
    return (
        <div className="admin-fail-page">
            <div className="admin-fail-card">
                <h1>You need to be admin to access this page.</h1>
                <button onClick={handleLogIn}>Log In</button>
            </div>
        </div>
    );
};

export default AdminFailPage;