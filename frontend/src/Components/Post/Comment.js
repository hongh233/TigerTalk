import React from "react";
import "../../assets/styles/Components/Post/Comment.css";
const Comment = ({ postComment }) => {
	const formatDate = (timestamp) => {
		const date = new Date(timestamp);
		return date.toLocaleString();
	};
	//adding comment
	return (
		<div className="comment">
			<div className="comment-header">
				<div className="comment-profile-picture">
					<a
						className="post-user-email"
						href={`/profile/${postComment.commentSenderUserProfileDTO.email}`}
					>
						<img
							src={postComment.commentSenderUserProfileDTO.profilePictureUrl}
							alt="Profile"
						/>
					</a>
				</div>

				<div className="comment-user-details">
					<a
						className="comment-user-email"
						href={`/profile/${postComment.commentSenderUserProfileDTO.email}`}
					>
						<h4>{postComment.commentSenderUserProfileDTO.userName}</h4>
					</a>

					<p>{formatDate(postComment.timestamp)}</p>
				</div>
			</div>
			<div className="comment-content">
				<p>{postComment.content}</p>
			</div>
		</div>
	);
};

export default Comment;
