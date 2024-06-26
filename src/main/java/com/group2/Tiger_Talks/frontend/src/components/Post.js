
import React, { useState } from 'react';
import { FaThumbsUp, FaComment, FaShare } from 'react-icons/fa';
import Comment from './Comment';
import '../assets/styles/Post.css';

const Post = ({ post }) => {
  const [likes, setLikes] = useState(post.likes);
  const [comments, setComments] = useState(post.comments || []);
  const [newComment, setNewComment] = useState('');

  const handleLike = () => {
    setLikes(likes + 1);
  };

  const handleCommentChange = (e) => {
    setNewComment(e.target.value);
  };

  const handleAddComment = () => {
    if (newComment.trim() === '') return;

    const newCommentObj = {
      userName: 'Current User',
      time: new Date().toLocaleString(),
      content: newComment,
    };
    setComments([...comments, newCommentObj]);
    setNewComment('');
  };

  const handleTagClick = (tag) => {
    // Define what happens when a tag is clicked
    console.log(`Tag clicked: ${tag}`);
  };

  const renderPostContent = (content) => {
    const parts = content.split(/(@\w+)/g);
    return parts.map((part, index) => {
      if (part.startsWith('@')) {
        return (
          <span 
            key={index} 
            className="tag" 
            onClick={() => handleTagClick(part)}
            style={{ color: 'blue', cursor: 'pointer' }}
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
        <div className="profile-picture"></div>
        <div className="post-user-details">
          <h3>{post.username}</h3>
          <p>{post.time}</p>
        </div>
      </div>
      <div className="post-content">
        <p>{post.content}</p>
      </div>
      <div className="post-footer">
        <button className="post-button" onClick={handleLike}>
          <FaThumbsUp /> Like ({likes})
        </button>
        <button className="post-button">
          <FaComment /> Comment
        </button>
        <button className="post-button">
          <FaShare /> Share
        </button>
      </div>
      <div className="comments-section">
        {comments.map((comment, index) => (
          <Comment key={index} comment={comment} />
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

