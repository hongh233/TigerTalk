import React, { useState } from "react";
import { useDispatch } from "react-redux";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";
import { handleGetAllGroups } from "../../axios/Group/GroupAxios";
import { findUsersByKeyword, getAllUsers } from "../../axios/UserAxios";

import Dropdown from "./DropDown";
import { IoSearch } from "react-icons/io5";
import "../../assets/styles/Components/Search/SearchBar.css";
import {
	filterGroups,
	filterUsersAlreadyInGroup,
	filterUsers,
} from "../../utils/filterFunctions.js";

const SearchBar = ({
	searchType,
	userEmail,
	setSearchGroupQuery,
	setSearchFriendQuery,
	setSearchMember,
	dropdownClassName,
	searchBarClassName,
	groupMembers,
	onFocus,
	onBlur,
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
		}
		handleSearch(query);
	};

	const handleSearch = async (query) => {
		if (query.length > 0) {
			if (searchType === "user") {
				await fetchUsers(query);
			} else if (searchType === "member") {
				await fetchMembers(query);
			}
		}
		fetchGroups(query);
		fetchFriends(query);
	};

	const fetchGlobal = async () => {
		const responseGroups = await handleGetAllGroups();
		const responseUsers = await getAllUsers();
		if (searchQuery.length > 0) {
			try {
				const filteredGroups = filterGroups(responseGroups, searchQuery);
				const filteredUsers = filterUsers(responseUsers, searchQuery);
				dispatch({ type: "SET_GLOBAL_USERS", payload: filteredUsers });
				dispatch({ type: "SET_GLOBAL_GROUPS", payload: filteredGroups });
			} catch (error) {
				console.error(`Error fetching users and groups:`, error);
			}
		} else {
			dispatch({ type: "SET_GLOBAL_USERS", payload: responseUsers });
			dispatch({ type: "SET_GLOBAL_GROUPS", payload: responseGroups });
		}
		navigate("/search");
	};

	const fetchUsers = async (query) => {
		try {
			const response = await findUsersByKeyword(query, email);
			setItems(response);
			if (setSearchFriendQuery) {
				setSearchFriendQuery(query);
			}
		} catch (error) {
			console.error(`Error fetching users:`, error);
		}
	};

	const fetchMembers = async (query) => {
		try {
			const response = await findUsersByKeyword(query, email);
			const filteredUsers = filterUsersAlreadyInGroup(groupMembers, response);
			setItems(filteredUsers);
			if (setSearchFriendQuery) {
				setSearchFriendQuery(query);
			}
		} catch (error) {
			console.error(`Error fetching users:`, error);
		}
	};

	const fetchGroups = (query) => {
		if (setSearchGroupQuery) {
			setSearchGroupQuery(query);
		}
	};

	const fetchFriends = (query) => {
		if (setSearchFriendQuery) {
			setSearchFriendQuery(query);
		}
	};

	const handleChoose = (email) => {
		if (searchType === "user" || searchType === "global") {
			navigate(`/profile/${email}`);
		} else if (searchType === "member") {
			setSearchMember(email);
		}
	};

	const handleKeyDown = (e) => {
		if (e.key === "Enter" && searchType === "global") {
			fetchGlobal();
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
						onFocus={onFocus}
						onBlur={onBlur}
						onKeyDown={handleKeyDown}
					/>
					{searchType === "global" && (
						<div className="header-search-button" onClick={fetchGlobal}>
							<IoSearch />
						</div>
					)}
				</div>
			</div>

			{showDropdown && items && (
				<Dropdown
					handleChoose={handleChoose}
					items={items}
					dropdownClassName={dropdownClassName}
				/>
			)}
		</div>
	);
};

export default SearchBar;
