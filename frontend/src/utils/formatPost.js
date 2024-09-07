export const formatPost = (array) => {
	return array.map((post) => ({
		id: post.postId,
		content: post.content,
		timestamp: post.timestamp,
		email: post.email,
		userProfileUserName: post.userProfileUserName,
		likes: post.numOfLike,
		comments: [],
		profileProfileURL: post.profileProfileURL,
		postImageURL: post.postImageURL,
		edited: post.edited,
		onlineStatus: post.onlineStatus
	}));
};
