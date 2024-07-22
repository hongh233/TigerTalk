import { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import Header from "../components/Header";
import NavBar from "../components/NavBar";
import "./../assets/styles/SearchPage.css";
const SearchPage = () => {
	const { globalUsers } = useSelector((state) => state.globalUsers);
	const { globalGroups } = useSelector((state) => state.globalGroups);

	console.log(globalGroups);
	return (
		<div className="search-page-container">
			<div className="search-content-header">
				<Header />
			</div>
			<div className="search-page">
				<div className="search-content-nav">
					<NavBar />
				</div>

				<div className="search-content-container">
					<h2>Users matched with keyword:</h2>
				</div>
			</div>
		</div>
	);
};

export default SearchPage;
