import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { FaComment, FaShare } from "react-icons/fa";
import Comment from "./Comment";
import {
	handleAddCommentAxios,
	getCommentFromPostId,
} from "./../axios/PostAxios";
import { fetchUserByEmail } from "./../axios/AuthenticationAxios";
import { formatDate } from "./../utils/formatDate";
import "../assets/styles/GroupPost.css";

const GroupPost = ({ post }) => {
	console.log(post);
	const [postComments, setPostComments] = useState(null);
	const [newComment, setNewComment] = useState("");

	const navigate = useNavigate();

	useEffect(() => {}, [postComments]);

	const handleFetchAndDisplayComments = async () => {
		const fetchedComments = await getCommentFromPostId(post.groupPostId);
		setPostComments(fetchedComments);
	};

	const handleCommentChange = (e) => {
		setNewComment(e.target.value);
	};

	// const handleAddComment = async () => {
	// 	const postSenderUserProfileDTO = await fetchUserByEmail(
	// 		post.postSenderEmail
	// 	);

	// 	if (newComment.trim() === "") return;
	// 	if (postSenderUserProfileDTO) {
	// 		const newCommentObj = {
	// 			content: newComment,
	// 			timestamp: new Date(),
	// 			commentSenderUserProfileDTO: user,
	// 			postSenderUserProfileDTO: postSenderUserProfileDTO,
	// 			postId: post.groupPostId,
	// 		};

	// 		await handleAddCommentAxios(newCommentObj);
	// 		await handleFetchAndDisplayComments();
	// 		setNewComment("");
	// 	}
	// };

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

	const handleTagClick = () => {
		navigate(`/friends`);
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
					// onClick={handleFetchAndDisplayComments}
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
						<Comment key={index} postComment={postComment} />
					))}
				<div className="add-comment">
					<input
						type="text"
						placeholder="Add a comment..."
						value={newComment}
						onChange={handleCommentChange}
					/>
					{/* <button onClick={handleAddComment}>Post</button> */}
				</div>
			</div>
		</div>
	);
};

export default GroupPost;
