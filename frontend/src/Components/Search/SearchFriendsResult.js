import React, {useEffect, useState} from "react";
import "../../assets/styles/Components/Search/SearchFriendsResult.css";
import StatusIcon from "../Main/StatusIcon";
import {useNavigate} from "react-router-dom";
import {useDispatch, useSelector} from "react-redux";
import {getAllFriendRequestsDoubleSided, sendFriendRequest} from "../../axios/Friend/FriendshipRequestAxios";
import {FaUserPlus} from "react-icons/fa";
import {MdAccountCircle, MdOutlinePendingActions} from "react-icons/md";
import {BsChatDotsFill} from "react-icons/bs";


const SearchFriendsResult = ({ searchedUsers }) => {

	const user = useSelector((state) => state.user.user);
	const friends = useSelector((state) => state.friends.friends);
	const [friendRequests, setFriendRequests] = useState([]);
	const navigate = useNavigate();
	const [buttonTypeMap, setButtonTypeMap] = useState("");

	useEffect(() => {
		const fetchFriendRequests = async () => {
			setFriendRequests(await getAllFriendRequestsDoubleSided(user.email));
		};
		if (user) {
			fetchFriendRequests();
		}
	}, [user]);

	useEffect(() => {
		const mapButtonTypes = () => {
			const newButtonTypeMap = {};
			searchedUsers.forEach((searchedUser) => {
				if (user.email === searchedUser.email) {
					newButtonTypeMap[searchedUser.email] = "You";
				} else if (friends.some((friend) => friend.email === searchedUser.email)) {
					newButtonTypeMap[searchedUser.email] = "Chat";
				} else if (friendRequests.some((request) => request.senderEmail === searchedUser.email || request.receiverEmail === searchedUser.email)) {
					newButtonTypeMap[searchedUser.email] = "Pending";
				} else {
					newButtonTypeMap[searchedUser.email] = "Add";
				}
			});
			setButtonTypeMap(newButtonTypeMap);
		};
		mapButtonTypes();
	}, [searchedUsers, friends, friendRequests, user]);

	const handleAddFriend = async (searchedUser) => {
		await sendFriendRequest({ senderEmail: user.email, receiverEmail: searchedUser.email });
		setButtonTypeMap((prev) => ({
			...prev,
			[searchedUser.email]: "Pending",
		}));
	};

	const renderButton = (searchedUser) => {
		const buttonType = buttonTypeMap[searchedUser.email];

		switch (buttonType) {
			case "You":
				return (
					<button className="search-friend-action-button"
							id="you"
							onClick={() => navigate(`/profile/${user.email}`)}>
						<MdAccountCircle />&nbsp;&nbsp;You
					</button>
				);
			case "Chat":
				return (
					<button className="search-friend-action-button"
							id="chat"
							onClick={() => navigate("/friends/message", { state: { selectedFriendEmail: searchedUser.email } })}>
						<BsChatDotsFill />&nbsp;&nbsp;Chat
					</button>
				);
			case "Pending":
				return (
					<button className="search-friend-action-button" id="pending">
						<MdOutlinePendingActions />&nbsp;&nbsp;Pending
					</button>
				);
			case "Add":
			default:
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
				<div className="search-result-item" key={searchedUser.email}>
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
						{renderButton(searchedUser)}
					</div>

				</div>
			))}
		</div>
	);
};

export default SearchFriendsResult;
