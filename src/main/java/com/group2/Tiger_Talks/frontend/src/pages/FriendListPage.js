import React, { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import NavBar from "../components/NavBar";
import Header from "../components/Header";
import "../assets/styles/FriendListPage.css"; // New CSS file
import UserComponent from "../components/UserComponent";
import axios from "axios";

const FriendListPage = () => {
	const user = useSelector((state) => state.user.user);
	const [friends, setFriends] = useState(null);

	const handleDeleteFriend = (id) => {
		setFriends(friends.filter((friend) => friend.id !== id));
	};

	const fetchFriends = async () => {
		if (user && user.email) {
			try {
				const response = await axios.get(
					`http://localhost:8085/friendships/DTO/${user.email}`
				);
				if (response.data.length > 0) {
					setFriends(response.data);
				}

				// dispatch({ type: "SET_FRIEND", payload: response.data });
			} catch (error) {
				console.error("Failed to fetch friend requests", error);
			}
		}
	};

	useEffect(() => {
		fetchFriends();
	}, [user]);

	return (
		<div className="friend-list-page">
			<Header />
			<div className="content">
				<div className="friend-list-nav">
					<NavBar />
				</div>
				<div className="friend-list-content">
					{friends ? (
						friends.map((friend) => (
							<UserComponent
								key={friend.email}
								user={friend}
								userEmail={user.email}
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
