import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import axios from "axios";
import { useUser } from "../context/UserContext";
import "../assets/styles/Login.css";
import { setUser } from "../redux/actions/userActions";
import { useDispatch } from "react-redux";

const LoginPage = () => {
	const [email, setEmail] = useState("");
	const [password, setPassword] = useState("");
	const [error, setError] = useState("");
	const dispatch = useDispatch();
	const { setUser } = useUser();
	const navigate = useNavigate();

	const handleSubmit = async (e) => {
		e.preventDefault();
		setError("");

		try {
			const response = await axios.post(
				"http://localhost:8085/api/logIn/userLogIn",
				null,
				{
					params: { email, password },
				}
			);
			const userProfile = response.data;
			localStorage.setItem("user", JSON.stringify(userProfile));
			dispatch(setUser(userProfile));
			alert("Log in successfully");
			navigate("/main", { state: { userProfile } });
		} catch (error) {
			if (error.response && error.response.status === 401) {
				setError("Invalid email or password.");
			} else {
				setError("An error occurred during Log In. Please try again later.");
			}
		}
	};

	return (
		<div className="login-container">
			<div className="login-card">
				<h2>Welcome Back</h2>
				<p>Login with Email and Password</p>

				{error && (
					<div className="error-message">
						<p>{error}</p>
					</div>
				)}

				<form onSubmit={handleSubmit}>
					<label>Email</label>
					<input
						type="email"
						placeholder="Enter your email"
						value={email}
						onChange={(e) => setEmail(e.target.value)}
						required
					/>

					<label>Password</label>
					<input
						type="password"
						placeholder="Enter your password"
						value={password}
						onChange={(e) => setPassword(e.target.value)}
						required
					/>

					<button type="submit">Login</button>
				</form>
				<div className="additional-links">
					<Link to="/forgotPassword">Forgot Password?</Link>
					<p>
						New User? <Link to="/signup">Sign Up Here Instead</Link>
					</p>
				</div>
			</div>
		</div>
	);
};

export default LoginPage;
