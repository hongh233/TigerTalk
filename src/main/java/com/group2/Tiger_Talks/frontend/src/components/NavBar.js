import React from 'react';
import { NavLink } from 'react-router-dom';
import { FaHome, FaUser, FaSignOutAlt } from 'react-icons/fa';
import GroupTab from './GroupTab';
import '../assets/styles/NavBar.css';
const NavBar = () => {
  const handleLogOut = () => {
    localStorage.removeItem("user");
  };

  return (
    <nav className="navbar">
      <NavLink to="/main">
        <FaHome /><span className='text-hide'> Home</span>
      </NavLink>
      <GroupTab/>
      <NavLink to="/profile">
        <FaUser /><span className='text-hide'>Account</span> 
      </NavLink>
      <NavLink to="/" onClick={handleLogOut}>
        <FaSignOutAlt /> <span className='text-hide'>Logout</span> 
      </NavLink>
    </nav>
  );
};
export default NavBar;
