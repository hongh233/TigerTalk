import React from 'react';
import { NavLink } from 'react-router-dom';
import { FaHome, FaEnvelope, FaUser, FaSignOutAlt } from 'react-icons/fa';
import GroupTab from './GroupTab';
import '../assets/styles/NavBar.css';
const NavBar = () => (
  <nav className="navbar">
    <NavLink to="/">
      <FaHome /><span className='text-hide'> Home</span>
    </NavLink>
    <GroupTab/>
    <NavLink to="/profile">
      <FaUser /><span className='text-hide'>Account</span> 
    </NavLink>
    <NavLink to="/login">
      <FaSignOutAlt /> <span className='text-hide'>Logout</span> 
    </NavLink>
  </nav>
);

export default NavBar;
