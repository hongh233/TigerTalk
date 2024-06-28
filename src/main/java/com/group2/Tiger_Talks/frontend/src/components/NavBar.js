import React from "react";
import { NavLink } from "react-router-dom";
import { FaHome, FaSignOutAlt, FaUser, FaUserShield } from "react-icons/fa";
import GroupTab from "./GroupTab";
import FriendsTab from "./FriendsTab";
import "../assets/styles/NavBar.css";
import { useUser } from "../context/UserContext";

const NavBar = () => {
	const handleLogOut = () => {
		localStorage.removeItem("user");
	};
	const { user } = useUser();

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
