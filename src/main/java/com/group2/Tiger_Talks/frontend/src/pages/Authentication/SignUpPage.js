import React, {useState} from "react";
import {Link, useNavigate} from "react-router-dom";
import axios from "axios";
import "../../assets/styles/Authentication/SignUpPage.css";
import {userSignUp} from "../../axios/AuthenticationAxios";

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

const SignUpPage = () => {
    const [form, setForm] = useState({
        firstName: "",
        lastName: "",
        age: "",
        gender: "",
        userName: "",
        email: "",
        password: "",
        confirmPassword: "",
        securityQuestion1: "",
        securityAnswer1: "",
        securityQuestion2: "",
        securityAnswer2: "",
        securityQuestion3: "",
        securityAnswer3: "",
    });

    const [errors, setErrors] = useState({});
    const navigate = useNavigate();

    const handleChange = (e) => {
        const {name, value} = e.target;
        setForm({
            ...form,
            [name]: value,
        });
    };

    const validate = () => {
        let errors = {};
        if (!form.firstName) errors.firstName = "First name is required";
        if (!form.lastName) errors.lastName = "Last name is required";
        if (!form.age) errors.age = "Age is required";
        else if (!Number.isInteger(Number(form.age)))
            errors.age = "Age must be an integer";
        if (!form.gender) errors.gender = "Gender is required";
        if (!form.userName) errors.userName = "User name is required";
        if (!form.email) errors.email = "Email is required";
        if (!form.password) errors.password = "Password is required";
        if (form.password !== form.confirmPassword)
            errors.confirmPassword = "Passwords do not match";
        if (!form.securityQuestion1)
            errors.securityQuestion1 = "Security question 1 is required";
        if (!form.securityAnswer1)
            errors.securityAnswer1 = "Answer for security question 1 is required";
        if (!form.securityQuestion2)
            errors.securityQuestion2 = "Security question 2 is required";
        if (!form.securityAnswer2)
            errors.securityAnswer2 = "Answer for security question 2 is required";
        if (!form.securityQuestion3)
            errors.securityQuestion3 = "Security question 3 is required";
        if (!form.securityAnswer3)
            errors.securityAnswer3 = "Answer for security question 3 is required";
        return errors;
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const errors = validate();
        setErrors(errors);
        if (Object.keys(errors).length === 0) {
            try {
                await userSignUp(form);
                alert("Sign up successfully");
                navigate("/");
            } catch (error) {
                if (error.response && error.response.data) {
                    const serverErrors = {};
                    const errorMessage = error.response.data;

                    switch (errorMessage) {
                        case "First name must contain no symbols":
                            serverErrors.firstName = errorMessage;
                            break;
                        case "Last name must contain no symbols":
                            serverErrors.lastName = errorMessage;
                            break;
                        case "Age must be grater than 0":
                            serverErrors.age = errorMessage;
                            break;
                        case "Username has already existed!":
                            serverErrors.userName = errorMessage;
                            break;
                        case "Invalid email address. Please use dal email address!":
                            serverErrors.email = errorMessage;
                            break;
                        case "Email has already existed!":
                            serverErrors.email = errorMessage;
                            break;
                        case "Password must have a minimum length of 8 characters.":
                            serverErrors.password = errorMessage;
                            break;
                        case "Password must have at least 1 uppercase character.":
                            serverErrors.password = errorMessage;
                            break;
                        case "Password must have at least 1 lowercase character.":
                            serverErrors.password = errorMessage;
                            break;
                        case "Password must have at least 1 number.":
                            serverErrors.password = errorMessage;
                            break;
                        case "Password must have at least 1 special character.":
                            serverErrors.password = errorMessage;
                            break;
                        default:
                           break;
                    }
                    setErrors(serverErrors);
                }
            }
        }
    };

    return (
        <div className="signup-page">
            <div className="signup-container">
                <h1>Create Your Account</h1>
                <form onSubmit={handleSubmit}>
                    <div className="form-group">
                        <input
                            type="text"
                            name="firstName"
                            placeholder="First name"
                            value={form.firstName}
                            onChange={handleChange}
                        />
                        {errors.firstName && <p className="error-css">{errors.firstName}</p>}
                    </div>

                    <div className="form-group">
                        <input
                            type="text"
                            name="lastName"
                            placeholder="Last name"
                            value={form.lastName}
                            onChange={handleChange}
                        />
                        {errors.lastName && <p className="error-css">{errors.lastName}</p>}
                    </div>

                    <div className="form-group">
                        <input
                            type="text"
                            name="age"
                            placeholder="Age"
                            value={form.age}
                            onChange={handleChange}
                        />
                        {errors.age && <p className="error-css">{errors.age}</p>}
                    </div>

                    <div className="form-group">
                        <input
                            type="text"
                            name="userName"
                            placeholder="User name"
                            value={form.userName}
                            onChange={handleChange}
                        />
                        {errors.userName && <p className="error-css">{errors.userName}</p>}
                    </div>

                    <div className="form-group">
                        <select name="gender" value={form.gender} onChange={handleChange}>
                            <option value="">Select Gender</option>
                            <option value="male">Male</option>
                            <option value="female">Female</option>
                            <option value="other">Other</option>
                        </select>
                        {errors.gender && <p className="error-css">{errors.gender}</p>}
                    </div>

                    <div className="form-group">
                        <input
                            type="email"
                            name="email"
                            placeholder="Email"
                            value={form.email}
                            onChange={handleChange}
                        />
                        {errors.email && <p className="error-css">{errors.email}</p>}
                    </div>

                    <div className="form-group">
                        <input
                            type="password"
                            name="password"
                            placeholder="Password"
                            value={form.password}
                            onChange={handleChange}
                        />
                        {errors.password && <p className="error-css">{errors.password}</p>}
                    </div>

                    <div className="form-group">
                        <input
                            type="password"
                            name="confirmPassword"
                            placeholder="Confirm Password"
                            value={form.confirmPassword}
                            onChange={handleChange}
                        />
                        {errors.confirmPassword && (
                            <p className="error-css">{errors.confirmPassword}</p>
                        )}
                    </div>

                    <div className="form-group">
                        <select
                            name="securityQuestion1"
                            value={form.securityQuestion1}
                            onChange={handleChange}
                        >
                            <option value="">Select Security Question 1</option>
                            {SECURITY_QUESTIONS.slice(0, 5).map((question, index) => (
                                <option key={index} value={question}>
                                    {question}
                                </option>
                            ))}
                        </select>
                        {errors.securityQuestion1 && (
                            <p className="error-css">{errors.securityQuestion1}</p>
                        )}
                    </div>

                    <div className="form-group">
                        <input
                            type="text"
                            name="securityAnswer1"
                            placeholder="Answer:"
                            value={form.securityAnswer1}
                            onChange={handleChange}
                        />
                        {errors.securityAnswer1 && (
                            <p className="error-css">{errors.securityAnswer1}</p>
                        )}
                    </div>

                    <div className="form-group">
                        <select
                            name="securityQuestion2"
                            value={form.securityQuestion2}
                            onChange={handleChange}
                        >
                            <option value="">Select Security Question 2</option>
                            {SECURITY_QUESTIONS.slice(5, 10).map((question, index) => (
                                <option key={index} value={question}>
                                    {question}
                                </option>
                            ))}
                        </select>
                        {errors.securityQuestion2 && (
                            <p className="error-css">{errors.securityQuestion2}</p>
                        )}
                    </div>

                    <div className="form-group">
                        <input
                            type="text"
                            name="securityAnswer2"
                            placeholder="Answer:"
                            value={form.securityAnswer2}
                            onChange={handleChange}
                        />
                        {errors.securityAnswer2 && (
                            <p className="error-css">{errors.securityAnswer2}</p>
                        )}
                    </div>

                    <div className="form-group">
                        <select
                            name="securityQuestion3"
                            value={form.securityQuestion3}
                            onChange={handleChange}
                        >
                            <option value="">Select Security Question 3</option>
                            {SECURITY_QUESTIONS.slice(10, 15).map((question, index) => (
                                <option key={index} value={question}>
                                    {question}
                                </option>
                            ))}
                        </select>
                        {errors.securityQuestion3 && (
                            <p className="error-css">{errors.securityQuestion3}</p>
                        )}
                    </div>

                    <div className="form-group">
                        <input
                            type="text"
                            name="securityAnswer3"
                            placeholder="Answer:"
                            value={form.securityAnswer3}
                            onChange={handleChange}
                        />
                        {errors.securityAnswer3 && (
                            <p className="error-css">{errors.securityAnswer3}</p>
                        )}
                    </div>

                    <button type="submit">Sign Up</button>
                </form>
                <p>
                    Already have an account? <Link to="/">Login</Link>
                </p>
            </div>
        </div>
    );
};

export default SignUpPage;
