import React, {useState} from "react";
import "../../assets/styles/Pages/Admin/AdminAddPage.css";
import {checkEmailExists, userSignUp} from "../../axios/Authentication/SignUpAxios";
import {useNavigate} from "react-router-dom";
import { SECURITY_QUESTIONS } from "../../utils/securityQuestions";


const AdminAddPage = () => {
    const [form, setForm] = useState({
        email: "",
        userName: "",
        gender: "",
        password: "",
        confirmPassword: "",
        securityQuestion: "",
        securityQuestionAnswer: "",
    });

    const [errors, setErrors] = useState({});
    const navigate = useNavigate();

    const handleChange = (e) => {
        const {name, value} = e.target;
        setForm({...form, [name]: value});
    };

    const validate = async () => {
        let errors = {};

        // Email check
        if (!form.email) {
            errors.email = "Email is required";
        } else if (!form.email.includes("@")) {
            errors.email = "Invalid email address. Must contain @";
        } else {
            try {
                const emailExists = await checkEmailExists(form.email);
                if (emailExists) {
                    errors.email = "Email already exists. Please choose another.";
                }
            } catch (error) {
                console.error("Error checking email existence:", error);
                errors.email = "Unable to check email availability. Please try again.";
            }
        }

        // Username check
        if (!form.userName) {
            errors.userName = "User Name is required";
        } else if (form.userName.length < 3 || form.userName.length > 20) {
            errors.userName = "User name must be between 3 and 20 characters long.";
        } else if (/[^A-Za-z0-9!@#$%^&*<>?]/.test(form.userName)) {
            errors.userName = "User Name contains invalid characters. Only letters, numbers, and characters !@#$%^&*<>? are allowed.";
        }

        // Pronouns check
        if (!form.gender) {
            errors.gender = "Pronouns is required";
        }

        // Password check
        if (!form.password) {
            errors.password = "Password is required";
        } else {
            if (form.password.length < 8 || form.password.length > 30) {
                errors.password = "Password must be between 8 and 30 characters long.";
            } else if (/[^A-Za-z0-9!@#$%^&*<>?]/.test(form.password)) {
                errors.password = "Password contains invalid characters. Only letters, numbers, and characters !@#$%^&*<>? are allowed.";
            }
        }
        // Password Confirm check
        if (form.password !== form.confirmPassword) {
            errors.confirmPassword = "Passwords do not match";
        }

        // Security Question and Answer check
        if (!form.securityQuestion) {
            errors.securityQuestion = "Security question is required";
        }
        if (!form.securityQuestionAnswer) {
            errors.securityQuestionAnswer = "Answer for security question is required";
        } else if (form.securityQuestionAnswer.length < 5 || form.securityQuestionAnswer.length > 50) {
            errors.securityQuestionAnswer = "Answer must be between 5 and 50 characters long.";
        }
        return errors;
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const errors = await validate();
        setErrors(errors);

        if (Object.keys(errors).length === 0) {
            try {
                await userSignUp(form);
                alert("Added user successfully");
                navigate("/admin");
            } catch (error) {
                alert("An error occurred during signup. Please try again later.");
            }
        }
    };

    return (
        <div className="admin-add-page">
            <div className="admin-add-container">
                <h1>Add a user to the site</h1>
                <form onSubmit={handleSubmit}>

                    <div className="form-group">
                        <input type="email" name="email" placeholder="Email"
                               value={form.email} onChange={handleChange}/>
                        {errors.email &&
                            <p className="error-css">{errors.email}</p>
                        }
                    </div>

                    <div className="form-group">
                        <input type="text" name="userName" placeholder="User name"
                               value={form.userName} onChange={handleChange}/>
                        {errors.userName &&
                            <p className="error-css">{errors.userName}</p>
                        }
                    </div>

                    <div className="form-group">
                        <select name="gender"
                                value={form.gender} onChange={handleChange}>
                            <option value="">Select Pronouns</option>
                            <option value="Don't specify">Don't specify</option>
                            <option value="they/them">they/them</option>
                            <option value="she/her">she/her</option>
                            <option value="he/him">he/him</option>
                            <option value="other">other</option>
                        </select>
                        {errors.gender &&
                            <p className="error-css">{errors.gender}</p>
                        }
                    </div>

                    <div className="form-group">
                        <input type="password" name="password" placeholder="Password"
                               value={form.password} onChange={handleChange}/>
                        {errors.password &&
                            <p className="error-css">{errors.password}</p>
                        }
                    </div>

                    <div className="form-group">
                        <input type="password" name="confirmPassword" placeholder="Confirm Password"
                               value={form.confirmPassword} onChange={handleChange}/>
                        {errors.confirmPassword && (
                            <p className="error-css">{errors.confirmPassword}</p>
                        )}
                    </div>

                    <div className="form-group">
                        <select name="securityQuestion"
                                value={form.securityQuestion} onChange={handleChange}>
                            <option value="">Select Security Question</option>
                            {SECURITY_QUESTIONS.map(
                                (question, index) => (
                                    <option key={index} value={question}>{question}</option>
                                ))
                            }
                        </select>
                        {errors.securityQuestion &&
                            <p className="error-css">{errors.securityQuestion}</p>
                        }
                    </div>

                    <div className="form-group">
                        <input type="text" name="securityQuestionAnswer" placeholder="Answer:"
                               value={form.securityQuestionAnswer} onChange={handleChange}/>
                        {errors.securityQuestionAnswer &&
                            <p className="error-css">{errors.securityQuestionAnswer}</p>
                        }
                    </div>

                    <button type="submit">Add User</button>
                </form>
            </div>
        </div>
    );
};

export default AdminAddPage;
