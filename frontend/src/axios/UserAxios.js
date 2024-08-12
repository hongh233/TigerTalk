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

export const getAllUsers = () => {
	return axios
		.get(`${URL}/api/user/getAllProfiles`)
		.then((response) => response.data)
		.catch((error) => {
			console.error("Error fetching users", error);
			throw error;
		});
};

export const updateUser = (updatedUser) => {
	return axios
		.put(`${URL}/api/user/update`, updatedUser)
		.then((response) => response.data)
		.catch((error) => {
			console.error("Error updating users", error);
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

export const getGuestUser = (email) => {
	return axios
		.get(`${URL}/api/user/getByEmail/${email}`)
		.then((response) => response.data)
		.catch((error) => {
			console.error("Error fetching guest user", error);
			throw error;
		});
};
