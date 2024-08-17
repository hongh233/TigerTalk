import React from "react";
import NotificationButton from "../Notification/NotificationButton";
import SearchBar from "../Search/SearchBar";
import "../../assets/styles/Components/Main/Header.css";
import {useSelector} from "react-redux";

const Header = () => {
	const user = useSelector((state) => state.user.user);

	return (
		<header className="header">
			<h1 className="header-title">
				<a href="/main" className="home-link">
					Tiger Talk
				</a>
			</h1>

			<SearchBar searchType="global" searchBarClassName={"header"} dropdownClassName={"header"}/>

			<NotificationButton />

			<div className="header-profile-picture">
				<a href={`/profile/${user.email}`}>
					<img src={user.profilePictureUrl} alt="user profile"/>
				</a>
				<div className="header-profile-popup">
					<p>Tiger Talk Account</p>
					<p>{user.userName}</p>
					<p>{user.email}</p>
				</div>
			</div>


		</header>
	);
};

export default Header;
