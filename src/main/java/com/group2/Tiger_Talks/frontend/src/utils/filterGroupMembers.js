export const filterUsersAlreadyInGroup = (groupMembers, users) => {
	// Extract the emails of group members
	const groupMemberEmails = groupMembers.map(
		(member) => member.userProfileDTO.email
	);

	// Filter users who are not in the group
	return users.filter((user) => !groupMemberEmails.includes(user.email));
};
