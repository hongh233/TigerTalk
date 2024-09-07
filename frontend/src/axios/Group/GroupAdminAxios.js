import axios from "axios";
const URL = process.env.REACT_APP_API_URL;


export const handleAddUsersToGroupByAdmin = (emails, groupId) => {
    return axios
        .post(`${URL}/api/groups/admin/addUsers/${groupId}`, emails)
        .then((response) => response.data)
        .catch((error) => {
            console.error("Error getting group membership id");
            throw error;
        });
};





