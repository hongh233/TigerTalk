import React, { useState, useEffect } from 'react';
import NavBar from '../components/NavBar';
import Header from '../components/Header';
import UserList from '../components/UserList';
import axios from 'axios';
import '../assets/styles/AdminPage.css';

const AdminPage = () => {
    const [selectedUsers, setSelectedUsers] = useState([]);
    const [data, setData] = useState([]);

    useEffect(() => {
      const fetchData = async () => {
        try {
          const response = await axios.get('http://localhost:8085/api/user/getAllProfiles');
          setData(response.data); 
        } catch (error) {
          console.error('Error fetching user data:', error);
        }
      };
      fetchData();
    }, []); 

    const handleEnableDisableUsers = () => {
        console.log('Toggling status for users:', selectedUsers);
        setSelectedUsers([]);
    };

    return (
        <div className="admin-page">
            <Header/>
            <div className='content'>
                <div className="admin-nav">
                    <NavBar/>
                </div>
                <div className="admin-content">
                    <UserList selectedUsers={selectedUsers} setSelectedUsers={setSelectedUsers} setData={setData} data={data}/>
                    <div className="admin-buttons">
                        <button 
                            className="add-user-button"
                        >
                            Add User
                        </button>
                        <button 
                            className="toggle-user-button" 
                            disabled={selectedUsers.length === 0} 
                            onClick={handleEnableDisableUsers}
                        >
                            Enable/Disable User
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default AdminPage;