import React, { useState } from "react";
import "../../assets/styles/Pages/Authentication/LoginPage.css";
import {userLogin} from "../../axios/Authentication/LoginAxios";
import { Link, useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";
import {getCurrentUser} from "../../axios/UserAxios";
import {getAllFriendsDTO} from "../../axios/Friend/FriendshipAxios";
import {setFriends, setFriendshipRequests} from "../../redux/actions/friendActions";
import {getAllFriendRequests} from "../../axios/Friend/FriendshipRequestAxios";


const LoginPage = () => {
	const [email, setEmail] = useState("@dal.ca");
	const [password, setPassword] = useState("aaaa1A@a"); // test password: "aaaa1A@a" / ""
	const [errors, setErrors] = useState("");
	const dispatch = useDispatch();
	const navigate = useNavigate();
	const [loading, setLoading] = useState(false);


	const handleSubmit = async (e) => {
		e.preventDefault();
		setErrors({});
		setLoading(true);

		try {
			await getCurrentUser(email);

			try {
				const userProfile = await userLogin(email, password);
				dispatch({ type: "SET_USER", payload: userProfile });

				// Fetch friends and update Redux
				const friends = await getAllFriendsDTO(email);
				dispatch(setFriends(friends));

				// Fetch friendship requests and update Redux
				const friendshipRequests = await getAllFriendRequests(email);
				dispatch(setFriendshipRequests(friendshipRequests));

				navigate("/main", { state: { userProfile }, replace: true });
			} catch (loginError) {
				setLoading(false);
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
					...prevErrors,
					email: "User not found. Please check your email."}));
			} else {
				setErrors((prevErrors) => ({
					...prevErrors,
					email: "An error occurred while checking the user. Please try again later."}));
			}
		}
	};

	return (
		<div className="login-container">
			<div className="login-card">
				{loading ? (
					<p>Loading...</p>
				) : (
					<>
						<h2>Welcome Back</h2>
						<p className="login-card-second-title">Login with Email and Password</p>

						<form onSubmit={handleSubmit}>

							<label>
								Email <small style={{color: '#999', fontWeight: 'normal'}}>[Admin: a to z@dal.ca]</small>
							</label>
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

							<label>
								Password <small style={{color: '#999', fontWeight: 'normal'}}>[Admin: aaaa1A@a]</small>
							</label>
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
					</>
				)}
			</div>
		</div>
	);
};

export default LoginPage;
