import React, {useState} from "react";
import {useNavigate} from "react-router-dom";
import axios from "axios";
import {FaComment, FaShare, FaThumbsUp} from "react-icons/fa";
import Comment from "./Comment";
import "../assets/styles/Post.css";

const Post = ({post, user}) => {
    const [likes, setLikes] = useState(post.likes || post.numOfLike);
    const [postComments, setComments] = useState(post.postComments || []);
    const [newComment, setNewComment] = useState("");
    const navigate = useNavigate();

    const handleLike = () => {
        const postId = post.id || post.postId;
        const userEmail = user.email;
        if (!postId || !userEmail) {
            console.error('Post ID or User Email is undefined.');
            return;
        }

        axios.put(`http://localhost:8085/posts/like/${postId}?userEmail=${userEmail}`)
            .then(response => {
                setLikes(response.data);
            })
            .catch(error => {
                console.error('Error liking post:', error);
            });
    };

    const handleCommentChange = (e) => {
        setNewComment(e.target.value);
    };

    const handleAddComment = () => {
        if (newComment.trim() === "") return;

        const newCommentObj = {
            userName: "Current User",
            time: new Date().toLocaleString(),
            content: newComment,
        };
        setComments([...postComments, newCommentObj]);
        setNewComment("");
    };

    /*
    const handleTagClick = (tag) => {
      // Define what happens when a tag is clicked
      console.log(`Tag clicked: ${tag}`);
    };
    */

    const handleTagClick = (tag) => {
        const userName = tag.substring(1); // Remove the '@' from the tag
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
                    <p>{post.timestamp}</p>
                </div>
            </div>
            <div className="post-content">
                {/*<p>{post.content}</p>*/}
                <p>{renderPostContent(post.content)}</p>
            </div>
            <div className="post-footer">
                <button className="post-button" onClick={handleLike}>
                    <FaThumbsUp/> Like ({likes})
                </button>
                <button className="post-button">
                    <FaComment/> Comment
                </button>
                <button className="post-button">
                    <FaShare/> Share
                </button>
            </div>
            <div className="postComments-section">
                {postComments.map((postComment, index) => (
                    <Comment key={index} postComment={postComment}/>
                ))}
                <div className="add-postComment">
                    <input
                        type="text"
                        placeholder="Add a postComment..."
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
