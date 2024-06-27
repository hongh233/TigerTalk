import React, { useState } from 'react';
import NavBar from '../components/NavBar';
import Header from '../components/Header';
import UserList from '../components/UserList';
import '../assets/styles/AdminPage.css';

const AdminPage = () => {
    const [selectedUsers, setSelectedUsers] = useState([]);

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
                    <UserList selectedUsers={selectedUsers} setSelectedUsers={setSelectedUsers}/>
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