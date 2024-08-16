import axios from "axios";
const URL = process.env.REACT_APP_API_URL;


export const handleAddUserToGroupByAdmin = (email, groupId) => {
    return axios
        .post(`${URL}/api/groups/admin/addUser/${email}/${groupId}`)
        .then((response) => response.data)
        .catch((error) => {
            console.error("Error getting group membership id");
            throw error;
        });
};





