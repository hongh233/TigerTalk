import React, {useState} from 'react';
import {FaBell} from 'react-icons/fa'; // Import the bell icon from react-icons
import Notification from './Notification';
import '../assets/styles/NotificationButton.css';

const NotificationButton = () => {
    const [showNotifications, setShowNotifications] = useState(false);

    const toggleNotifications = () => {
        setShowNotifications(!showNotifications);
    };

    return (
        <div className="notification-button">
            <FaBell onClick={toggleNotifications} className="notification-icon"/>
            {showNotifications && <Notification/>}
        </div>
    );
};

export default NotificationButton;
