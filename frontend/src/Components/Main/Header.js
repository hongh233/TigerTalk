import React, {useRef, useState} from "react";
import "../../assets/styles/Components/Main/Header.css";
import {FaBars, FaSignOutAlt} from "react-icons/fa";
import NotificationButton from "../Notification/NotificationButton";
import SearchBar from "../Search/SearchBar";
import {useDispatch, useSelector} from "react-redux";
import {NavLink, useNavigate} from "react-router-dom";
import {userLogout} from "../../axios/Authentication/LoginAxios";
import {IoHomeSharp} from "react-icons/io5";
import {BsChatDotsFill} from "react-icons/bs";
import {FaUserGroup, FaUserLarge} from "react-icons/fa6";
import {MdAdminPanelSettings} from "react-icons/md";


const Header = () => {
	const user = useSelector((state) => state.user.user);
	const [showProfilePopup, setShowProfilePopup] = useState(false);
	const profileTimeoutRef = useRef(null);
	const [isNavbarVisible, setIsNavbarVisible] = useState(true);
	const dispatch = useDispatch();
	const navigate = useNavigate();

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

	const handleProfileMouseEnter = () => {
		if (profileTimeoutRef.current) {
			clearTimeout(profileTimeoutRef.current);
		}
		profileTimeoutRef.current = setTimeout(() => {
			setShowProfilePopup(true);
		}, 300);
	};

	const handleProfileMouseLeave = () => {
		if (profileTimeoutRef.current) {
			clearTimeout(profileTimeoutRef.current);
		}
		profileTimeoutRef.current = setTimeout(() => {
			setShowProfilePopup(false);
		}, 300);
	};

	const toggleNavbar = () => {
		setIsNavbarVisible(!isNavbarVisible);
	};

	return (
		<header className="header">
			<button className="navbar-toggle-button" onClick={toggleNavbar}><FaBars /></button>

			<h1 className="header-title"><a href="/main" className="home-link">Tiger Talk</a></h1>

			<SearchBar searchType="global" searchBarClassName={"header"} dropdownClassName={"header"}/>

			<NotificationButton />

			<div className="header-profile-picture" onMouseEnter={handleProfileMouseEnter} onMouseLeave={handleProfileMouseLeave}>
				<a href={`/profile/${user.email}`}><img src={user.profilePictureUrl} alt="user profile"/></a>
				{showProfilePopup && (<div className="header-profile-popup"><p>Tiger Talk Account</p><p>{user.userName}</p><p>{user.email}</p></div>)}
			</div>

			<nav className={`navbar ${isNavbarVisible ? 'navbar-visible' : 'navbar-hidden'}`}>
				<NavLink to="/main" className="navbar-icon-box"><IoHomeSharp /><span className="navbar-icon-text">Home</span></NavLink>
				<NavLink to="/friends/message" className="navbar-icon-box"><BsChatDotsFill /><span className="navbar-icon-text">Chat</span></NavLink>
				<NavLink to="/friends/friend-list" className="navbar-icon-box"><FaUserLarge /><span className="navbar-icon-text">Friend</span></NavLink>
				<NavLink to="/group" className="navbar-icon-box"><FaUserGroup /><span className="navbar-icon-text">Group</span></NavLink>
				{user.userLevel === "admin" && (
					<NavLink to="/admin" className="navbar-icon-box"><MdAdminPanelSettings /> <span className="navbar-icon-text">Admin</span></NavLink>
				)}
				<NavLink to="/" onClick={handleLogOut} className="navbar-icon-box"><FaSignOutAlt /> <span className="navbar-icon-text">Logout</span></NavLink>
			</nav>

		</header>
	);
};

export default Header;
