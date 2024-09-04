import React, { useState } from "react";
import "../../assets/styles/Pages/Authentication/LoginPage.css";
import {userLogin} from "../../axios/Authentication/LoginAxios";
import { Link, useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";
import {getCurrentUser} from "../../axios/UserAxios";


const LoginPage = () => {
	const [email, setEmail] = useState("");
	const [password, setPassword] = useState(""); // test password: aaaa1A@a
	const [errors, setErrors] = useState("");
	const dispatch = useDispatch();
	const navigate = useNavigate();

	const handleSubmit = async (e) => {
		e.preventDefault();
		setErrors({});

		try {
			await getCurrentUser(email);

			try {
				const userProfile = await userLogin(email, password);
				dispatch({ type: "SET_USER", payload: userProfile });
				alert("Log in successfully");
				navigate("/main", { state: { userProfile } });
			} catch (loginError) {
				if (loginError.response && loginError.response.status === 401) {
					setErrors((prevErrors) => ({
						...prevErrors, password: "Invalid password."}));
				} else {
					setErrors((prevErrors) => ({
						...prevErrors, password: "An error occurred during Log In. Please try again later."}));
				}
			}
		} catch (error) {
			if (error.response && error.response.status === 404) {
				setErrors((prevErrors) => ({
					...prevErrors, email: "User not found. Please check your email."}));
			} else {
				setErrors((prevErrors) => ({
					...prevErrors, email: "An error occurred while checking the user. Please try again later."}));
			}
		}
	};

	return (
		<div className="login-container">
			<div className="login-card">
				<h2>Welcome Back</h2>
				<p className="login-card-second-title">Login with Email and Password</p>

				<form onSubmit={handleSubmit}>

					<label>Email</label>
					<div>
						<input
							type="email"
							placeholder="Enter your email"
							value={email}
							onChange={(e) => setEmail(e.target.value)}
							required
						/>
						{errors.email && (<p className="error-css">{errors.email}</p>)}
					</div>

					<label>Password</label>
					<div>
						<input
							type="password" placeholder="Enter your password" value={password}
							onChange={(e) => setPassword(e.target.value)}
							required
						/>
						{errors.password && (<p className="error-css">{errors.password}</p>)}
					</div>

					<button type="submit">Login</button>
				</form>
				<div className="additional-links">
					<Link to="/forgotPassword">Forgot Password?</Link>
					<p>New User? <Link to="/signup">Sign Up Here Instead</Link></p>
				</div>
			</div>
		</div>
	);
};

export default LoginPage;
