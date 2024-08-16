import React from "react";
import NotificationButton from "../Notification/NotificationButton";

import SearchBar from "../Search/SearchBar";
import "../../assets/styles/Components/Main/Header.css";

const Header = () => {
	return (
		<header className="header">
			<h1 className="header-title">
				<a href="/main" className="home-link">
					Tiger Talks
				</a>
			</h1>

			<NotificationButton />

			<SearchBar
				searchType="global"
				searchBarClassName={"header"}
				dropdownClassName={"header"}
			/>
		</header>
	);
};

export default Header;
