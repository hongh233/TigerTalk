import React, { useEffect, useState } from "react";
import axios from "axios";
import NotificationButton from "./NotificationButton";
import "../assets/styles/Header.css";
import { useSelector } from "react-redux";

const Header = () => {
	const user = useSelector((state) => state.user.user);
	const [searchQuery, setSearchQuery] = useState("");
	const [searchResults, setSearchResults] = useState([]);
	const [allProfiles, setAllProfiles] = useState([]);
	const [showDropdown, setShowDropdown] = useState(false);

	useEffect(() => {
		fetchAllProfiles();
	}, []);

	const fetchAllProfiles = async () => {
		try {
			const response = await axios.get(
				"http://localhost:8085/api/user/getAllProfiles"
			);
			setAllProfiles(response.data);
		} catch (error) {
			console.error("Error fetching all profiles:", error);
		}
	};

	const handleInputChange = (e) => {
		setSearchQuery(e.target.value);
	};

	const handleSearch = async () => {
		if (searchQuery.length > 0) {
			await fetchAllProfiles();
		}
		filterProfiles(searchQuery);
	};

	const filterProfiles = (query) => {
		const filteredProfiles = allProfiles.filter(
			(profile) =>
				(profile.firstName.toLowerCase().includes(query.toLowerCase()) ||
					profile.lastName.toLowerCase().includes(query.toLowerCase()) ||
					profile.email.toLowerCase().includes(query.toLowerCase())) &&
				profile.email.toLowerCase() !== user.email
		);
		setSearchResults(filteredProfiles);
	};

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
		<header className="header">
			<h1>
				<a href="/main" className="home-link">
					Tiger Talks
				</a>
			</h1>
			<NotificationButton />
			<div className="search-bar">
				<input
					type="text"
					placeholder="Search..."
					value={searchQuery}
					onChange={handleInputChange}
					onFocus={() => setShowDropdown(true)}
				/>
				<button type="button" onClick={handleSearch}>
					Search
				</button>
			</div>
			{showDropdown && searchResults.length > 0 && searchQuery.length > 0 && (
				<div className="search-results-dropdown">
					{searchResults.map((profileUser, index) => (
						<div key={index} className="search-result-item">
							<a
								className="post-user-email"
								href={`/profile/${profileUser.email}`}
							>
								<div className="profile-header">
									<div className="profile-user-picture">
										<img src={profileUser.profilePictureUrl} alt="Profile" />
									</div>
									<div className="profile-info">
										<div className="profile-name-status">
											<h3>
												{profileUser.userName}
												<span
													className="status-circle"
													style={{
														backgroundColor: getStatusColor(
															profileUser.onlineStatus
														),
													}}
												></span>
											</h3>
										</div>
										<p>{profileUser.email}</p>
									</div>
								</div>
							</a>
						</div>
					))}
				</div>
			)}
		</header>
	);
};

export default Header;
