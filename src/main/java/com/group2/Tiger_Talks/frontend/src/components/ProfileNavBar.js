import React from 'react';
import {FaHome, FaHammer, FaSignOutAlt } from 'react-icons/fa';
import '../assets/styles/ProfileNavBar.css';
import GroupTab from './GroupTab';

const ProfileNavBar = ({ user }) => {
  const handleLogOut = () => {
    localStorage.removeItem('user');
  };
  return(
    <nav className="profile-navbar">
      <div className="profile-header">
        <div className="profile-user-picture"></div>
        <div className="profile-info">
          <h3>{user.name}</h3>
          <p>{user.email}</p>
        </div>
      </div>
      <div className="profile-links">
        <a href="/main"><FaHome /><span className='text-hide'>Home</span></a>
        <a href="/profile/1"><FaHammer /><span className='text-hide'>Profile Setting</span></a>
        
        <GroupTab/>
        <a href="/" onClick={handleLogOut}><FaSignOutAlt /><span className='text-hide'>Logout</span></a>
      </div>
    </nav>
  );
};
export default ProfileNavBar;
