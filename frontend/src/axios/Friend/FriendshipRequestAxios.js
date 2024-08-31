import axios from "axios";
const URL = process.env.REACT_APP_API_URL;



// This axio is used in FriendRequestPage
export const getAllFriendRequests = async (email) => {
    try {
        const response = await axios.get(`${URL}/friendshipRequests/${email}`);
        return response.data;
    } catch (error) {
        throw error;
    }
};


export const sendFriendRequest = async (params) => {
    try {
        const response = axios.post(`${URL}/friendshipRequests/send`, null, {params});
        return response.data;
    } catch (error) {
        throw error;
    }
};



export const acceptFriendRequest = async (id) => {
    try {
        return await axios.post(`${URL}/friendshipRequests/accept?id=${id}`);
    } catch (error) {
        throw error;
    }
};


export const rejectFriendRequest = async (id) => {
    try {
        return await axios.post(`${URL}/friendshipRequests/reject?id=${id}`);
    } catch (error) {
        throw error;
    }
};




export const areFriendshipRequestExist = async (email1, email2) => {
    try {
        const response = await axios.get(
            `${URL}/friendshipRequests/areFriendshipRequestExist/${email1}/${email2}`
        );
        return response.data;
    } catch (error) {
        throw error;
    }
};



