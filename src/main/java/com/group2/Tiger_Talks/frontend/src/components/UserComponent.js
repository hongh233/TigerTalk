import React from "react";
import axios from "axios";
import "../assets/styles/FriendComponent.css";

const UserComponent = ({ user, userEmail }) => {
	console.log(user);
	const handleDelete = async () => {
		try {
			const response = await axios.delete(
				`http://localhost:8085/friendships/deleteByEmail/${userEmail}/${user.email}`
			);
			if (response.status === 200) {
				window.alert("Friend deleted successfully!");
				window.location.reload();
			}
		} catch (error) {
			window.alert("Failed to delete friend. Please try again.");
			console.error(error);
		}
	};

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
				<button className="delete-button" onClick={handleDelete}>
					Delete
				</button>
			</div>
		</div>
	);
};

export default UserComponent;
