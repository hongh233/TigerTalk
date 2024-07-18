import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { FaComment, FaShare } from "react-icons/fa";
import GroupComment from "./GroupComment";
import {
	handleAddGroupPostComment,
	handleGetCommentsForOneGroupPost,
} from "./../axios/GroupAxios";
import { formatDate } from "./../utils/formatDate";
import "../assets/styles/GroupPost.css";
// import GroupPostDelete from "./GroupPostDelete";
// import {handleDeleteGroupPost} from "../axios/GroupAxios";

const GroupPost = ({
	post,
	isMember,
	groupId,
	groupMembershipId,
	userEmail,
}) => {
	const [postComments, setPostComments] = useState(null);
	const [newComment, setNewComment] = useState("");
	const navigate = useNavigate();

	useEffect(() => {}, [postComments]);

	const handleFetchAndDisplayComments = async () => {
		const fetchedComments = await handleGetCommentsForOneGroupPost(
			post.groupPostId
		);
		console.log(fetchedComments);
		setPostComments(fetchedComments);
	};

	const handleCommentChange = (e) => {
		setNewComment(e.target.value);
	};

	const handleAddCommentToAxios = async () => {
		try {
			if (newComment.trim() === "") return;

			const newCommentObj = {
				content: newComment,
				groupMembership: {
					groupMembershipId: groupMembershipId,
					userProfile: {
						email: userEmail,
					},
				},
			};
			await handleAddGroupPostComment(post.groupPostId, newCommentObj);
			await handleFetchAndDisplayComments();
			setNewComment("");
		} catch (error) {
			console.error("Error sharing content:", error);
		}
	};

	const handleShare = async () => {
		if (navigator.share) {
			try {
				await navigator.share({
					text: `${post.groupPostContent} - Posted by the user ${post.groupPostSenderUserName} at ${post.groupPostCreateTime}`,
				});
				console.log("Content shared successfully");
			} catch (error) {
				console.error("Error sharing content:", error);
			}
		} else {
			alert("Web Share API is not supported in your browser.");
		}
	};

	// const renderPostContent = (content) => {
	// 	const parts = content.split(/(@\w+)/g);
	// 	return parts.map((part, index) => {
	// 		if (part.startsWith("@")) {
	// 			return (
	// 				<span
	// 					key={index}
	// 					className="tag"
	// 					onClick={() => handleTagClick(part)}
	// 					style={{ color: "blue", cursor: "pointer" }}
	// 				>
	// 					{part}
	// 				</span>
	// 			);
	// 		} else {
	// 			return part;
	// 		}
	// 	});
	// };

	return (
		<div className="group-post">
			<div className="group-post-header">
				<div className="profile-picture">
					<a
						className="group-post-user-email"
						href={`/profile/${post.postSenderEmail}`}
					>
						<img src={post.groupPostSenderProfilePictureURL} alt="avatar" />
					</a>
				</div>
				<div className="group-post-user-details">
					<h3>
						<a
							className="group-post-user-email"
							href={`/profile/${post.postSenderEmail}`}
						>
							{post.groupPostSenderUserName}
						</a>
					</h3>
					<p>{formatDate(post.groupPostCreateTime)}</p>
				</div>
				{/* <GroupPostDelete
					groupPostId={post.groupPostId}
					onDelete={handlePostDelete}
				/> */}
			</div>
			<div className="group-post-content">
				<p>{post.groupPostContent}</p>
			</div>
			{post.postPictureURL && (
				<div className="post-content-img-container">
					<div className="post-content-img">
						<img src={post.postPictureURL} />
					</div>
				</div>
			)}

			<div className="group-post-footer">
				<button
					className="group-post-button"
					onClick={handleFetchAndDisplayComments}
				>
					<FaComment />
				</button>
				<button className="group-post-button" onClick={handleShare}>
					<FaShare />
				</button>
			</div>
			<div className="group-postComments-section">
				{postComments &&
					postComments.map((postComment, index) => (
						<GroupComment key={index} postComment={postComment} />
					))}

				{isMember && (
					<div className="add-comment">
						<input
							type="text"
							placeholder="Add a comment..."
							value={newComment}
							onChange={handleCommentChange}
						/>
						<button onClick={handleAddCommentToAxios}>Post</button>
					</div>
				)}
			</div>
		</div>
	);
};

export default GroupPost;
