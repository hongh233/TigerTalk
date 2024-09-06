import axios from "axios";
const URL = process.env.REACT_APP_API_URL;


// current strategy: search by user email and username
export const searchUsers = (content) => {
    return axios
        .get(`${URL}/api/search/users/${content}`)
        .then((response) => response.data)
        .catch((error) => {
            console.error("Error fetching users", error);
            throw error;
        });
};


// current strategy: search by group name
export const searchGroups = (content) => {
    return axios
        .get(`${URL}/api/search/group/${content}`)
        .then((response) => response.data)
        .catch((error) => {
            console.error("Error searching for groups");
            throw error;
        });
};