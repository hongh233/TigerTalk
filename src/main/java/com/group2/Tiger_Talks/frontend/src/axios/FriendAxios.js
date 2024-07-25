import axios from "axios";

const URL = process.env.REACT_APP_API_URL;

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
            `http://localhost:8085/friendships/deleteByEmail/${senderEmail}/${receiverEmail}`
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
        const response = await axios.get(`${URL}/friendships/message/getAll/${friendshipId}`);
        return response.data;
    } catch (error) {
        throw error;
    }
};

export const createMessage = async (message) => {
    try {
        return await axios.post(`${URL}/friendships/message/create`, message);
    } catch (error) {
        throw error;
    }
};