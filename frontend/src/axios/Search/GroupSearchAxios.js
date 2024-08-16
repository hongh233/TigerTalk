import axios from "axios";
const URL = process.env.REACT_APP_API_URL;




export const handleFindGroups = (groupName, userEmail) => {
    return axios
        .get(`${URL}/api/search/group/${groupName}/${userEmail}`)
        .then((response) => response.data)
        .catch((error) => {
            console.error("Error searching for groups");
            throw error;
        });
};