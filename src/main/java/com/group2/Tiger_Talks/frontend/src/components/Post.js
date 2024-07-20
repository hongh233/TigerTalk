import React, {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import {FaComment, FaShare, FaThumbsUp} from "react-icons/fa";
import Comment from "./Comment";
import {
    handleLikeAxios,
    handleAddCommentAxios,
    getCommentFromPostId,
} from "./../axios/PostAxios";
import {fetchUserByEmail} from "./../axios/AuthenticationAxios";
import {formatDate} from "./../utils/formatDate";
import "../assets/styles/Post.css";

const Post = ({post, user}) => {
    const [likes, setLikes] = useState(post.likes || post.numOfLike);
    const [postComments, setPostComments] = useState(null);

    const [newComment, setNewComment] = useState("");

    const navigate = useNavigate();

    useEffect(() => {
    }, [postComments]);

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

    const [commentToggle, setCommentToggle] = useState(false);
    const handleFetchAndDisplayComments = async () => {
        setCommentToggle(prevState => !prevState); // Works don't touch
        const fetchedComments = await getCommentFromPostId(post.id);
        setPostComments(fetchedComments);
    };

    const handleCommentChange = (e) => {
        setNewComment(e.target.value);
    };

    const handleAddComment = async () => {
        setCommentToggle(false); // IT works don't touch
        //fetch post owner DTO
        const postSenderUserProfileDTO = await fetchUserByEmail(post.email);

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
    /*
    const handleTagClick = (tag) => {
      // Define what happens when a tag is clicked
      console.log(`Tag clicked: ${tag}`);
    };
    */

    const handleTagClick = () => {
        navigate(`/friends`);
    };

    const renderPostContent = (content) => {
        const parts = content.split(/(@\w+)/g);
        return parts.map((part, index) => {
            if (part.startsWith("@")) {
                return (
                    <span
                        key={index}
                        className="tag"
                        onClick={() => handleTagClick(part)}
                        style={{color: "blue", cursor: "pointer"}}
                    >
						{part}
					</span>
                );
            } else {
                return part;
            }
        });
    };

    return (
        <div className="post">
            <div className="post-header">
                <div className="profile-picture">
                    <a className="post-user-email" href={`/profile/${post.email}`}>
                        <img src={post.profileProfileURL} alt="avatar"/>
                    </a>
                </div>
                <div className="post-user-details">
                    <h3>
                        <a className="post-user-email" href={`/profile/${post.email}`}>
                            {post.userProfileUserName}
                        </a>
                    </h3>
                    {/* Display the username here */}
                    <p>{formatDate(post.timestamp)}</p>
                </div>
            </div>

            <div className="post-content">
                <p>{renderPostContent(post.content)}</p>
            </div>
            {post.postImageURL && (
                <div className="post-content-img-container">
                    <div className="post-content-img">
                        <img src={post.postImageURL} alt="Post content"/>
                    </div>
                </div>
            )}
            <div className="post-footer">
                <button className="post-button" onClick={handleLike}>
                    {likes} <FaThumbsUp/>
                </button>
                <button className="post-button" onClick={handleFetchAndDisplayComments}>
                    <FaComment/>
                </button>
                <button className="post-button" onClick={handleShare}>
                    <FaShare/>
                </button>
            </div>
            <div className="postComments-section">
                {postComments && commentToggle &&
                    postComments.map((postComment, index) => (
                        <Comment key={index} postComment={postComment}/>
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
