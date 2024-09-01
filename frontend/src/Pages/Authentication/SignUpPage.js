import React, {useState} from "react";
import "../../assets/styles/Pages/Authentication/SignUpPage.css";
import {userSignUp} from "../../axios/Authentication/SignUpAxios";
import {Link, useNavigate} from "react-router-dom";


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
        securityQuestion: "",
        securityAnswer: "",
    });

    const [errors, setErrors] = useState({});
    const navigate = useNavigate();

    const handleChange = (e) => {
        const {name, value} = e.target;
        setForm({...form, [name]: value.trim()});
    };

    const validate = () => {
        let errors = {};
        if (!form.firstName) errors.firstName = "First name is required";
        if (!form.lastName) errors.lastName = "Last name is required";
        if (!form.age) errors.age = "Age is required";
        else if (!Number.isInteger(Number(form.age)) || Number(form.age) <= 0) errors.age = "Age must be a positive integer.";
        if (!form.gender) errors.gender = "Gender is required";
        if (!form.userName) errors.userName = "User name is required";
        if (!form.email) errors.email = "Email is required";
        if (!form.password) errors.password = "Password is required";
        if (form.password !== form.confirmPassword) errors.confirmPassword = "Passwords do not match";
        if (!form.securityQuestion) errors.securityQuestion = "Security question is required";
        if (!form.securityAnswer) errors.securityAnswer = "Answer for security question is required";
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
                        <input type="text" name="firstName" placeholder="First name" value={form.firstName} onChange={handleChange}/>
                        {errors.firstName && <p className="error-css">{errors.firstName}</p>}
                    </div>

                    <div className="form-group">
                        <input type="text" name="lastName" placeholder="Last name" value={form.lastName} onChange={handleChange}/>
                        {errors.lastName && <p className="error-css">{errors.lastName}</p>}
                    </div>

                    <div className="form-group">
                        <input type="text" name="age" placeholder="Age" value={form.age} onChange={handleChange}/>
                        {errors.age && <p className="error-css">{errors.age}</p>}
                    </div>

                    <div className="form-group">
                        <input type="text" name="userName" placeholder="User name" value={form.userName} onChange={handleChange}/>
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
                        <input type="email" name="email" placeholder="Email" value={form.email} onChange={handleChange}/>
                        {errors.email && <p className="error-css">{errors.email}</p>}
                    </div>

                    <div className="form-group">
                        <input type="password" name="password" placeholder="Password" value={form.password} onChange={handleChange}/>
                        {errors.password && <p className="error-css">{errors.password}</p>}
                    </div>

                    <div className="form-group">
                        <input type="password" name="confirmPassword" placeholder="Confirm Password" value={form.confirmPassword} onChange={handleChange}/>
                        {errors.confirmPassword && (
                            <p className="error-css">{errors.confirmPassword}</p>
                        )}
                    </div>

                    <div className="form-group">
                        <select name="securityQuestion" value={form.securityQuestion} onChange={handleChange}>
                            <option value="">Select Security Question</option>
                            {SECURITY_QUESTIONS.map(
                                (question, index) => (
                                    <option key={index} value={question}>{question}</option>
                                ))
                            }
                        </select>
                        {errors.securityQuestion && (<p className="error-css">{errors.securityQuestion}</p>)}
                    </div>

                    <div className="form-group">
                        <input type="text" name="securityAnswer" placeholder="Answer:"
                               value={form.securityAnswer} onChange={handleChange}/>
                        {errors.securityAnswer &&
                            <p className="error-css">{errors.securityAnswer}</p>
                        }
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
