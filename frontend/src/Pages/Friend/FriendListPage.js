import React, { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import NavBar from "../../Components/Main/NavBar";
import Header from "../../Components/Main/Header";
import "../../assets/styles/Pages/Friend/FriendListPage.css"; // New CSS file
import FriendshipMembership from "../../Components/Friend/FriendshipMembership";
import SearchBar from "../../Components/Search/SearchBar";
import { filterUsers } from "../../utils/filterFunctions.js";
import { deleteFriendshipByEmail } from "../../axios/Friend/FriendshipAxios";
import {getAllFriendsDTO} from "../../axios/Friend/FriendshipAxios";

const FriendListPage = () => {
	const user = useSelector((state) => state.user.user);
	const [friends, setFriends] = useState([]);
	const [allFriends, setAllFriends] = useState([]);
	const [searchFriendQuery, setSearchFriendQuery] = useState("");

	const handleDeleteFriend = (id) => {
		setFriends(friends.filter((friend) => friend.id !== id));
	};

	useEffect(() => {
		const fetchFriends = async () => {
			if (user && user.email) {
				try {
					const responseData = await getAllFriendsDTO(user.email);
					if (responseData.length > 0) {
						setAllFriends(responseData);
						setFriends(responseData);
					}
				} catch (error) {
					console.error("Failed to fetch friends", error);
				}
			}
		};

		fetchFriends();
	}, [user]);

	useEffect(() => {
		if (searchFriendQuery) {
			const filteredFriends = filterUsers(allFriends, searchFriendQuery);
			setFriends(filteredFriends);
		} else {
			setFriends(allFriends);
		}
	}, [searchFriendQuery, allFriends]);

	return (
		<div className="main-page">
			<Header />
			<div className="content">
				<div className="sidebar">
					<NavBar />
				</div>

				<div className="friend-list-content">
					<h3>Friend list:</h3>
					<div className="friend-list-search-bar">
						<SearchBar
							searchType="friend"
							setSearchFriendQuery={setSearchFriendQuery}
							dropdownClassName="friend"
							searchBarClassName="friend"
						/>
					</div>

					{friends.length > 0 ? (
						friends.map((friend) => (
							<FriendshipMembership
								key={friend.email}
								user={friend}
								userEmail={user.email}
								onDelete={handleDeleteFriend}
								handleDeleteFn={() => deleteFriendshipByEmail(user.email, friend.email)}
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
