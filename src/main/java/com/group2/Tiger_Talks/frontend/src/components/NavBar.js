import React from 'react';
import { NavLink } from 'react-router-dom';
import { FaHome, FaEnvelope, FaUser, FaSignOutAlt } from 'react-icons/fa';
import GroupTab from './GroupTab';
import '../assets/styles/NavBar.css';
const NavBar = () => (
  <nav className="navbar">
    <NavLink to="/" exact activeClassName="active">
      <FaHome /> Home
    </NavLink>
    <GroupTab/>
    <NavLink to="/profile" activeClassName="active">
      <FaUser /> Account
    </NavLink>
    <NavLink to="/login" activeClassName="active">
      <FaSignOutAlt /> Logout
    </NavLink>
  </nav>
);

export default NavBar;
