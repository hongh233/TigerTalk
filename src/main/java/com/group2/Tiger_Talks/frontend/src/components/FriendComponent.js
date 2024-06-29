import React from "react";
import axios from "axios";
import "../assets/styles/FriendComponent.css";
import emitter from "./../context/EventEmitter"; // Import the event emitter
const FriendComponent = ({ friend }) => {
	const handleDelete = async () => {
		try {
			const response = await axios.delete(
				`http://localhost:8085/friends/delete?id=${friend.id}`
			);
			if (response.status === 200) {
				window.alert("Friend deleted successfully!");
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
					<img src={friend.profilePictureUrl} alt="avatar" />
				</div>
				<div className="friend-details">
					<a href={"/profile/" + friend.email}>{friend.userName}</a>
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

export default FriendComponent;
