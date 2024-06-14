import React from 'react';
import { FaUser,FaHome, FaEnvelope, FaBell, FaSignOutAlt } from 'react-icons/fa';
import '../assets/styles/ProfileNavBar.css';
import GroupTab from './GroupTab';

const ProfileNavBar = ({ user }) => (
  <nav className="profile-navbar">
    <div className="profile-header">
      <div className="profile-picture"></div>
      <div className="profile-info">
        <h3>{user.name}</h3>
        <p>{user.email}</p>
      </div>
    </div>
    <div className="profile-links">
      <a href="/"><FaHome />Home</a>
      <a href="/profile/1"><FaUser />Profile Setting</a>
      
      <GroupTab/>
      <a href="/login"><FaSignOutAlt />Logout</a>
    </div>
  </nav>
);

export default ProfileNavBar;
