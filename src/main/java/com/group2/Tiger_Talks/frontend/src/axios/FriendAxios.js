import axios from "axios";

const URL = process.env.REACT_APP_API_URL;

export const addFriend = async (params) => {
	try {
		const response = axios.post(`${URL}/friendshipRequests/send`, null, {
			params,
		});
		return response.data;
	} catch (error) {
		throw error;
	}
};
export const checkFriendship = async (senderEmail, receiverEmail) => {
	try {
		const response = await axios.get(
			`${URL}/friendships/areFriends/${senderEmail}/${receiverEmail}`
		);
		return response.data;
	} catch (error) {
		throw error;
	}
};

export const checkFriendShipRequest = async (email1, email2) => {
	try {
		const response = await axios.get(
			`${URL}/friendshipRequests/areFriendshipRequestExist/${email1}/${email2}`
		);
		return response.data;
	} catch (error) {
		throw error;
	}
};

// This axio is used in FriendListPage, FriendMessagePage
export const getAllFriendsByEmail = async (email) => {
	try {
		const response = await axios.get(`${URL}/friendships/DTO/${email}`);
		return response.data;
	} catch (error) {
		throw error;
	}
};

// This axio is used in FriendListPage
export const handleDelete = async (senderEmail, receiverEmail) => {
	try {
		const response = await axios.delete(
			`${URL}/friendships/deleteByEmail/${senderEmail}/${receiverEmail}`
		);
		if (response.status === 200) {
			window.alert("Friend deleted successfully!");
			window.location.reload();
		}
	} catch (error) {
		window.alert("Failed to delete friend. Please try again.");
		console.error(error);
	}
};

// This axio is used in FriendMessagePage
export const getAllMessagesByFriendshipId = async (friendshipId) => {
	try {
		const response = await axios.get(
			`${URL}/friendships/message/getAll/${friendshipId}`
		);
		return response.data;
	} catch (error) {
		throw error;
	}
};

// This axio is used in FriendMessagePage
export const createMessage = async (message) => {
	try {
		return await axios.post(`${URL}/friendships/message/create`, message);
	} catch (error) {
		throw error;
	}
};

// This axio is used in FriendRequestPage
export const getAllFriendRequests = async (email) => {
	try {
		const response = await axios.get(`${URL}/friendshipRequests/${email}`);
		return response.data;
	} catch (error) {
		throw error;
	}
};
