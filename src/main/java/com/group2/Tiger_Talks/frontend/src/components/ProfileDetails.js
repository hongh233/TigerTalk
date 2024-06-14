import React from 'react';
import './../assets/styles/ProfileDetails.css';

const ProfileDetails = ({ user }) => (
  <div className="profile-details">
    <h2>{user.name}</h2>
    <p>{user.bio}</p>
  </div>
);

export default ProfileDetails;
