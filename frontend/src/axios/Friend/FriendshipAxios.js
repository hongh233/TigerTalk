import axios from "axios";
const URL = process.env.REACT_APP_API_URL;


// This axio is used in FriendListPage, FriendMessagePage
export const getAllFriendsDTO = async (email) => {
    const response = await axios.get(`${URL}/friendships/DTO/${email}`);
    return response.data;
};


// This axio is used in FriendListPage
export const deleteFriendshipByEmail = async (senderEmail, receiverEmail) => {
    try {
        const response = await axios.delete(
            `${URL}/friendships/deleteByEmail/${senderEmail}/${receiverEmail}`
        );
        return response.status === 200;
    } catch (error) {
        console.error("Failed to delete friend. Please try again.");
    }
};


export const areFriends = async (senderEmail, receiverEmail) => {
    try {
        const response = await axios.get(
            `${URL}/friendships/areFriends/${senderEmail}/${receiverEmail}`
        );
        return response.data;
    } catch (error) {
        throw error;
    }
};