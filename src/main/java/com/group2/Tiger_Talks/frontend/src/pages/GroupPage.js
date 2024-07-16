import React, { useState, useEffect } from "react";
import NavBar from "../components/NavBar";
import Header from "../components/Header";
import "../assets/styles/GroupPage.css";
import Group from "../components/Group";
import { handleGetAllGroups } from "./../axios/GroupAxios";

const GroupPage = () => {
	const [groups, setGroups] = useState([]);
	useEffect(() => {
		fetchGroups();
	}, []);
	const fetchGroups = async () => {
		const data = await handleGetAllGroups();
		setGroups(data);
	};
	return (
		<div className="group-page">
			<Header />
			<div className="content">
				<div className="group-nav">
					<NavBar />
				</div>
				<div className="group-content">
					{groups &&
						groups.map((group) => <Group key={group.groupId} group={group} />)}
				</div>
			</div>
		</div>
	);
};

export default GroupPage;
