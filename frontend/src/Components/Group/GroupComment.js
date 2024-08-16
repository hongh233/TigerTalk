import React from "react";
import "../../assets/styles/Components/Group/GroupComment.css";
const GroupComment = ({ postComment }) => {
	const formatDate = (timestamp) => {
		const date = new Date(timestamp);
		return date.toLocaleString();
	};

	return postComment ? (
		<div className="comment">
			<div className="comment-header">
				<div className="comment-profile-picture">
					<a
						className="post-user-email"
						href={`/profile/${postComment.senderEmail}`}
					>
						<img
							src={postComment.groupPostCommentSenderProfilePictureURL}
							alt="Profile"
						/>
					</a>
				</div>

				<div className="comment-user-details">
					<a
						className="comment-user-email"
						href={`/profile/${postComment.senderEmail}`}
					>
						<h4>{postComment.groupPostCommentSenderUserName}</h4>
					</a>
					<p>{formatDate(postComment.groupPostCommentCreateTime)}</p>
				</div>
			</div>
			<div className="comment-content">
				<p>{postComment.groupPostCommentContent}</p>
			</div>
		</div>
	) : (
		""
	);
};

export default GroupComment;
