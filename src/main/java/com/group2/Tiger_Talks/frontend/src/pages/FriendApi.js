// src/pages/FriendApi.js
import axios from 'axios';

const API_URL = 'http://localhost:8085/friends';

export const sendFriendRequest = (senderEmail, receiverEmail) => {
    return axios.post(`${API_URL}/add`, null, {
        params: {senderEmail, receiverEmail},
    });
};

export const acceptFriendRequest = (friendshipRequestId) => {
    return axios.post(`${API_URL}/accept`, null, {
        params: {friendshipRequestId},
    });
};

export const rejectFriendRequest = (friendshipRequestId) => {
    return axios.post(`${API_URL}/reject`, null, {
        params: {friendshipRequestId},
    });
};

export const getAllFriends = (email) => {
    return axios.get(`${API_URL}/getAll`, {
        params: {email},
    });
};
