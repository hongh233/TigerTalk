import axios from "axios";
const URL = process.env.REACT_APP_API_URL;

export const handleCreateGroup = (form) => {
	return axios
		.post(`${URL}/api/groups/create`, null, {
			params: {
				groupName: form.groupName,
				creatorEmail: form.userEmail,
				isPrivate: form.status === "private",
			},
		})
		.then((response) => response.data)
		.catch((error) => {
			console.error("Error creating group");
			throw error;
		});
};

export const handleGetAllGroups = () => {
	return axios
		.get(`${URL}/api/groups/get/allGroups`)
		.then((response) => response.data)
		.catch((error) => {
			console.error("Error getting all groups");
			throw error;
		});
};
