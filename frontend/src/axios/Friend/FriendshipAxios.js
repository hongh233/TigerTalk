import axios from "axios";
const URL = process.env.REACT_APP_API_URL;


// This axio is used in FriendListPage, FriendMessagePage
export const getAllFriendsDTO = async (email) => {
    try {
        const response = await axios.get(`${URL}/friendships/DTO/${email}`);
        return response.data;
    } catch (error) {
        throw error;
    }
};


// This axio is used in FriendListPage
export const deleteFriendshipByEmail = async (senderEmail, receiverEmail) => {
    const userConfirmed = window.confirm(`Are you sure you want to delete ${receiverEmail}?`);
    if (userConfirmed) {
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