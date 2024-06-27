import React from 'react';
import NavBar from '../components/NavBar';
import Header from '../components/Header';
import '../assets/styles/AdminPage.css';


const AdminPage = () => (
  <div className="admin-page">
    <Header/>
    <div className='content'>
      <div className="admin-nav">
        <NavBar/>
      </div>
      <div className="admin-content">
      </div>
    </div>
  
  </div>
);

export default AdminPage;
