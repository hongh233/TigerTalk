import React, { useEffect, useState } from "react";
import "../../assets/styles/Pages/Friend/FriendPage.css";
import { deleteFriendshipByEmail } from "../../axios/Friend/FriendshipAxios";
import {FaUserPlus} from "react-icons/fa";
import {useDispatch, useSelector} from "react-redux";
import FriendshipMembership from "../../Components/Friend/FriendshipMembership";
import { filterUsers } from "../../utils/filterFunctions.js";
import FriendRequestList from "../../Components/Friend/FriendRequestList";
import {Badge} from "@mui/material";
import {removeFriend} from "../../redux/actions/friendActions";


const FriendPage = () => {
	const user = useSelector((state) => state.user.user);
	const friends = useSelector((state) => state.friends.friends);
	const friendRequests = useSelector((state) => state.friends.friendshipRequests);
	const [filteredFriends, setFilteredFriends] = useState(friends);
	const [searchFriendQuery, setSearchFriendQuery] = useState("");
	const [showFriendRequestList, setShowFriendRequestList] = useState(false);
	const [hoverTimeout, setHoverTimeout] = useState(null);
	const dispatch = useDispatch();

	useEffect(() => {
		if (searchFriendQuery) {
			const result = filterUsers(friends, searchFriendQuery);
			setFilteredFriends(result);
		} else {
			setFilteredFriends(friends);
		}
	}, [searchFriendQuery, friends]);

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

	const handleFriendDelete = async (friendEmail, friendId) => {
		const userConfirmed = window.confirm(`Are you sure you want to delete ${friendEmail}?`);
		if (userConfirmed) {
			const isDeleted = await deleteFriendshipByEmail(user.email, friendEmail);
			if (isDeleted) {
				window.alert("Friend deleted successfully!");
				dispatch(removeFriend(friendId));
			} else {
				window.alert("Failed to delete friend. Please try again.");
			}
		}
	};

	return (
		<div className="main-page">
			<div className="content">
				<div className="friend-list-content">

					<div className="friend-request-page-search-and-create">
						<div className="friend-list-search-bar">
							<input
								type="text"
								placeholder="Find Available Friends..."
								value={searchFriendQuery}
								onChange={handleInputChange}
							/>
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

					{filteredFriends.length > 0 ? (
						filteredFriends.map((friend) => (
							<FriendshipMembership
								key={friend.email}
								user={friend}
								userEmail={user.email}
								handleDeleteFn={() => handleFriendDelete(friend.email, friend.id)}
							/>
						))
					) : (
						<div className="no-friends">
							<p>There is no friend available.</p>
						</div>
					)}
				</div>
			</div>
		</div>
	);
};

export default FriendPage;
