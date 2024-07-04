import React from 'react';
import '../assets/styles/Comment.css';

const Comment = ({postComment}) => (
    <div className="postComment">
        <div className="postComment-header">
            <div className="postComment-profile-picture"></div>
            <div className="postComment-user-details">
                <h4>{postComment.userName}</h4>
                <p>{postComment.time}</p>
            </div>
        </div>
        <div className="postComment-content">
            <p>{postComment.content}</p>
        </div>
    </div>
);

export default Comment;
