import React from "react";
import "../assets/styles/Comment.css";

const Comment = ({ postComment }) => (
	<div className="comment">
		<div className="comment-header">
			<div className="comment-profile-picture"></div>
			<div className="comment-user-details">
				<h4>{postComment.userName}</h4>
				<p>{postComment.time}</p>
			</div>
		</div>
		<div className="comment-content">
			<p>{postComment.content}</p>
		</div>
	</div>
);

export default Comment;
