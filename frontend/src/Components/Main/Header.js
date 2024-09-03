import React, {useRef, useState} from "react";
import "../../assets/styles/Components/Main/Header.css";
import {FaSignOutAlt} from "react-icons/fa";
import NotificationButton from "./NotificationButton";
import SearchBar from "../Search/SearchBar";
import {useDispatch, useSelector} from "react-redux";
import {NavLink, useNavigate} from "react-router-dom";
import {userLogout} from "../../axios/Authentication/LoginAxios";
import {BsChatDotsFill} from "react-icons/bs";
import {FaUserGroup, FaUserLarge} from "react-icons/fa6";
import {MdAccountCircle, MdAdminPanelSettings, MdOutlineSecurity} from "react-icons/md";


const Header = () => {
	const user = useSelector((state) => state.user.user);
	const [showProfilePopup, setShowProfilePopup] = useState(false);
	const profileTimeoutRef = useRef(null);
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


	return (
		<header className="header">

			<h1 className="header-title"><a href="/main" className="home-link">Tiger Talk</a></h1>

			<SearchBar searchType="global" searchBarClassName={"header"} dropdownClassName={"header"}/>

			<NavLink to="/friends/message" className="navbar-icon-box"><BsChatDotsFill /><span className="navbar-icon-text">Chat</span></NavLink>
			<NavLink to="/friends/friend-list" className="navbar-icon-box"><FaUserLarge /><span className="navbar-icon-text">Friend</span></NavLink>
			<NavLink to="/group" className="navbar-icon-box"><FaUserGroup /><span className="navbar-icon-text">Group</span></NavLink>

			<NotificationButton />

			<div className="header-profile-picture" onMouseEnter={handleProfileMouseEnter} onMouseLeave={handleProfileMouseLeave}>
				<div>
					<img src={user.profilePictureUrl} alt="user profile"/>
				</div>
				{showProfilePopup && (
					<div className="header-profile-popup">
						<p className="title">Tiger Talk Account</p>
						<p className="userName">{user.userName}</p>
						<p className="email">{user.email}</p>
						<hr />
						<div className="navbar-user-image-button-list" onClick={() => navigate(`/profile/${user.email}`)}>
							<MdAccountCircle /> My Profile
						</div>
						<div className="navbar-user-image-button-list" onClick={() => navigate("/security")}>
							<MdOutlineSecurity /> Security
						</div>
						<div className="navbar-user-image-button-list" onClick={handleLogOut}>
							<FaSignOutAlt /> Logout
						</div>
						{user.userLevel === "admin" && (
							<div className="navbar-user-image-button-list" onClick={() => navigate("/admin")}>
								<MdAdminPanelSettings /> Admin
							</div>
						)}
					</div>
				)}
			</div>


		</header>
	);
};

export default Header;
