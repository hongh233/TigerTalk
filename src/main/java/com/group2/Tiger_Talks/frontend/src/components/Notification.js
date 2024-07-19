import React, { useEffect, useState } from 'react';
import '../assets/styles/Notification.css';
import { useSelector } from 'react-redux';
import axios from 'axios';

const Notification = () => {
    const user = useSelector((state) => state.user.user);
    const [notifications, setNotifications] = useState([]);
    const [visibleCount, setVisibleCount] = useState(3);


    useEffect(() => {
        if (user) {
            const fetchNotifications = async (email) => {
                try {
                    const response = await axios.get(
                        `http://localhost:8085/api/notification/get/${email}`
                    );
                    console.log(response);
                    setNotifications(response.data);
                } catch (err) {
                    console.error("There was an error fetching notifications!", err);
                }
            };
            fetchNotifications(user.email);
        }
    }, [user]);

    const handleApprove = (notificationId) => {
        // Handle approve logic here
        console.log(`Approved notification ${notificationId}`);
    };

    const handleDisapprove = (notificationId) => {
        // Handle disapprove logic here
        console.log(`Disapproved notification ${notificationId}`);
    };

    const handleSeeMore = () => {
        setVisibleCount(prevCount => prevCount + 3);
    };

    return (
        <div className="notification-popup">
            <ul>
                {notifications.slice(0, visibleCount).map((notification) => (
                    <li key={notification.notificationId}>
                        {notification.content}
                        {notification.notificationType === 'FriendshipRequestSend' && (
                            <div>
                                <button onClick={() => handleApprove(notification.notificationId)}>Approve</button>
                                <button onClick={() => handleDisapprove(notification.notificationId)}>Disapprove</button>
                            </div>
                        )}
                    </li>
                ))}
            </ul>
            {visibleCount < notifications.length && (
                <button onClick={handleSeeMore}>See more</button>
            )}
        </div>
    );
};

export default Notification;
