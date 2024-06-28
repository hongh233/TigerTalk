import React from "react";
import NavBar from "../components/NavBar";
import Header from "../components/Header";
import "../assets/styles/FriendRequestPage.css"; // Update to the new CSS file
import FriendRequestComponent from "../components/FriendRequestComponent";
import { UserProvider, useUser } from "../context/UserContext";

const FriendRequestPage = () => {
	const { user } = useUser();
	const friendRequests = user.receiverFriendshipRequestList;

	return (
		<div className="friend-request-list-page">
			<Header />
			<div className="content">
				<div className="friend-request-list-nav">
					<NavBar />
				</div>
				<div className="friend-request-list-content">
					{friendRequests.length > 0 ? (
						friendRequests.map((request) => (
							<FriendRequestComponent
								key={request.friendshipRequestId}
								request={request}
							/>
						))
					) : (
						<div className="no-friend-requests">
							<p>You have no friend requests</p>
						</div>
					)}
				</div>
			</div>
		</div>
	);
};

export default FriendRequestPage;
