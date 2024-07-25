import React from 'react';
import '../assets/styles/Notification.css';
import { FaEye, FaTrash } from 'react-icons/fa';
import axios from "axios";

const Notification = ({ notifications, setNotifications }) => {

    const handleDelete = (notificationId) => {
        axios.delete(`http://localhost:8085/api/notification/delete/${notificationId}`)
            .then(response => {
                setNotifications(prevNotifications =>
                    [...prevNotifications.filter(notification => notification.notificationId !== notificationId)]
                );
            })
            .catch(error => {
                console.error("There was an error deleting the notification!", error);
            });
    };

    return (
        <div className="notification-popup">
            <ul>
                {notifications.map((notification) => (
                    <li key={notification.notificationId}>
                        <div className="notification-left">
                            <span className="notification-text">{notification.content}</span>
                            <div className="notification-time">
                                {new Date(notification.createTime).toLocaleString()}
                            </div>
                        </div>
                        <div className="notification-buttons">
                            <FaEye style={{ cursor: 'pointer' }} />
                            <FaTrash
                                onClick={() => handleDelete(notification.notificationId)}
                                style={{ cursor: 'pointer' }}
                            />
                        </div>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default Notification;
