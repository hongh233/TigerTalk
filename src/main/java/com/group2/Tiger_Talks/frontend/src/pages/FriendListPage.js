import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import NavBar from "../components/NavBar";
import Header from "../components/Header";
import "../assets/styles/FriendListPage.css"; // New CSS file
import FriendComponent from "../components/FriendComponent";
import axios from "axios";

const FriendListPage = () => {
	const dispatch = useDispatch();
	const user = useSelector((state) => state.user.user);
	const [friends, setFriends] = useState(user.friends);

	const handleDeleteFriend = (id) => {
		setFriends(friends.filter((friend) => friend.id !== id));
	};
	useEffect(() => {
		const fetchFriends = async () => {
			if (user && user.email) {
				try {
					const response = await axios.get(
						`http://localhost:8085/friendships/DTO/${user.email}`
					);
					setFriends(response.data);
					// dispatch({ type: "SET_FRIEND", payload: response.data });
				} catch (error) {
					console.error("Failed to fetch friend requests", error);
				}
			}
		};

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
					{friends.length > 0 ? (
						friends.map((friend) => (
							<FriendComponent
								key={friend.email}
								friend={friend}
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
