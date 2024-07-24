import axios from "axios";
const URL = process.env.REACT_APP_API_URL;

export const handleCreateGroup = (form) => {
	console.log(form);
	let interest = "music" // TODO: Handle group interest
	return axios
		.post(
			`${URL}/api/groups/create/${form.groupName}/${form.userEmail}/${form.status}/${interest}`
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

export const handleDeleteGroupMembership = (membershipId) => {
	return axios
		.delete(`${URL}/api/groups/delete/groupMembership/${membershipId}`)
		.then((response) => response.data)
		.catch((error) => {
			console.error("Error deleting group member");
			throw error;
		});
};

export const handleGetMembershipID = (userEmail, groupID) => {
	return axios
		.get(`${URL}/api/groups/get/getMemberShipId/${userEmail}/${groupID}`)
		.then((response) => response.data)
		.catch((error) => {
			console.error("Error retrieving membership ID");
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

export const handleLikeAxios = (postId, userEmail) => {
	return axios
		.put(`${URL}/api/groups/post/like/${postId}?userEmail=${userEmail}`)
		.then((response) => response.data)
		.catch((error) => {
			console.error("Error liking post:", error);
			throw error;
		});
};


export const handleDeletePostAxios = (postId) =>{
	return axios
	.delete(`${URL}/api/groups/post/delete/${postId}`)
	.then((response)=>response.data)
	.catch((error)=>{
		console.error("Error deleting post:",error);
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
			console.error("Error getting group membership id");
			throw error;
		});
};

export const handleFindGroups = (groupName, userEmail) => {
	return axios
		.get(`${URL}/api/search/group/${groupName}/${userEmail}`)
		.then((response) => response.data)
		.catch((error) => {
			console.error("Error searching for groups");
			throw error;
		});
};

export const handleGetGroupMembersByGroupId = (groupId) => {
	return axios
		.get(`${URL}/api/groups/get/group/${groupId}/members`)
		.then((response) => response.data)
		.catch((error) => {
			console.error("Error getting group membership id");
			throw error;
		});
};

export const handleAddUserToGroupByAdmin = (email, groupId) => {
	return axios
		.post(`${URL}/api/groups/admin/addUser/${email}/${groupId}`)
		.then((response) => response.data)
		.catch((error) => {
			console.error("Error getting group membership id");
			throw error;
		});
};
