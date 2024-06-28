import React from 'react';
import '../assets/styles/Comment.css';

const Comment = ({comment}) => (
    <div className="comment">
        <div className="comment-header">
            <div className="comment-profile-picture"></div>
            <div className="comment-user-details">
                <h4>{comment.userName}</h4>
                <p>{comment.time}</p>
            </div>
        </div>
        <div className="comment-content">
            <p>{comment.content}</p>
        </div>
    </div>
);

export default Comment;
