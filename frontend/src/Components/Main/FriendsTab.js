import React, { useState } from "react";
// Icon:
import { HiUserGroup } from "react-icons/hi";
import { FaUserFriends, FaUserPlus } from "react-icons/fa";
import {MdMessage} from "react-icons/md";
// CSS:
import "../../assets/styles/Components/Friend/FriendsTab.css";


const FriendsTab = () => {
	const [showGroupTab, setShowGroupTab] = useState(false);

	const toggleGroupTab = () => {
		setShowGroupTab(!showGroupTab);
	};

	return (
		<div className="friend-tab">
			<button onClick={toggleGroupTab} className="friend-tab-button">
				<FaUserFriends /> <span className="text-hide">Friends</span>
			</button>
			<div className={`friend-tab-dropdown ${showGroupTab ? "show" : ""}`}>
				<a href="/friends/friend-request-list" className="friend-tab-item">
					<FaUserPlus />
					<span className="text-hide">Friend request list</span>
				</a>
				<a href="/friends/friend-list" className="friend-tab-item">
					<HiUserGroup />
					<span className="text-hide"> View Friends</span>
				</a>
				<a href="/friends/message" className="friend-tab-item">
					<MdMessage />
					<span className="text-hide"> Message</span>
				</a>
			</div>
		</div>
	);
};

export default FriendsTab;
