import React from "react";
import "../../assets/styles/Components/Main/NavBar.css";
import {userLogout} from "../../axios/Authentication/LoginAxios";
import {FaSignOutAlt} from "react-icons/fa";
import {IoHomeSharp} from "react-icons/io5";
import {BsChatDotsFill} from "react-icons/bs";
import {MdAdminPanelSettings} from "react-icons/md";
import {FaUserGroup, FaUserLarge} from "react-icons/fa6";
import { useDispatch, useSelector } from "react-redux";
import { NavLink, useNavigate } from "react-router-dom";


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

			<NavLink to="/main" className="navbar-icon-box">
				<IoHomeSharp /><span className="navbar-icon-text">Home</span>
			</NavLink>

			<NavLink to="/friends/message" className="navbar-icon-box">
				<BsChatDotsFill /><span className="navbar-icon-text">Chat</span>
			</NavLink>

			<NavLink to="/friends/friend-list" className="navbar-icon-box">
				<FaUserLarge /><span className="navbar-icon-text">Friend</span>
			</NavLink>

			<NavLink to="/group" className="navbar-icon-box">
				<FaUserGroup /><span className="navbar-icon-text">Group</span>
			</NavLink>

			{user.userLevel === "admin" && (
				<NavLink to="/admin" className="navbar-icon-box">
					<MdAdminPanelSettings /> <span className="navbar-icon-text">Admin</span>
				</NavLink>
			)}

			<NavLink to="/" onClick={handleLogOut} className="navbar-icon-box">
				<FaSignOutAlt /> <span className="navbar-icon-text">Logout</span>
			</NavLink>

		</nav>

	);
};
export default NavBar;
