import React, {useRef, useState} from "react";
import "../../assets/styles/Components/Main/Header.css";
import {FaBars} from "react-icons/fa";
import NavBar from "./NavBar";
import NotificationButton from "../Notification/NotificationButton";
import SearchBar from "../Search/SearchBar";
import {useSelector} from "react-redux";


const Header = () => {
	const user = useSelector((state) => state.user.user);
	const [showProfilePopup, setShowProfilePopup] = useState(false);
	const profileTimeoutRef = useRef(null);
	const [isNavbarVisible, setIsNavbarVisible] = useState(true);


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
				{showProfilePopup && (
					<div className="header-profile-popup"><p>Tiger Talk Account</p><p>{user.userName}</p><p>{user.email}</p></div>
				)}
			</div>

			{isNavbarVisible && <NavBar />}

		</header>
	);
};

export default Header;
