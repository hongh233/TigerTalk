import React, { useState } from "react";
import "../../assets/styles/Pages/Profile/SecurityPage.css";
import Header from "../../Components/Main/Header";
import { useDispatch, useSelector } from "react-redux";
import {updateUserPassword, updateUserSecurityQuestionAndAnswer} from "../../axios/UserAxios";
import {setUser} from "../../redux/actions/userActions";
import { SECURITY_QUESTIONS } from "../../utils/securityQuestions";


const SecurityPage = () => {
    const user = useSelector((state) => state.user.user);
    const dispatch = useDispatch();

    const [currentPassword, setCurrentPassword] = useState("");
    const [newPassword, setNewPassword] = useState("");
    const [confirmPassword, setConfirmPassword] = useState("");
    const [oldSecurityQuestion, setOldSecurityQuestion] = useState("");
    const [oldSecurityAnswer, setOldSecurityAnswer] = useState("");
    const [newSecurityQuestion, setNewSecurityQuestion] = useState("");
    const [newSecurityAnswer, setNewSecurityAnswer] = useState("");
    const [errors, setErrors] = useState({});


    const validatePasswords = () => {
        let errors = {};

        if (user.password !== currentPassword) {
            errors.currentPassword = "Incorrect current password";
        }

        if (!newPassword) {
            errors.newPassword = "New password is required";
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

        if (newPassword !== confirmPassword) {
            errors.confirmPassword = "Passwords do not match";
        }

        return errors;
    };

    const validateSecurityQuestions = () => {
        let errors = {};

         if (user.securityQuestion !== oldSecurityQuestion) {
             errors.oldSecurityQuestion = "Old security question does not match";
         }

        if (!oldSecurityAnswer) {
            errors.oldSecurityAnswer = "Answer to the old security question is required";
        } else if (oldSecurityAnswer !== user.securityQuestionAnswer) {
            errors.oldSecurityAnswer = "Incorrect answer to the old security question";
        }

         if (!newSecurityQuestion) {
             errors.newSecurityQuestion = "New security question is required";
         }

        if (!newSecurityAnswer) {
            errors.newSecurityAnswer = "New answer for security question is required";
        } else if (newSecurityAnswer.length < 5 || newSecurityAnswer.length > 50) {
            errors.newSecurityAnswer = "Answer must be between 5 and 50 characters long.";
        }

        return errors;
    }

    const handlePasswordChange = async (e) => {
        e.preventDefault();
        const errors = validatePasswords();
        setErrors(errors);

        if (Object.keys(errors).length === 0) {
            try {
                await updateUserPassword(user.email, newPassword);
                const updatedUser = { ...user, password: newPassword };
                dispatch(setUser(updatedUser));
                alert("Password updated successfully");
                window.location.reload();
            } catch (error) {
                console.error("Error updating password:", error);
                alert("An error occurred while updating the password. Please try again.");
            }
        }
    };

    const handleSecurityQuestionChange = async (e) => {
        e.preventDefault();
        const errors = validateSecurityQuestions();
        setErrors(errors);

        if (Object.keys(errors).length === 0) {
            try {
                await updateUserSecurityQuestionAndAnswer(user.email, newSecurityQuestion, newSecurityAnswer);
                const updatedUser = {
                    ...user,
                    securityQuestion: newSecurityQuestion,
                    securityQuestionAnswer: newSecurityAnswer
                };
                dispatch(setUser(updatedUser));
                alert("Security question and answer updated successfully.");
                window.location.reload();
            } catch (error) {
                console.error("Error updating security question and answer:", error);
                alert("An error occurred while updating the security question and answer. Please try again.");
            }
        }
    };

    return (
        <div className="main-page">
            <div className="content">

                <div className="security-page">
                    <h2>Security Settings</h2>

                    {/* Change Password Section */}
                    <div className="security-form">
                        <h3>Change Password</h3>
                        <input
                            type="password"
                            placeholder="Current Password"
                            value={currentPassword}
                            onChange={(e) => setCurrentPassword(e.target.value)}
                        />
                        {errors.currentPassword && (
                            <p className="error-css">{errors.currentPassword}</p>
                        )}

                        <input
                            type="password"
                            placeholder="New Password"
                            value={newPassword}
                            onChange={(e) => setNewPassword(e.target.value)}
                        />
                        {errors.newPassword && (
                            <p className="error-css">{errors.newPassword}</p>
                        )}

                        <input
                            type="password"
                            placeholder="Confirm New Password"
                            value={confirmPassword}
                            onChange={(e) => setConfirmPassword(e.target.value)}
                        />
                        {errors.confirmPassword && (
                            <p className="error-css">{errors.confirmPassword}</p>
                        )}

                        <button onClick={handlePasswordChange}>Update Password</button>
                    </div>


                    {/* Update Security Question Section */}
                    <div className="security-form">
                        <h3>Update Security Question</h3>

                        <select
                            value={oldSecurityQuestion}
                            onChange={(e) => setOldSecurityQuestion(e.target.value)}
                        >
                            <option value="" disabled>Select your old security question</option>
                            {SECURITY_QUESTIONS.map((question, index) => (
                                <option key={index} value={question}>
                                    {question}
                                </option>
                            ))}
                        </select>
                        {errors.oldSecurityQuestion && (
                            <p className="error-css">{errors.oldSecurityQuestion}</p>
                        )}


                        <input
                            type="text"
                            placeholder="Old Answer"
                            value={oldSecurityAnswer}
                            onChange={(e) => setOldSecurityAnswer(e.target.value)}
                        />
                        {errors.oldSecurityAnswer && (
                            <p className="error-css">{errors.oldSecurityAnswer}</p>
                        )}


                        <select
                            value={newSecurityQuestion}
                            onChange={(e) => setNewSecurityQuestion(e.target.value)}
                        >
                            <option value="" disabled>Select a new security question</option>
                            {SECURITY_QUESTIONS.map((question, index) => (
                                <option key={index} value={question}>
                                    {question}
                                </option>
                            ))}
                        </select>
                        {errors.newSecurityQuestion && (
                            <p className="error-css">{errors.newSecurityQuestion}</p>
                        )}


                        <input
                            type="text"
                            placeholder="New Answer"
                            value={newSecurityAnswer}
                            onChange={(e) => setNewSecurityAnswer(e.target.value)}
                        />
                        {errors.newSecurityAnswer && (
                            <p className="error-css">{errors.newSecurityAnswer}</p>
                        )}


                        <button onClick={handleSecurityQuestionChange}>Update Security Question</button>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default SecurityPage;
