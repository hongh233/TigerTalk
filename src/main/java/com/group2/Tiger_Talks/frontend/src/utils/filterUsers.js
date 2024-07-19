export const filterUsers = (users, query) => {
	if (!query) {
		return users;
	}
	const lowerCaseQuery = query.toLowerCase();
	return users.filter(
		(user) =>
			user.email.toLowerCase().includes(lowerCaseQuery) ||
			user.userName.toLowerCase().includes(lowerCaseQuery)
	);
};
