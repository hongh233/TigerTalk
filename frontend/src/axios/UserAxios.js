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


export const updateUserProfileSetCommonInfo = (email, firstName, lastName, userName, biography, birthday, gender) => {
	return axios
		.put(`${URL}/api/user/update/commonInfo`, null, {
			params: {email, firstName, lastName, userName, biography, birthday, gender},
		})
		.then(response => response.data)
		.catch(error => {
			console.error("Error updating user common info", error);
			throw error;
		});
};
export const updateUserProfileSetProfilePicture = (email, profilePictureUrl) => {
	return axios
		.put(`${URL}/api/user/update/profilePicture`, null, {
			params: {
				email, profilePictureUrl},
		})
		.then(response => response.data)
		.catch(error => {
			console.error("Error updating user profile picture", error);
			throw error;
		});
};
export const updateUserProfileSetOnlineStatus = (email, onlineStatus) => {
	return axios
		.put(`${URL}/api/user/update/onlineStatus`, null, {
			params: {
				email, onlineStatus},
		})
		.then(response => response.data)
		.catch(error => {
			console.error("Error updating user online status", error);
			throw error;
		});
};





export const updateUserProfileSetRole = (email, role) => {
	return axios
		.put(`${URL}/api/user/update/role`, null, {
			params: {email, role},
		})
		.then(response => response.data)
		.catch(error => {
			console.error("Error updating user role", error);
			throw error;
		});
};
export const updateUserProfileSetValidated = (email, validated) => {
	return axios
		.put(`${URL}/api/user/update/validated`, null, {
			params: {email, validated},
		})
		.then(response => response.data)
		.catch(error => {
			console.error("Error updating user validated status", error);
			throw error;
		});
};
export const updateUserProfileSetUserLevel = (email, userLevel) => {
	return axios
		.put(`${URL}/api/user/update/userLevel`, null, {
			params: {email, userLevel},
		})
		.then(response => response.data)
		.catch(error => {
			console.error("Error updating user level", error);
			throw error;
		});
};








