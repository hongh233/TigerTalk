import "../../assets/styles/Pages/Friend/FriendListPage.css";
import { deleteFriendshipByEmail } from "../../axios/Friend/FriendshipAxios";
import {getAllFriendsDTO} from "../../axios/Friend/FriendshipAxios";
import {FaUserPlus} from "react-icons/fa";
import React, { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import Header from "../../Components/Main/Header";
import FriendshipMembership from "../../Components/Friend/FriendshipMembership";
import { filterUsers } from "../../utils/filterFunctions.js";


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

	const handleInputChange = (e) => {
		setSearchFriendQuery(e.target.value);
	};

	return (
		<div className="main-page">
			<Header />
			<div className="content">
				<div className="friend-list-content">

					<div className="friend-list-search-bar">
						<input
							type="text"
							placeholder="Search Available Friends..."
							value={searchFriendQuery}
							onChange={handleInputChange}
						/>
					</div>

					<a href="/friends/friend-request-list" className="friend-tab-item">
						<FaUserPlus />
						<span className="text-hide">Friend request list</span>
					</a>

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
						<div className="no-friends"><p>There is no friend available.</p></div>
					)}
				</div>
			</div>
		</div>
	);
};

export default FriendListPage;
