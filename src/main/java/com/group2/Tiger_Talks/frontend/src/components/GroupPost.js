import React, { useEffect, useState } from "react";
import { FaComment, FaShare, FaThumbsUp, FaTrash } from "react-icons/fa";
import GroupComment from "./GroupComment";
import {
	handleAddGroupPostComment,
	handleGetCommentsForOneGroupPost,
	handleLikeAxios,
	handleDeletePostAxios,
	handleGetGroupById,
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
	removePost,
}) => {
	const [likes, setLikes] = useState(post.likes || post.numOfLikes);
	const [postComments, setPostComments] = useState([]);
	const [newComment, setNewComment] = useState("");
	const [isAuthorOrAdmin, setIsAuthorOrAdmin] = useState(false);
	const [commentToggle, setCommentToggle] = useState(false);
	//get number of comments
	useEffect(() => {
		if (post.groupPostId) {
			async function fetchComments() {
				const fetchedComments = await handleGetCommentsForOneGroupPost(
					post.groupPostId
				);
				setPostComments(fetchedComments);
			}
			fetchComments();
		}
	}, [commentToggle, post.groupPostId]);

	const handleFetchAndDisplayComments = () => {
		setCommentToggle((prevState) => !prevState); // Works don't touch
	};

	const handleLike = async () => {
		const postId = post.groupPostId;
		if (!postId || !userEmail) {
			console.error("Post ID or User Email is undefined.");
			return;
		}

		const updatedLikes = await handleLikeAxios(postId, userEmail);
		setLikes(updatedLikes);
	};

	const handleCommentChange = (e) => {
		setNewComment(e.target.value);
	};

	const handleAddCommentToAxios = async () => {
		try {
			setCommentToggle(false); // IT works don't touch
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

	useEffect(() => {
		const fetchGroupDetails = async () => {
			try {
				const groupData = await handleGetGroupById(groupId);

				if (
					groupData.groupCreatorEmail === userEmail ||
					userEmail === post.email
				) {
					setIsAuthorOrAdmin(true);
				} else {
					setIsAuthorOrAdmin(false);
				}
			} catch (error) {
				console.error("Failed to fetch group details", error);
			}
		};
		fetchGroupDetails();
	}, [userEmail, groupId, post]);

	const handleDelete = async () => {
		if (window.confirm("Are you sure you want to delete this post?")) {
			await handleDeletePostAxios(post.groupPostId);
			removePost(post.groupPostId);
			setPostComments([]);
			setLikes("");
		}
	};

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
						<img src={post.postPictureURL} alt="Post content" />
					</div>
				</div>
			)}

			<div className="group-post-footer">
				<button className="group-post-button" onClick={handleLike}>
					{likes} <FaThumbsUp />
				</button>
				<button
					className="group-post-button"
					onClick={handleFetchAndDisplayComments}
				>
					{postComments.length > 0 ? postComments.length : ""} <FaComment />
				</button>
				<button className="group-post-button" onClick={handleShare}>
					<FaShare />
				</button>
				{isAuthorOrAdmin && (
					<button className="post-button delete-button" onClick={handleDelete}>
						<FaTrash />
					</button>
				)}
			</div>
			<div className="group-postComments-section">
				{postComments &&
					commentToggle &&
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
