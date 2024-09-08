export const formatDate = (timestamp) => {
	const date = new Date(timestamp);
	return date.toLocaleString();
};

export const formatLocalDate = (timestamp) => {
	const date = new Date(timestamp);
	return date.toLocaleDateString();
};