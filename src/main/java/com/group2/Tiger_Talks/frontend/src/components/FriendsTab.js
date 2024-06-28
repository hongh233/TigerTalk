import React, { useState } from "react";
import { HiAcademicCap, HiPlus, HiUserGroup } from "react-icons/hi";
import "../assets/styles/FriendsTab.css";

const FriendsTab = () => {
	const [showGroupTab, setShowGroupTab] = useState(false);

	const toggleGroupTab = () => {
		setShowGroupTab(!showGroupTab);
	};

	return (
		<div className="group-tab">
			<button onClick={toggleGroupTab} className="group-tab-button">
				<HiAcademicCap /> <span className="text-hide">Friends</span>
			</button>
			<div className={`group-tab-dropdown ${showGroupTab ? "show" : ""}`}>
				<a href="/friends/friend-request-list" className="group-tab-item">
					<HiPlus /> <span className="text-hide">Friend request list</span>
				</a>
				<a href="/friends/friend-list" className="group-tab-item">
					<HiUserGroup />
					<span className="text-hide"> View Friends</span>
				</a>
			</div>
		</div>
	);
};

export default FriendsTab;
