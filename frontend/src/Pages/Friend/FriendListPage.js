import React, { useEffect, useState } from "react";
import "../../assets/styles/Pages/Friend/FriendListPage.css";
import { deleteFriendshipByEmail } from "../../axios/Friend/FriendshipAxios";
import {getAllFriendsDTO} from "../../axios/Friend/FriendshipAxios";
import {FaUserPlus} from "react-icons/fa";
import { useSelector } from "react-redux";
import Header from "../../Components/Main/Header";
import FriendshipMembership from "../../Components/Friend/FriendshipMembership";
import { filterUsers } from "../../utils/filterFunctions.js";
import FriendRequestList from "../../Components/Friend/FriendRequestList";
import {getAllFriendRequests} from "../../axios/Friend/FriendshipRequestAxios";
import {Badge} from "@mui/material";


const FriendListPage = () => {
	const user = useSelector((state) => state.user.user);
	const [friends, setFriends] = useState([]);
	const [allFriends, setAllFriends] = useState([]);
	const [searchFriendQuery, setSearchFriendQuery] = useState("");
	const [showFriendRequestList, setShowFriendRequestList] = useState(false);
	const [hoverTimeout, setHoverTimeout] = useState(null);

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

	const handleMouseEnter = () => {
		setHoverTimeout(setTimeout(() => {
			setShowFriendRequestList(true);
		}, 300));
	};

	const handleMouseLeave = () => {
		if (hoverTimeout) {
			clearTimeout(hoverTimeout);
			setHoverTimeout(null);
		}
		setTimeout(() => {
			setShowFriendRequestList(false);
		}, 300);
	};

	const handleToggleClick = () => {
		setShowFriendRequestList((prev) => !prev);
	};


	const [friendRequests, setFriendRequests] = useState([]);
	useEffect(() => {
		const fetchFriendRequests = async () => {
			if (user && user.email) {
				try {
					const responseData = await getAllFriendRequests(user.email);
					setFriendRequests(responseData);
				} catch (error) {
					console.error("Failed to fetch friend requests", error);
				}
			}
		};
		fetchFriendRequests();
	}, [user]);

	return (
		<div className="main-page">
			<Header />
			<div className="content">
				<div className="friend-list-content">

					<div className="friend-request-page-search-and-create">
						<div className="friend-list-search-bar">
							<input type="text" placeholder="Search Available Friends..." value={searchFriendQuery} onChange={handleInputChange}/>
						</div>

						<div className="friend-request-list-and-button"
							 onMouseEnter={handleMouseEnter}
							 onMouseLeave={handleMouseLeave}>

							<Badge badgeContent={friendRequests.length} max={99} color="error">
								<button className="friend-request-page-button" onClick={handleToggleClick}>
									<FaUserPlus />
									<span>&nbsp;Friend Requests</span>
								</button>
							</Badge>

							{showFriendRequestList && (
								<FriendRequestList
									friendRequests={friendRequests}
								/>
							)}
						</div>
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
						<div className="no-friends"><p>There is no friend available.</p></div>
					)}

				</div>
			</div>
		</div>
	);
};

export default FriendListPage;
