import React, { useState, useEffect } from "react";
import { useSelector } from "react-redux";
import NavBar from "../../components/NavBar";
import Header from "../../components/Header";
import "../../assets/styles/GroupPage.css";
import Group from "../../components/Group";
import SearchBar from "../../components/SearchBar";
import { filterGroups } from "./../../utils/filterGroups";
import { ImFilter } from "react-icons/im";
import {
	handleGetGroupUserIsMember,
	handleGetAllGroups,
} from "../../axios/GroupAxios";

const GroupPage = () => {
	const user = useSelector((state) => state.user.user);
	const [myGroups, setMyGroups] = useState([]);
	const [allGroups, setAllGroups] = useState([]);
	const [filteredGroups, setFilteredGroups] = useState([]);
	const [searchGroupQuery, setSearchGroupQuery] = useState("");
	const [isNavVisible, setIsNavVisible] = useState(false);
	const [filterOption, setFilterOption] = useState("all");
	const [showFilterDropdown, setShowFilterDropdown] = useState(false);
	const [isSearchFocused, setIsSearchFocused] = useState(false);

	useEffect(() => {
		const fetchGroups = async () => {
			try {
				if (user) {
					const userGroups = await handleGetGroupUserIsMember(user.email);
					setMyGroups(userGroups);

					const allGroupsData = await handleGetAllGroups();
					setAllGroups(allGroupsData);
					setFilteredGroups(allGroupsData);
				}
			} catch (err) {
				console.error(err);
			}
		};
		fetchGroups();
	}, [user]);

	useEffect(() => {
		let filtered = allGroups;

		if (isSearchFocused) {
			filtered = allGroups;
		} else {
			if (filterOption === "myGroups") {
				filtered = myGroups;
			} else if (filterOption === "public") {
				filtered = allGroups.filter((group) => !group.isPrivate);
			}
		}

		if (searchGroupQuery) {
			filtered = filterGroups(allGroups, searchGroupQuery);
		}

		setFilteredGroups(filtered);
	}, [searchGroupQuery, allGroups, myGroups, filterOption, isSearchFocused]);

	const handleFilterChange = (option) => {
		setFilterOption(option);

		setSearchGroupQuery(""); // Clear the search query when filter changes
	};

	const getFilterHeader = () => {
		switch (filterOption) {
			case "myGroups":
				return "Groups you are a member of:";
			case "public":
				return "Public Groups:";

			default:
				return "All Groups:";
		}
	};

	return (
		<div className="group-page">
			<Header />
			<div
				className="menu-toggle"
				onClick={() => setIsNavVisible(!isNavVisible)}
			>
				<div></div>
				<div></div>
				<div></div>
			</div>

			<div className={`content ${isNavVisible ? "nav-visible" : ""}`}>
				<div className={`sidebar ${isNavVisible ? "visible" : ""}`}>
					<button className="close-btn" onClick={() => setIsNavVisible(false)}>
						×
					</button>
					<NavBar />
				</div>
				<div className="group-content-container">
					<div className="group-page-header">
						<h2>Search available groups:</h2>
					</div>
					<div className="group-page-search-bar">
						<SearchBar
							searchType="group"
							setSearchGroupQuery={setSearchGroupQuery}
							dropdownClassName="group"
							searchBarClassName="group"
							onFocus={() => setIsSearchFocused(true)}
							onBlur={() => setIsSearchFocused(false)}
						/>
					</div>

					<br />
					<div className="group-member-section">
						<div
							className="filter-icon"
							onClick={() => setShowFilterDropdown(!showFilterDropdown)}
						>
							<ImFilter />
						</div>
						{showFilterDropdown && (
							<div className="filter-dropdown">
								<button
									className="group-filter-button"
									onClick={() => handleFilterChange("myGroups")}
								>
									Groups I'm a Member Of
								</button>
								<button
									className="group-filter-button"
									onClick={() => handleFilterChange("public")}
								>
									Public Groups
								</button>
							</div>
						)}
					</div>

					<h2>{getFilterHeader()}</h2>
					<div className="group-content">
						{filteredGroups.length > 0 ? (
							filteredGroups.map((group) => (
								<Group key={group.groupId} group={group} />
							))
						) : (
							<p>No groups found.</p>
						)}
					</div>
				</div>
			</div>
		</div>
	);
};

export default GroupPage;
