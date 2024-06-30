import React from "react";
import axios from "axios";
import "../assets/styles/FriendRequestComponent.css";

const FriendRequestComponent = ({ request }) => {
	const handleAccept = async () => {
		try {
			const response = await axios.post(
				`http://localhost:8085/friendshipRequests/accept?id=${request.id}`
			);
			if (response.status === 200) {
				window.alert("Friend request accepted!");
				window.location.reload();
			}
		} catch (error) {
			window.alert("Failed to accept friend request. Please try again.");
			console.error(error);
		}
	};

	const handleReject = async () => {
		try {
			const response = await axios.post(
				`http://localhost:8085/friendshipRequests/reject?id=${request.id}`
			);
			if (response.status === 200) {
				window.alert("Friend request rejected!");
				window.location.reload();
			}
		} catch (error) {
			window.alert("Failed to reject friend request. Please try again.");
			console.error(error);
		}
	};

	return (
		<div className="friend-request">
			<div className="friend-request-header">
				<div className="friend-picture">
					<img src={request.senderProfilePictureUrl} alt="avatar" />
				</div>
				<div className="friend-details">
					<a href={"/profile/" + request.senderEmail}>{request.senderName}</a>
				</div>
			</div>
			<div className="friend-request-actions">
				<button className="accept-button" onClick={handleAccept}>
					Accept
				</button>
				<button className="reject-button" onClick={handleReject}>
					Reject
				</button>
			</div>
		</div>
	);
};

export default FriendRequestComponent;
