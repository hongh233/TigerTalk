import React from "react";
import { useSelector } from "react-redux";
import { useDispatch } from "react-redux";
import axios from "axios";
import { NavLink, useNavigate } from "react-router-dom";
import { FaHome, FaSignOutAlt, FaUser, FaUserShield } from "react-icons/fa";
import GroupTab from "./GroupTab";
import FriendsTab from "./FriendsTab";
import "../assets/styles/NavBar.css";
import { persistor } from "../redux/store";

const NavBar = () => {
	const dispatch = useDispatch();
	const navigate = useNavigate();
	const user = useSelector((state) => state.user.user);
	const handleLogOut = async () => {
		try {
			const response = await axios.post(
				`http://localhost:8085/api/logIn/userLogOut?email=${user.email}`
			);
			if (response.status === 200) {
				dispatch({ type: "SET_USER", payload: null });
				navigate("/");
			}
		} catch (error) {
			console.error("Failed to logout", error);
		}
	};

	return (
		<nav className="navbar">
			<NavLink to="/main">
				<FaHome />
				<span className="text-hide">Home</span>
			</NavLink>
			<GroupTab />
			<FriendsTab />
			<NavLink to={`/profile/${user.email}`}>
				<FaUser />
				<span className="text-hide">Account</span>
			</NavLink>
			{user.userLevel === "admin" && (
				<NavLink to="/admin">
					<FaUserShield /> <span className="text-hide">Admin</span>
				</NavLink>
			)}
			<NavLink to="/" onClick={handleLogOut}>
				<FaSignOutAlt /> <span className="text-hide">Logout</span>
			</NavLink>
		</nav>
	);
};
export default NavBar;
