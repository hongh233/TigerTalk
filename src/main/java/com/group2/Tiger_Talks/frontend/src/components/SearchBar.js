import React, { useState } from "react";
import { useDispatch } from "react-redux";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import { handleFindGroups } from "./../axios/GroupAxios";
import { findUsersByKeyword } from "./../axios/UserAxios";
import { filterUsersAlreadyInGroup } from "./../utils/filterGroupMembers";
import Dropdown from "./../components/DropDown";
import "../assets/styles/SearchBar.css";

const SearchBar = ({
	searchType,
	userEmail,
	setSearchGroup,
	setSearchFriendQuery,
	setSearchPublicUser,
	setSearchMember,
	dropdownClassName,
	searchBarClassName,
	groupMembers,
}) => {
	const [searchQuery, setSearchQuery] = useState("");
	const [items, setItems] = useState([]);
	const [showDropdown, setShowDropdown] = useState(false);
	const navigate = useNavigate();
	const dispatch = useDispatch();
	const { email } = useSelector((state) => state.user.user);

	const handleInputChange = (e) => {
		const query = e.target.value;
		setSearchQuery(query);
		if (query === "") {
			setShowDropdown(false);
		} else {
			setShowDropdown(true);
			handleSearch(query);
		}
	};

	const handleSearch = async (query) => {
		if (query.length > 0) {
			if (searchType === "user") {
				await fetchUsers(query);
			} else if (searchType === "group") {
				await fetchGroups(query);
			} else if (searchType === "friend") {
				await fetchFriends(query);
			} else if (searchType === "member") {
				await fetchMembers(query);
			}
		}
	};

	const fetchGlobal = async () => {
		if (searchQuery.length > 0) {
			try {
				const responseUsers = await findUsersByKeyword(searchQuery, email);
				const responseGroups = await handleFindGroups(
					searchQuery.toLowerCase(),
					email
				);
				dispatch({ type: "SET_GLOBAL_USERS", payload: responseUsers });
				dispatch({ type: "SET_GLOBAL_GROUPS", payload: responseGroups });
			} catch (error) {
				console.error(`Error fetching users and groups:`, error);
			}
		}
	};

	const fetchUsers = async (query) => {
		try {
			const response = await findUsersByKeyword(query, email);
			setItems(response);
		} catch (error) {
			console.error(`Error fetching users:`, error);
		}
	};
	const fetchMembers = async (query) => {
		try {
			const response = await findUsersByKeyword(query, email);
			const filteredUsers = filterUsersAlreadyInGroup(groupMembers, response);
			setItems(filteredUsers);
		} catch (error) {
			console.error(`Error fetching users:`, error);
		}
	};

	const fetchGroups = async (query) => {
		try {
			const response = await handleFindGroups(query.toLowerCase(), userEmail);
			setSearchGroup(response);
		} catch (error) {
			console.error(`Error fetching groups:`, error);
		}
	};

	const fetchFriends = async (query) => {
		setSearchFriendQuery(query);
	};

	const handleChoose = (email) => {
		if (searchType === "user") {
			navigate(`/profile/${email}`);
		} else if (searchType === "member") {
			setSearchMember(email);
		}
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
		<div className={`search-bar-${searchBarClassName}`}>
			<div className={`search-bar-${searchBarClassName}-input`}>
				<div className="search-input-and-button">
					<input
						type="text"
						placeholder="Search..."
						value={searchQuery}
						onChange={handleInputChange}
						onFocus={() => setShowDropdown(true)}
					/>
					{searchType === "global" ? (
						<div className="header-search-button" onClick={() => fetchGlobal()}>
							Search
						</div>
					) : (
						""
					)}
				</div>
			</div>

			{showDropdown && items && (
				<Dropdown
					handleChoose={handleChoose}
					items={items}
					getStatusColor={getStatusColor}
					dropdownClassName={dropdownClassName}
				/>
			)}
		</div>
	);
};

export default SearchBar;
