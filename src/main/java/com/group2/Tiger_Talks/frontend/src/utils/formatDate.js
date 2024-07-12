export const formatDate = (timestamp) => {
	const date = new Date(timestamp);
	return date.toLocaleString();
};
