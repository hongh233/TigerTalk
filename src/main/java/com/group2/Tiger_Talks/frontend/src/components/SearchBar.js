import React, { useState } from "react";
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
			console.log(query);
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
				<input
					type="text"
					placeholder="Search..."
					value={searchQuery}
					onChange={handleInputChange}
					onFocus={() => setShowDropdown(true)}
				/>
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
