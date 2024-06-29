import React, { useState, useEffect } from "react";
import NavBar from "../components/NavBar";
import Header from "../components/Header";
import "../assets/styles/FriendListPage.css"; // New CSS file
import FriendComponent from "../components/FriendComponent";
import { UserProvider, useUser } from "../context/UserContext";
import axios from "axios";

const FriendListPage = () => {
	const { user } = useUser();
	const [friends, setFriends] = useState(user.allFriends); // Assuming you have a friendList in user context

	const handleDeleteFriend = (id) => {
		setFriends(friends.filter((friend) => friend.id !== id));
	};
	useEffect(() => {
		const fetchFriends = async () => {
			try {
				const response = await axios.get(
					`http://localhost:8085/friendships/DTO/${user.email}`
				);

				setFriends(response.data);
			} catch (error) {
				console.error("Failed to fetch friends", error);
			}
		};

		if (user && user.email) {
			fetchFriends();
		}
	}, [user]);

	return (
		<div className="friend-list-page">
			<Header />
			<div className="content">
				<div className="friend-list-nav">
					<NavBar />
				</div>
				<div className="friend-list-content">
					{friends.length > 0 ? (
						friends.map((friend) => (
							<FriendComponent
								key={friend.id}
								friend={friend}
								onDelete={handleDeleteFriend}
							/>
						))
					) : (
						<div className="no-friends">
							<p>You have no friends.</p>
						</div>
					)}
				</div>
			</div>
		</div>
	);
};

export default FriendListPage;
