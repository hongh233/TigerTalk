import React, { useState, useEffect } from "react";
import "../../assets/styles/Components/Post/Post.css";
import { handleAddCommentAxios, getCommentFromPostId } from "../../axios/Post/PostCommentAxios";
import { handleLikeAxios, handleEditPostAxios, handleDeletePostAxios } from "../../axios/Post/PostAxios";
import { getCurrentUser } from "../../axios/UserAxios";
import { FaComment, FaShare, FaThumbsUp, FaTrash, FaEdit } from "react-icons/fa";
import { useNavigate } from "react-router-dom";
import Comment from "./Comment";
import { formatDate } from "../../utils/formatDate";


const Post = ({ post, user, removePost }) => {
	const [likes, setLikes] = useState(post.likes || post.numOfLikes);
	const [postComments, setPostComments] = useState([]);
	const [newComment, setNewComment] = useState("");
	const [isEditing, setIsEditing] = useState(false);
	const [editedContent, setEditedContent] = useState(post.content);
	const [isEdited, setIsEdited] = useState(post.edited);
	const [commentToggle, setCommentToggle] = useState(false);

	const navigate = useNavigate();

	//get number of comments
	useEffect(() => {
		if (post.id) {
			async function fetchComments() {
				const fetchedComments = await getCommentFromPostId(post.id);
				setPostComments(fetchedComments);
			}
			fetchComments();
		}
	}, [commentToggle, post.id]);

	const handleLike = async () => {
		const postId = post.id || post.postId;
		const userEmail = user.email;
		if (!postId || !userEmail) {
			console.error("Post ID or User Email is undefined.");
			return;
		}

		const updatedLikes = await handleLikeAxios(postId, userEmail);
		setLikes(updatedLikes);
	};

	const handleFetchAndDisplayComments = () => {
		setCommentToggle((prevState) => !prevState); // Works don't touch
	};

	const handleCommentChange = (e) => {
		setNewComment(e.target.value);
	};

	const handleAddComment = async () => {
		setCommentToggle(false); // IT works don't touch
		//fetch post owner DTO
		const postSenderUserProfileDTO = await getCurrentUser(post.email);

		if (newComment.trim() === "") return;
		if (postSenderUserProfileDTO) {
			const newCommentObj = {
				content: newComment,
				timestamp: new Date(),
				commentSenderUserProfileDTO: user,
				postSenderUserProfileDTO: postSenderUserProfileDTO,
				postId: post.id,
			};

			await handleAddCommentAxios(newCommentObj);
			await handleFetchAndDisplayComments();
			setNewComment("");
		}
	};

	const handleShare = async () => {
		if (navigator.share) {
			try {
				await navigator.share({
					text: `${post.content} - Posted by the user ${post.userProfileUserName} at ${post.timestamp}`,
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

	const renderPostContent = (content = "") => {
		if (typeof content !== "string") {
			console.error("Content is not a string:", content);
			return null;
		}
		const parts = content.split(/(@\w+)/g);
		return parts.map((part, index) => {
			if (part.startsWith("@")) {
				return (
					<span
						key={index}
						className="tag"
						onClick={() => handleTagClick(part)}
						style={{ color: "blue", cursor: "pointer" }}
					>
						{part}
					</span>
				);
			} else {
				return part;
			}
		});
	};

	const handleEditClick = () => {
		setIsEditing(true);
	};

	const handleCancelEdit = () => {
		setIsEditing(false);
		setEditedContent(post.content);
	};

	const handleSaveEdit = async () => {
		if (editedContent.trim() === "") return;
		await handleEditPostAxios(post.id, editedContent);
		setIsEditing(false);
		setEditedContent(editedContent);
		setIsEdited(true);
	};

	const handleDelete = async () => {
		if (window.confirm("Are you sure you want to delete this post?")) {
			await handleDeletePostAxios(post.id);
			removePost(post.id);
			setPostComments([]);
			setLikes("");
		}
	};

	const isAuthorOrAdmin =
		user.email === post.email || user.userLevel === "admin";

	return (
		<div className="post">
			<div className="post-header">
				<div className="profile-picture">
					<a className="post-user-email" href={`/profile/${post.email}`}>
						<img src={post.profileProfileURL} alt="avatar" />
					</a>
				</div>
				<div className="post-user-details">
					<h3>
						<a className="post-user-email" href={`/profile/${post.email}`}>
							{post.userProfileUserName}
						</a>
					</h3>
					<p>{formatDate(post.timestamp)}</p>
				</div>
			</div>

			<div className="post-content">
				{isEditing ? (
					<textarea value={editedContent}
						onChange={(e) => setEditedContent(e.target.value)}
					/>
				) : (
					<p>{renderPostContent(editedContent)}</p>
				)}
				{isEdited && <small className="edited-text">(edited)</small>}
			</div>

			{post.postImageURL && (
				<div className="post-content-img-container">
					<div className="post-content-img">
						<img src={post.postImageURL} alt="Post content" />
					</div>
				</div>
			)}

			<div className="post-footer">
				<button className="post-button" onClick={handleLike}>
					{likes} <FaThumbsUp />
				</button>
				<button className="post-button" onClick={handleFetchAndDisplayComments}>
					{postComments.length > 0 ? postComments.length : ""} <FaComment />
				</button>
				<button className="post-button" onClick={handleShare}>
					<FaShare />
				</button>
				{user.email === post.email && !isEditing && (
					<button className="post-button" onClick={handleEditClick}>
						<FaEdit />
					</button>
				)}
				{user.email === post.email && isEditing && (
					<>
						<button className="post-button" onClick={handleSaveEdit}>Save</button>
						<button className="post-button" onClick={handleCancelEdit}>Cancel</button>
					</>
				)}
				{isAuthorOrAdmin && (
					<button className="post-button delete-button" onClick={handleDelete}>
						<FaTrash />
					</button>
				)}
			</div>

			<div className="postComments-section">
				{postComments &&
					commentToggle &&
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
					<button onClick={handleAddComment}>Post</button>
				</div>
			</div>
		</div>
	);
};

export default Post;
