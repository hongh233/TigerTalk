import React, {useState} from "react";
import "../../assets/styles/Components/Main/Header.css";
import NotificationButton from "./NotificationButton";
import {useNavigate} from "react-router-dom";
import {IoSearch} from "react-icons/io5";


const Header = () => {
	const navigate = useNavigate();
	const [searchContent, setSearchContent] = useState("");


	return (
		<header className="header">

			<h1 className="tiger-talk-logo">
				<a href="/main">Tiger Talk</a>
			</h1>

			<div className="search-bar-header">
				<div className="search-input-and-icon">
					<IoSearch className="inside-bar-search-icon" />
					<input
						type="text"
						placeholder="Search..."
						value={searchContent}
						onChange={(e) => {
							setSearchContent(e.target.value);
						}}
						onKeyDown={(e) => {
							if (e.key === "Enter" && searchContent) {
								navigate("/search", { state: { content: searchContent } })
							}
						}}
					/>
				</div>
			</div>

			<NotificationButton />

		</header>
	);
};

export default Header;
