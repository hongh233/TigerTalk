import React, { useState } from "react";
import "../../assets/styles/Pages/Authentication/ForgotPasswordPage.css";
import {
    sendToken,
    validateEmailExist,
    validateToken,
    verifySecurityAnswers
} from "../../axios/Authentication/PasswordResetAxios";
import { useNavigate } from 'react-router-dom';
import {updateUserPassword} from "../../axios/UserAxios";
import { SECURITY_QUESTIONS } from "../../utils/securityQuestions";


const ForgotPasswordPage = () => {
    const [email, setEmail] = useState('');
    const [errors, setErrors] = useState({});
    const [step, setStep] = useState(1);
    const navigate = useNavigate();
    const [code, setCode] = useState('');
    const [question, setQuestion] = useState('');
    const [questionAnswer, setQuestionAnswer] = useState('');
    const [isSubmitting, setIsSubmitting] = useState(false);
    const [newPassword, setNewPassword] = useState('');
    const [confirmPassword, setConfirmPassword] = useState('');
    const [errorsEmail, setErrorsEmail] = useState({});
    const [errorsSecurity, setErrorsSecurity] = useState({});
    const [errorsReset, setErrorsReset] = useState({});


    const handleChange = (e) => {
        setEmail(e.target.value);
    };

    const handleNext = async () => {
        setErrors({});
        if (!email) {
            setErrors({ email: 'Email is required' });
            return;
        }

        try {
            const responseData = await validateEmailExist(email);
            if (responseData === "Email exists and is valid.") {
                setStep(2);
            }
        } catch (error) {
            if (error.response && error.response.status === 400) {
                setErrors({ email: error.response.data });
            } else {
                setErrors({ email: "An error occurred during email validation. Please try again later." });
            }
        }
    };

    const handleSecurityQuestions = () => {
        setStep(4);
    };

    const handleEmailVerification = async () => {
        try {
            await sendToken(email);
            setStep(3);
        } catch (error) {
            setErrors({ email: "An error occurred during sending email. Please try again later." });
        }
    };




    //Email Verification
    const handleChange_emailVerification = (e) => {
        setCode(e.target.value);
    };
    const handleSubmit_emailVerification = async (e) => {
        e.preventDefault();
        setErrorsEmail({});
        try {
            await validateToken(email, code);
            alert('Email has verified! You can now reset your password!');
            setStep(5);
        } catch (error) {
            setErrorsEmail({general: error.response.data});
        }
    };





    // Security Question
    const validateSecurityQuestion = () => {
        let errors = {};
        if (!question) {
            errors.question = "Please select a security question";
        }
        if (!questionAnswer) {
            errors.questionAnswer = "Answer to the security question is required";
        }
        return errors;
    };

    const handleSubmit_securityQuestion = async (e) => {
        e.preventDefault();
        setErrorsSecurity({});
        const validationErrors = validateSecurityQuestion();
        setErrorsSecurity(validationErrors);


        if (Object.keys(validationErrors).length === 0) {
            setIsSubmitting(true);
            try {
                await verifySecurityAnswers(email, question, questionAnswer);
                alert('Security questions have been verified! You can now reset your password!');
                setStep(5);
            } catch (error) {
                setErrorsSecurity({ general: "Security question or answer not match!" });
            } finally {
                setIsSubmitting(false);
            }
        }
    };





    // Reset Password
    const handleChange_resetPassword = (e) => {
        const {name, value} = e.target;
        if (name === 'newPassword') {
            setNewPassword(value);
        } else if (name === 'confirmPassword') {
            setConfirmPassword(value);
        }
    };

    const validatePasswords = () => {
        let errors = {};

        // Password check
        if (!newPassword) {
            errors.newPassword = "Password is required";
        } else {
            if (newPassword.length < 8 || newPassword.length > 30) {
                errors.newPassword = "Password must be between 8 and 30 characters long.";
            } else if (!/[A-Z]/.test(newPassword)) {
                errors.newPassword = "Password must have at least 1 uppercase character.";
            } else if (!/[a-z]/.test(newPassword)) {
                errors.newPassword = "Password must have at least 1 lowercase character.";
            } else if (!/[0-9]/.test(newPassword)) {
                errors.newPassword = "Password must have at least 1 number.";
            } else if (!/[!@#$%^&*<>?]/.test(newPassword)) {
                errors.newPassword = "Password must have at least 1 special character, include: !@#$%^&*<>?";
            } else if (/[^A-Za-z0-9!@#$%^&*<>?]/.test(newPassword)) {
                errors.newPassword = "Password contains invalid characters. Only letters, numbers, and characters !@#$%^&*<>? are allowed.";
            }
        }

        // Confirm Password check
        if (newPassword !== confirmPassword) {
            errors.confirmPassword = "Passwords do not match";
        }

        return errors;
    };

    const handleSubmit_resetPassword = async (e) => {
        e.preventDefault();
        const errors = validatePasswords();
        setErrorsReset(errors);

        if (Object.keys(errors).length === 0) {
            try {
                await updateUserPassword(email, newPassword);
                alert("Password updated successfully");
                navigate('/');
            } catch (error) {
                console.error("Error updating password:", error);
                alert("An error occurred while updating the password. Please try again.");
            }
        }
    };

    return (
        <div className="forgot-password-page">
            {step === 1 && (
                <div className="forgot-password-card">
                    <h1>Forgot Password</h1>
                    <input type="email" name="email" placeholder="Enter your email" value={email} onChange={handleChange}/>
                    {errors.email && <div className="error-css">{errors.email}</div>}
                    <button onClick={handleNext}>Next Step</button>
                </div>
            )}
            {step === 2 && (
                <div className="forgot-password-card">
                    <h1>Identity Verification</h1>
                    <div className="verification-options-container">
                        <button onClick={handleEmailVerification}>Email Verification</button>
                        <button onClick={handleSecurityQuestions}>Ask Security Questions</button>
                    </div>
                </div>
            )}
            {step === 3 && (
                <div className="email-verification">
                    <div className="email-verification-card">
                        <h1>Enter Verification Code</h1>
                        <form onSubmit={handleSubmit_emailVerification}>
                            <input type="text" placeholder="Enter code sent to your email" value={code} onChange={handleChange_emailVerification}/>
                            <button type="submit">Submit</button>
                        </form>
                        {errorsEmail.general && <p className="error-css">{errorsEmail.general}</p>}
                    </div>
                </div>
            )}
            {step === 4 && (
                <div className="security-questions">
                    <div className="security-questions-card">

                        <h1>Answer Security Questions</h1>
                        <form onSubmit={handleSubmit_securityQuestion}>

                            <div className="form-group">
                                <select
                                    name="question"
                                    value={question}
                                    onChange={(e) => setQuestion(e.target.value)}>

                                    <option value="">Select Security Question</option>
                                    {SECURITY_QUESTIONS.map(
                                        (securityQuestion, idx) => (
                                            <option key={idx} value={securityQuestion}>
                                                {securityQuestion}
                                            </option>
                                        )
                                    )}
                                </select>
                                {errorsSecurity.question && <p className="error-css">{errorsSecurity.question}</p>}
                            </div>

                            <div className="form-group">
                                <input
                                    type="text"
                                    name="questionAnswer"
                                    value={questionAnswer}
                                    onChange={(e) => setQuestionAnswer(e.target.value)}
                                    placeholder="Answer to security question"
                                />
                                {errorsSecurity.questionAnswer && <p className="error-css">{errorsSecurity.questionAnswer}</p>}
                            </div>

                            <button type="submit" disabled={isSubmitting}>Submit</button>
                        </form>

                        {errorsSecurity.general && <p className="error-css">{errorsSecurity.general}</p>}
                    </div>
                </div>
            )}
            {step === 5 && (
                <div className="reset-password">
                    <div className="reset-password-card">
                        <h1>Reset Password</h1>
                        <form onSubmit={handleSubmit_resetPassword}>
                            <div>
                                <label>New Password</label>
                                <input
                                    type="password"
                                    name="newPassword"
                                    value={newPassword}
                                    onChange={handleChange_resetPassword}
                                />
                                {errorsReset.newPassword && <p className="error-css">{errorsReset.newPassword}</p>}
                            </div>
                            <div>
                                <label>Confirm Password</label>
                                <input
                                    type="password"
                                    name="confirmPassword"
                                    value={confirmPassword}
                                    onChange={handleChange_resetPassword}
                                />
                                {errorsReset.confirmPassword && <p className="error-css">{errorsReset.confirmPassword}</p>}
                            </div>
                            <button type="submit">Submit</button>
                        </form>
                    </div>
                </div>
            )}
        </div>
    );
};

export default ForgotPasswordPage;