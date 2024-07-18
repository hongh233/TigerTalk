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

export const handleUpdateGroup = (groupUpdate) => {
	return axios
		.post(`${URL}/api/groups/update/groupInfo`, groupUpdate)
		.then((response) => response.data)
		.catch((error) => {
			console.error("Error updating group");
			throw error;
		});
};

export const handleDeleteGroup = (groupId) => {
	return axios
		.delete(`${URL}/api/groups/delete/group/${groupId}`)
		.then((response) => response.data)
		.catch((error) => {
			console.error("Error deleting group");
			throw error;
		});
};

export const handleGetGroupById = (groupId) => {
	return axios
		.get(`${URL}/api/groups/get/group/${groupId}`)
		.then((response) => response.data)
		.catch((error) => {
			console.error("Error getting all groups");
			throw error;
		});
};

export const checkIsMember = (userEmail, groupId) => {
	return axios
		.get(`${URL}/api/groups/get/getMemberShipId/${userEmail}/${groupId}`)
		.then(() => true)
		.catch((error) => {
			console.error("Error getting all groups");
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

export const handleJoinGroup = (userEmail, groupId) => {
	return axios
		.post(`${URL}/api/groups/join/${userEmail}/${groupId}`)
		.then((response) => response.data)
		.catch((error) => {
			console.error("Error joining user to the group");
			throw error;
		});
};

export const handleLeaveGroup = (groupMembershipId) => {
	return axios
		.delete(`${URL}/api/groups/delete/groupMembership/${groupMembershipId}`)
		.then((response) => response.data)
		.catch((error) => {
			console.error("Error leaving group");
			throw error;
		});
};

//GROUP POST
export const handleCreatePost = (post) => {
	return axios
		.post(`${URL}/api/groups/post/create`, post)
		.then((response) => response.data)
		.catch((error) => {
			console.error("Error creating group post");
			throw error;
		});
};

export const handleGetAllPost = (groupId) => {
	return axios
		.get(`${URL}/api/groups/post/getAll/${groupId}`)
		.then((response) => response.data)
		.catch((error) => {
			console.error("Error creating group post");
			throw error;
		});
};

export const handleGetGroupUserIsMember = (userEmail) => {
	return axios
		.get(`${URL}/api/groups/get/allGroups/${userEmail}`)
		.then((response) => response.data)
		.catch((error) => {
			console.error("Error getting groups for user");
			throw error;
		});
};

export const handleAddGroupPostComment = (groupPostId, newCommentObj) => {
	return axios
		.post(`${URL}/api/groups/post/comment/create/${groupPostId}`, newCommentObj)
		.then((response) => response.data)
		.catch((error) => {
			console.error("Error creating group post");
			throw error;
		});
};

export const handleGetCommentsForOneGroupPost = (groupPostId) => {
	return axios
		.get(`${URL}/api/groups/post/comment/${groupPostId}`)
		.then((response) => response.data)
		.catch((error) => {
			console.error("Error getting group post comments");
			throw error;
		});
};
export const handleGetGroupMembershipId = (userEmail, groupId) => {
	return axios
		.get(`${URL}/api/groups/get/getMemberShipId/${userEmail}/${groupId}`)
		.then((response) => response.data)
		.catch((error) => {
			console.error("Error getting group mebership id");
			throw error;
		});
};

export const handleFindGroups = (groupName, userEmail) => {
	return axios
		.get(`${URL}/api/groups/search/publicGroups/${groupName}/${userEmail}`)
		.then((response) => response.data)
		.catch((error) => {
			console.error("Error search for groups");
			throw error;
		});
};
