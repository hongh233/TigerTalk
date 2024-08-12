export const filterUsersAlreadyInGroup = (groupMembers, users) => {
	// Extract the emails of group members
	const groupMemberEmails = groupMembers.map(
		(member) => member.userProfileDTO.email
	);

	// Filter users who are not in the group
	return users.filter((user) => !groupMemberEmails.includes(user.email));
};
export const filterUsers = (users, searchQuery, filterOption) => {
	let filteredUsers = users;

	if (filterOption === "friends") {
		filteredUsers = users.filter((user) => user.isFriend); // Assuming you have a property to check friendship
	} else if (filterOption === "online") {
		filteredUsers = users.filter((user) => user.onlineStatus === "available");
	}

	if (searchQuery) {
		filteredUsers = filteredUsers.filter((user) =>
			user.userName.toLowerCase().includes(searchQuery.toLowerCase())
		);
	}

	return filteredUsers;
};

export const filterGroups = (groups, searchQuery, filterOption) => {
	let filteredGroups = groups;

	if (filterOption === "myGroups") {
		filteredGroups = groups.filter((group) =>
			group.groupMemberList.some(
				(member) => member.userProfileDTO.email === "currentUserEmail"
			)
		); // Replace with actual current user's email
	} else if (filterOption === "public") {
		filteredGroups = groups.filter((group) => !group.isPrivate);
	}

	if (searchQuery) {
		filteredGroups = filteredGroups.filter((group) =>
			group.groupName.toLowerCase().includes(searchQuery.toLowerCase())
		);
	}

	return filteredGroups;
};
