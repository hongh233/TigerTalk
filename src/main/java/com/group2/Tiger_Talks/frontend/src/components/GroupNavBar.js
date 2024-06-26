import React from 'react';
import {FaHome, FaSignOutAlt } from 'react-icons/fa';
import '../assets/styles/GroupNavBar.css';
import GroupTab from './GroupTab';

const GroupNavBar = () => {
  const handleLogOut = () => {
    localStorage.removeItem("user");
  };
  return (
    <nav className="group-navbar">
      <div className="group-links">
        <a href="/main"><FaHome /><span className='text-hide'>Home</span></a>
        <GroupTab/>
        <a href="/" onClick={handleLogOut}><FaSignOutAlt /><span className='text-hide'>Logout</span></a>
      </div>
    </nav>
  );
};
export default GroupNavBar;
