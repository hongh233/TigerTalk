import React, {useState} from 'react';
import {useLocation, useNavigate} from 'react-router-dom';
import axios from 'axios';
import "../../assets/styles/ForgetPassword/EmailVerificationPage.css";

const EmailVerificationPage = () => {
    const {state} = useLocation();
    const {email} = state || {};
    const [code, setCode] = useState('');
    const [errors, setErrors] = useState({});
    const navigate = useNavigate();

    const handleChange = (e) => {
        setCode(e.target.value);
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setErrors({});
        try {
            await axios.post(`http://localhost:8085/api/passwordReset/checkToken/${code}`, {
                email
            });
            alert('Email has verified! You can now reset your password!');
            navigate('/resetPassword', {state: {email}});
        } catch (error) {
            setErrors({general: error.response.data});
        }
    };


    return (
        <div className="email-verification">
            <div className="email-verification-card">
                <h1>Enter Verification Code</h1>
                <form onSubmit={handleSubmit}>
                    <input
                        type="text"
                        placeholder="Enter code sent to your email"
                        value={code}
                        onChange={handleChange}
                    />
                    <button type="submit">Submit</button>
                </form>
                {errors.general && <p className="error-css">{errors.general}</p>}
            </div>
        </div>
    );
};

export default EmailVerificationPage;