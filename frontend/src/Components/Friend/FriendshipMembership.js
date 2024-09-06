import React from "react";
import "../../assets/styles/Components/Friend/FriendshipMembership.css";
import {formatDate} from "../../utils/formatDate";
import StatusIcon from "../Main/StatusIcon";


const FriendshipMembership = ({ user, handleDeleteFn  }) => {


	return (
		<div className="friendship-membership">
			<div className="friendship-membership-friend-header">
				<div className="common-profile-picture-and-status-icon">
					<img src={user.profilePictureUrl} alt="avatar" />
					<StatusIcon status={user.onlineStatus}/>
				</div>
				<div className="friendship-membership-friend-details">
					<a href={"/profile/" + user.email}>{user.userName}</a>
					<p>Email: {user.email}</p>
					<p>Add Time: {formatDate(user.createTime)}</p>
				</div>
			</div>
			<div className="friend-actions">
				<button className="delete-button" onClick={handleDeleteFn}>Delete</button>
			</div>
		</div>
	);
};

export default FriendshipMembership;
