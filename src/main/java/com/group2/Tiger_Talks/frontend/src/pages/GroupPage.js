import React, { useState, useEffect } from "react";
import { useSelector } from "react-redux";
import NavBar from "../components/NavBar";
import Header from "../components/Header";
import "../assets/styles/GroupPage.css";
import Group from "../components/Group";
import SearchBar from "../components/SearchBar";
import {
	handleGetGroupUserIsMember,
} from "../axios/GroupAxios";

const GroupPage = () => {
	const user = useSelector((state) => state.user.user);
	const [groups, setGroups] = useState([]);
	const [searchGroup, setSearchGroup] = useState([]);
	useEffect(() => {
		const fetchGroups = async () => {
			try {
				if (user) {
					const data = await handleGetGroupUserIsMember(user.email);
					setGroups(data);
				}
			} catch (err) {
				console.error(err);
			}
		};
		fetchGroups();
	}, [user]);

	return (
		<div className="group-page">
			<Header />
			<div className="group-page-wrapper">
				<div className="group-nav">
					<NavBar />
				</div>
				<div className="group-content-container">
					<h2>Search available groups:</h2>
					<div className="group-page-search-bar">
						<SearchBar
							searchType="group"
							userEmail={user.email}
							setSearchGroup={setSearchGroup}
						/>
					</div>

					<br />
					<div className="group-content">
						{searchGroup.length > 0 &&
							searchGroup.map((group) => (
								<Group key={group.groupId} group={group} />
							))}
					</div>

					<h2>Groups you are a member of: </h2>
					<div className="group-content">
						{groups.length > 0 ? (
							groups.map((group) => <Group key={group.groupId} group={group} />)
						) : (
							<p>You have not joined any group yet!</p>
						)}
					</div>
				</div>
			</div>
		</div>
	);
};

export default GroupPage;
