import React from 'react';
import '../assets/styles/PostCreation.css';

const PostCreation = () => (
  <div className="post-creation">
    <div className="profile-picture"></div>
    <textarea placeholder="What's Happening?"></textarea>
    <div className="location-and-image">
      <input type="text" placeholder="Want to add Location?" />
      <div className="image-upload">
        <span>+</span>
      </div>
    </div>
    <button className="post-button">Post</button>
  </div>
);

export default PostCreation;
