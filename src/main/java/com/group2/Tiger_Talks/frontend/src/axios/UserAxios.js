import axios from "axios";

const URL = process.env.REACT_APP_API_URL;

export const findUsersByKeyword = (searchQuery, userEmail) => {
    return axios
        .get(`${URL}/api/search/users/${searchQuery}/${userEmail}`)
        .then((response) => response.data)
        .catch((error) => {
            console.error("Error fetching users", error);
            throw error;
        });
};

export const handleDelete = async (senderEmail, receiverEmail) => {
    try {
        const response = await axios.delete(
            `http://localhost:8085/friendships/deleteByEmail/${senderEmail}/${receiverEmail}`
        );
        if (response.status === 200) {
            window.alert("Friend deleted successfully!");
            window.location.reload();
        }
    } catch (error) {
        window.alert("Failed to delete friend. Please try again.");
        console.error(error);
    }
};