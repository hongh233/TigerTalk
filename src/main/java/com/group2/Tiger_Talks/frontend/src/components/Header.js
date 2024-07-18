import React from "react";
import { useSelector } from "react-redux";
import NotificationButton from "./NotificationButton";
import SearchBar from "./SearchBar";
import "../assets/styles/Header.css";

const Header = () => {
	const user = useSelector((state) => state.user.user);

	return (
		<header className="header">
			<h1>
				<a href="/main" className="home-link">
					Tiger Talks
				</a>
			</h1>
			<NotificationButton />

			<SearchBar searchType="user" currentUser={user} />
		</header>
	);
};

export default Header;
