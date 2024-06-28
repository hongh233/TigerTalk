import React, {useState} from "react"
import {useNavigate} from 'react-router-dom';
import axios from 'axios';
import "../assets/styles/ForgotPasswordPage.css"


const ForgotPasswordPage = () => {
    const [email, setEmail] = useState('');
    const [errors, setErrors] = useState({});
    const [step, setStep] = useState(1);
    const navigate = useNavigate();

    const handleChange = (e) => {
        setEmail(e.target.value);
    };

    const handleNext = async () => {
        setErrors({});
        if (!email) {
            setErrors({email: 'Email is required'});
            return;
        }

        try {
            const response = await axios.post('http://localhost:8085/api/passwordReset/validateEmailExist', null, {
                params: {email}
            });
            if (response.data === "Email exists and is valid.") {
                setStep(2);
            }
        } catch (error) {
            if (error.response && error.response.status === 400) {
                setErrors({email: error.response.data});
            } else {
                setErrors({email: "An error occurred during email validation. Please try again later."});
            }
        }
    };

    const handleSecurityQuestions = () => {
        navigate('/securityQuestions', {state: {email}});
    };

    const handleEmailVerification = async () => {
        try {
            await axios.post('http://localhost:8085/api/passwordReset/sendToken', null, {
                params: {email}
            });
            navigate('/emailVerification', {state: {email}});
        } catch (error) {
            setErrors({email: "An error occurred during sending email. Please try again later."});
        }
    };

    return (
        <div className="forgot-password-page">
            {step === 1 && (
                <div className="forgot-password-card">
                    <h1>Forgot Password</h1>
                    <input
                        type="email"
                        name="email"
                        placeholder="Enter your email"
                        value={email}
                        onChange={handleChange}
                    />
                    <button onClick={handleNext}>Next Step</button>
                    {errors.email && <p className="error">{errors.email}</p>}
                </div>
            )}
            {step === 2 && (
                <div className="forgot-password-card verification-options-container">
                    <h1>Identity Verification</h1>
                    <button onClick={handleEmailVerification}>Email Verification</button>
                    <button onClick={handleSecurityQuestions}>Ask Security Questions</button>
                </div>
            )}
        </div>
    );
};

export default ForgotPasswordPage;