import React from "react";
import "../../assets/styles/Components/Group/GroupPostComment.css";
import {formatDate} from "../../utils/formatDate";
import StatusIcon from "../Main/StatusIcon";


const GroupPostComment = ({ postComment }) => {

	return postComment ? (
		<div className="comment">
			<div className="comment-header">
				<div className="common-profile-picture-and-status-icon">
					<a className="post-user-email" href={`/profile/${postComment.senderEmail}`}>
						<img src={postComment.groupPostCommentSenderProfilePictureURL} alt="Profile"/>
						<StatusIcon status={postComment.onlineStatus} />
					</a>
				</div>

				<div className="comment-user-details">
					<a className="comment-user-email" href={`/profile/${postComment.senderEmail}`}>
						<h4>{postComment.groupPostCommentSenderUserName}</h4>
					</a>
					<p>{formatDate(postComment.groupPostCommentCreateTime)}</p>
				</div>
			</div>
			<div className="group-comment-content">
				<p>{postComment.groupPostCommentContent}</p>
			</div>
		</div>
	) : (
		""
	);
};

export default GroupPostComment;
