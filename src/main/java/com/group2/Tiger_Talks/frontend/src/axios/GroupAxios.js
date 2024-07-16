import axios from "axios";
const URL = process.env.REACT_APP_API_URL;

export const handleCreateGroup = (form) => {
	console.log(form);
	return axios
		.post(
			`${URL}/api/groups/create/${form.groupName}/${form.userEmail}/${form.status}`
		)
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
