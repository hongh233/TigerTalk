export const filterGroups = (groups, query) => {
	if (!query) {
		return groups;
	}

	const lowerCaseQuery = query.toLowerCase();
	return groups.filter((group) =>
		group.groupName.toLowerCase().includes(lowerCaseQuery)
	);
};
