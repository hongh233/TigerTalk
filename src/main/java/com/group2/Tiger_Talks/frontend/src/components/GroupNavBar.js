import React from 'react';
import {FaHome, FaSignOutAlt } from 'react-icons/fa';
import '../assets/styles/GroupNavBar.css';
import GroupTab from './GroupTab';

const GroupNavBar = () => (
  <nav className="group-navbar">
    <div className="group-links">
      <a href="/"><FaHome />Home</a>
      <GroupTab/>
      <a href="/login"><FaSignOutAlt />Logout</a>
    </div>
  </nav>
);

export default GroupNavBar;
