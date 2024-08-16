import axios from "axios";
const URL = process.env.REACT_APP_API_URL;


export const getNotifications = (email) => {
    return axios
        .get(`${URL}/api/notification/get/${email}`)
        .then(response => response.data)
        .catch(error => {
            console.error("There was an error fetching notifications!", error);
            throw error;
        });
};


export const deleteNotification = (notificationId) => {
    return axios
        .delete(`${URL}/api/notification/delete/${notificationId}`)
        .then(response => response.data)
        .catch(error => {
            console.error("There was an error deleting the notification!", error);
            throw error;
        });
};



