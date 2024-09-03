import React, { useState } from "react";
import "../../assets/styles/Pages/Profile/SecurityPage.css";
import Header from "../../Components/Main/Header";
import { useDispatch, useSelector } from "react-redux";
import {updateUserPassword} from "../../axios/UserAxios";
import {setUser} from "../../redux/actions/userActions";

const SECURITY_QUESTIONS = [
    "What was your favourite book as a child?",
    "What school did you attend for grade nine?",
    "What was the last name of your grade three teacher?",
    "What is your favourite person from history?",
    "What was the first video game you have ever played?",
    "In what city were you born?",
    "What was the name of your elementary school?",
    "What was the name of your best friend in high school?",
    "What was your childhood nickname?",
    "Where did you go on your first vacation?",
    "What is the name of the hospital where you were born?",
    "What is your favourite food?",
    "What is the name of your favorite childhood toy?",
    "What is your favorite movie?",
    "What is your favorite restaurant?",
];

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

    const handleSecurityQuestionChange = (e) => {
        e.preventDefault();
        if (oldSecurityAnswer !== user.securityQuestionAnswer) {
            setErrors({ oldSecurityAnswer: "Incorrect answer to the old security question" });
            return;
        }

        // TODO: Implement security question update logic
        console.log("Security question updated to:", newSecurityQuestion);
    };

    return (
        <div className="main-page">
            <Header />
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
                        <input
                            type="text"
                            placeholder="New Answer"
                            value={newSecurityAnswer}
                            onChange={(e) => setNewSecurityAnswer(e.target.value)}
                        />
                        <button onClick={handleSecurityQuestionChange}>Update Security Question</button>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default SecurityPage;
