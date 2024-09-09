import React from "react";
import "../../assets/styles/Components/Search/SearchFriendsResult.css";
import StatusIcon from "../Main/StatusIcon";
import {useNavigate} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import {sendFriendRequest} from "../../axios/Friend/FriendshipRequestAxios";
import {addFriendshipRequest} from "../../redux/actions/friendActions";
import {FaUserPlus} from "react-icons/fa";
import {MdAccountCircle, MdOutlinePendingActions} from "react-icons/md";
import {BsChatDotsFill} from "react-icons/bs";


const SearchFriendsResult = ({ searchedUsers }) => {

	const user = useSelector((state) => state.user.user);
	const friends = useSelector((state) => state.friends.friends);
	const friendRequests = useSelector((state) => state.friends.friendshipRequests);
	const navigate = useNavigate();
	const dispatch = useDispatch();

	const isFriend = (email) => {
		return friends.some(friend => friend.email === email);
	};

	const isPending = (email) => {
		return friendRequests.some(request => request.senderEmail === email || request.receiverEmail === email);
	};

	const handleAddFriend = async (searchedUser) => {
		const params = {
			senderEmail: user.email,
			receiverEmail: searchedUser.email,
		};
		try {
			const response = await sendFriendRequest(params);
			dispatch(addFriendshipRequest(response));
		} catch (error) {
			console.log("Error sending friend request:", error);
		}
	};

	const renderFriendActionButton = (searchedUser) => {
		if (user.email === searchedUser.email) {
			return (
				<button className="search-friend-action-button"
						id="you"
						onClick={(email) => {navigate(`/profile/${user.email}`)}}
				>
					<MdAccountCircle />&nbsp;&nbsp;You
				</button>
			);
		} else if (isFriend(searchedUser.email)) {
			return (
				<button className="search-friend-action-button"
						id="chat"
						onClick={() => navigate("/friends/message")}>
					<BsChatDotsFill />&nbsp;&nbsp;Chat
				</button>
			);
		} else if (isPending(searchedUser.email)) {
			return (
				<button className="search-friend-action-button"
						id="pending">
					<MdOutlinePendingActions />&nbsp;&nbsp;Pending
				</button>
			);
		} else {
			return (
				<button className="search-friend-action-button"
						id="add"
						onClick={() => handleAddFriend(searchedUser)}>
					<FaUserPlus />&nbsp;&nbsp;Add
				</button>
			);
		}
	};

	return (
		<div className="search-results-dropdown-header">
			{searchedUsers.map((searchedUser) => (
				<div className="search-result-item"
					 key={searchedUser.email}
				>

					<div className="search-friend-profile-header">

						<div className="common-profile-picture-and-status-icon"
							 onClick={(email) => {navigate(`/profile/${searchedUser.email}`)}}>
							<img src={searchedUser.profilePictureUrl} alt="Profile" />
							<StatusIcon status={searchedUser.onlineStatus} />
						</div>

						<div className="search-friend-profile-info">
							<div className="search-friend-profile-name-status">
								<div id="userName"
									 onClick={(email) => {navigate(`/profile/${searchedUser.email}`)}}
								>{searchedUser.userName}</div>
								<div id="email">{searchedUser.email}</div>
							</div>
						</div>

					</div>

					<div className="search-friend-action-container">
						{renderFriendActionButton(searchedUser)}
					</div>

				</div>
			))}
		</div>
	);
};

export default SearchFriendsResult;
