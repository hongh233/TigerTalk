import axios from "axios";
const URL = process.env.REACT_APP_API_URL;


// This axio is used in FriendMessagePage
export const createMessage = async (message) => {
    try {
        return await axios.post(`${URL}/friendships/message/create`, message);
    } catch (error) {
        throw error;
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

export const markMessageAsRead = async (messageId) => {
    try {
        return await axios.patch(`${URL}/friendships/message/setRead/${messageId}`);
    } catch (error) {
        throw error;
    }
}

