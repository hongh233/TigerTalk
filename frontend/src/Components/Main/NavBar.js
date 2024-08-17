import React from "react";
import { useDispatch, useSelector } from "react-redux";
import { NavLink, useNavigate } from "react-router-dom";
import GroupTab from "./GroupTab";
import FriendsTab from "./FriendsTab";
// Icon:
import { FaHome, FaSignOutAlt, FaUserShield } from "react-icons/fa";
// Axio:
import {userLogout} from "../../axios/Authentication/LoginAxios";
// CSS:
import "../../assets/styles/Components/Main/NavBar.css";


const NavBar = () => {
	const dispatch = useDispatch();
	const navigate = useNavigate();
	const user = useSelector((state) => state.user.user);
	const handleLogOut = async () => {
		try {
			const response = await userLogout(user.email);
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
