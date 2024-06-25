import React from 'react';
import {FaHome, FaSignOutAlt } from 'react-icons/fa';
import '../assets/styles/GroupNavBar.css';
import GroupTab from './GroupTab';

const GroupNavBar = () => (
  <nav className="group-navbar">
    <div className="group-links">
      <a href="/main"><FaHome /><span className='text-hide'>Home</span></a>
      <GroupTab/>
      <a href="/"><FaSignOutAlt /><span className='text-hide'>Logout</span></a>
    </div>
  </nav>
);

export default GroupNavBar;
