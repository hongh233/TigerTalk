import axios from "axios";

export const SET_FRIEND_REQUESTS = "SET_FRIEND_REQUESTS";

export const setFriendRequests = (friendRequests) => ({
	type: SET_FRIEND_REQUESTS,
	payload: friendRequests,
});
