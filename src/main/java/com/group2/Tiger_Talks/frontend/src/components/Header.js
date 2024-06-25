import React from 'react';
import NavBar from './NavBar';
import NotificationButton from './NotificationButton';
import '../assets/styles/Header.css';

const Header = () => (
  <header className="header">
    <h1>
      <a href="/main" className="home-link">Tiger Talks</a>
    </h1>
      <NotificationButton />
    <div className="search-bar">
      <input type="text" placeholder="Search..." />
      <button type="button">Search</button>
    </div>
  
  </header>
);

export default Header;
