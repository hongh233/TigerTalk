import React, {useRef, useState} from "react";
import NotificationButton from "../Notification/NotificationButton";
import SearchBar from "../Search/SearchBar";
import {useSelector} from "react-redux";
// CSS:
import "../../assets/styles/Components/Main/Header.css";


const Header = () => {
	const user = useSelector((state) => state.user.user);
	const [showProfilePopup, setShowProfilePopup] = useState(false);
	const profileTimeoutRef = useRef(null);

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
			<h1 className="header-title">
				<a href="/main" className="home-link">Tiger Talk</a>
			</h1>

			<SearchBar searchType="global" searchBarClassName={"header"} dropdownClassName={"header"}/>

			<NotificationButton />

			<div
				className="header-profile-picture"
				onMouseEnter={handleProfileMouseEnter}
				onMouseLeave={handleProfileMouseLeave}
			>
				<a href={`/profile/${user.email}`}>
					<img src={user.profilePictureUrl} alt="user profile"/>
				</a>
				{showProfilePopup && (
					<div className="header-profile-popup">
						<p>Tiger Talk Account</p>
						<p>{user.userName}</p>
						<p>{user.email}</p>
					</div>
				)}
			</div>


		</header>
	);
};

export default Header;
