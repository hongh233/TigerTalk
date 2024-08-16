import axios from "axios";

const URL = process.env.REACT_APP_API_URL;

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

export const fetchUserByEmail = async (userEmail, dispatch) => {
	try {
		const response = await axios.get(`${URL}/api/user/getByEmail/${userEmail}`);
		return response.data;
	} catch (error) {
		console.error("Error fetching profile user data:", error);
	}
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
