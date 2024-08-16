import React from "react";
import "../../assets/styles/Components/Friend/FriendshipMembership.css";

const FriendshipMembership = ({ user, handleDeleteFn  }) => {


	return (
		<div className="friend">
			<div className="friend-header">
				<div className="friend-picture">
					<img src={user.profilePictureUrl} alt="avatar" />
				</div>
				<div className="friend-details">
					<a href={"/profile/" + user.email}>{user.userName}</a>
					<p>Email: {user.email}</p>
				</div>
			</div>
			<div className="friend-actions">
				<button className="delete-button" onClick={handleDeleteFn}>
					Delete
				</button>
			</div>
		</div>
	);
};

export default FriendshipMembership;
