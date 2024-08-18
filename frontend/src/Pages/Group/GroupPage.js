import React, { useState, useEffect } from "react";
import { useSelector } from "react-redux";
import NavBar from "../../Components/Main/NavBar";
import Header from "../../Components/Main/Header";
import Group from "../../Components/Group/Group";
import SearchBar from "../../Components/Search/SearchBar";
import { filterGroups } from "../../utils/filterFunctions.js";
// Axio:
import { handleGetGroupUserIsMember } from "../../axios/Group/GroupAxios";
// CSS:
import "../../assets/styles/Pages/Group/GroupPage.css";
import {HiPlus} from "react-icons/hi";


const GroupPage = () => {
	const user = useSelector((state) => state.user.user);
	const [createdGroups, setCreatedGroups] = useState([]);
	const [joinedGroups, setJoinedGroups] = useState([]);
	const [filteredCreatedGroups, setFilteredCreatedGroups] = useState([]);
	const [filteredJoinedGroups, setFilteredJoinedGroups] = useState([]);
	const [searchGroupQuery, setSearchGroupQuery] = useState("");

	const [showAllCreatedGroups, setShowAllCreatedGroups] = useState(false);
	const [showAllJoinedGroups, setShowAllJoinedGroups] = useState(false);

	useEffect(() => {
		const fetchGroups = async () => {
			try {
				if (user) {
					const userGroups = await handleGetGroupUserIsMember(user.email);
					const created = userGroups.filter(group => group.groupCreatorEmail === user.email);
					const joined = userGroups.filter(group => group.groupCreatorEmail !== user.email);
					setCreatedGroups(created);
					setJoinedGroups(joined);
					setFilteredCreatedGroups(created);
					setFilteredJoinedGroups(joined);
				}
			} catch (err) {
				console.error(err);
			}
		};
		fetchGroups();
	}, [user]);

	useEffect(() => {
		if (searchGroupQuery) {
			const filteredCreated = filterGroups(createdGroups, searchGroupQuery);
			const filteredJoined = filterGroups(joinedGroups, searchGroupQuery);
			setFilteredCreatedGroups(filteredCreated);
			setFilteredJoinedGroups(filteredJoined);
		} else {
			setFilteredCreatedGroups(createdGroups);
			setFilteredJoinedGroups(joinedGroups);
		}
	}, [searchGroupQuery, createdGroups, joinedGroups]);


	const handleToggleShowCreatedGroups = () => {
		setShowAllCreatedGroups(!showAllCreatedGroups);
	};

	const handleToggleShowJoinedGroups = () => {
		setShowAllJoinedGroups(!showAllJoinedGroups);
	};

	const renderGroupSection = (groups, showAllGroups, toggleShowGroups) => {
		const groupsToShow = showAllGroups ? groups : groups.slice(0, 4);
		return (
			<>
				<div className="group-content">
					{groupsToShow.map((group) => (
						<Group key={group.groupId} group={group} />
					))}
				</div>
				{groups.length > 4 && (
					<p onClick={toggleShowGroups} className="toggle-groups">
						{showAllGroups ? "View Less" : "View More"}
					</p>
				)}
			</>
		);
	};


	return (
		<div className="main-page">
			<Header />
			<div className="content">
				<div className="sidebar">
					<NavBar />
				</div>

				<div className="group-content-container">

					<div className="group-page-header">
						<h2>Search Available Groups:</h2>
					</div>

					<div className="group-page-search-bar">
						<SearchBar searchType="group" setSearchGroupQuery={setSearchGroupQuery} dropdownClassName="group" searchBarClassName="group"/>
					</div>

					<a href="/group/creategroup/" className="create-group-button">
						<HiPlus/> <span className="text-hide">Create a new group</span>
					</a>

					<div className="group-list-container">
						<h2>Created Groups:</h2>
						{renderGroupSection(filteredCreatedGroups, showAllCreatedGroups, handleToggleShowCreatedGroups)}

						<h2>Joined Groups:</h2>
						{renderGroupSection(filteredJoinedGroups, showAllJoinedGroups, handleToggleShowJoinedGroups)}
					</div>

				</div>
			</div>
		</div>
	);
};

export default GroupPage;
