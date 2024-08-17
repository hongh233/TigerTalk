import axios from "axios";
const URL = process.env.REACT_APP_API_URL;


export const updateUser = (updatedUser) => {
	return axios
		.put(`${URL}/api/user/update`, updatedUser, {
			headers: {
				"Content-Type": "application/json",
			}
		})
		.then((response) => response.data)
		.catch((error) => {
			console.error("Error updating users", error);
			throw error;
		});
};


export const getAllUsers = () => {
	return axios
		.get(`${URL}/api/user/getAllProfiles`)
		.then((response) => response.data)
		.catch((error) => {
			console.error("Error fetching users", error);
			throw error;
		});
};


export const getCurrentUser = (email) => {
	return axios
		.get(`${URL}/api/user/getByEmail/${email}`)
		.then((response) => response.data)
		.catch((error) => {
			console.error("Error fetching user", error);
			throw error;
		});
};


export const deleteUserProfileByEmail = (email) => {
	return axios
		.delete(`${URL}/api/user/deleteByEmail/${email}`)
		.then(response => response.data)
		.catch(error => {
			console.error(`Error deleting user with email ${email}:`, error);
			throw error;
		});
};