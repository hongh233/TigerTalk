import React, { useEffect, useState } from "react";
import axios from "axios";
import { handleFindGroups } from "./../axios/GroupAxios";
import "../assets/styles/Header.css";
import Group from "./Group";
const SearchBar = ({ searchType, userEmail, setSearchGroup }) => {
	const [searchQuery, setSearchQuery] = useState("");
	const [searchResults, setSearchResults] = useState([]);
	const [allItems, setAllItems] = useState(null);
	const [showDropdown, setShowDropdown] = useState(false);

	useEffect(() => {
		fetchAllItems();
	}, []);

	const fetchAllItems = async () => {
		try {
			const response = await axios.get(
				`http://localhost:8085/api/user/getAllProfiles`
			);
			setAllItems(response.data);
		} catch (error) {
			console.error(`Error fetching all ${searchType}s:`, error);
		}
	};

	const handleInputChange = (e) => {
		setSearchQuery(e.target.value);
	};

	const handleSearch = async () => {
		let data;
		if (searchQuery.length > 0) {
			if (searchType === "user") {
				data = await fetchAllItems();
			} else if (searchType === "group") {
				data = await handleFindGroups(
					searchQuery.toLocaleLowerCase(),
					userEmail
				);
				setSearchGroup(data);
			}
		}
		setAllItems(data);
	};
	const handleSearchGroup = async (e) => {
		setSearchQuery(e.target.value);
		let data =
			searchQuery &&
			(await handleFindGroups(searchQuery.toLocaleLowerCase(), userEmail));
		setSearchGroup(data);
	};

	// const filterItems = (query) => {
	// 	const filteredItems = allItems.filter((item) => {
	// 		const searchFields =
	// 			searchType === "user"
	// 				? [item.firstName, item.lastName, item.email]
	// 				: [item.groupName, item.groupDescription];
	// 		return (
	// 			searchFields.some((field) =>
	// 				field.toLowerCase().includes(query.toLowerCase())
	// 			) &&
	// 			searchType === "user" &&
	// 			item.email.toLowerCase() !== currentUser.email
	// 		);
	// 	});
	// 	setSearchResults(filteredItems);
	// };

	const getStatusColor = (status) => {
		switch (status) {
			case "available":
				return "green";
			case "busy":
				return "#DC143C";
			case "away":
				return "#FDDA0D";
			default:
				return "gray";
		}
	};

	return (
		<div className="search-bar">
			{searchType === "user" ? (
				<input
					type="text"
					placeholder="Search..."
					value={searchQuery}
					onChange={handleInputChange}
					onFocus={() => setShowDropdown(true)}
				/>
			) : (
				<input
					type="text"
					placeholder="Search..."
					value={searchQuery}
					onChange={handleSearchGroup}
					onFocus={() => setShowDropdown(true)}
				/>
			)}

			<button type="button" onClick={handleSearch}>
				Search
			</button>
			{searchType === "user" &&
				showDropdown &&
				allItems &&
				allItems.length > 0 &&
				searchQuery.length > 0 && (
					<div className="search-results-dropdown">
						{allItems.map((item, index) => (
							<div key={index} className="search-result-item">
								<a
									className="post-user-email"
									href={`/${searchType === "user" ? "profile" : "group"}/${
										item.email || item.groupId
									}`}
								>
									<div className="profile-header">
										{searchType === "user" && (
											<div className="profile-user-picture">
												<img src={item.profilePictureUrl} alt="Profile" />
											</div>
										)}
										<div className="profile-info">
											<div className="profile-name-status">
												<h3>
													{searchType === "user"
														? item.userName
														: item.groupName}
													{searchType === "user" && (
														<span
															className="status-circle"
															style={{
																backgroundColor: getStatusColor(
																	item.onlineStatus
																),
															}}
														></span>
													)}
												</h3>
											</div>
											<p>
												{searchType === "user"
													? item.email
													: item.groupDescription}
											</p>
										</div>
									</div>
								</a>
							</div>
						))}
					</div>
				)}
		</div>
	);
};

export default SearchBar;
