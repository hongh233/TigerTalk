import React from 'react';
import '../../assets/styles/Components/Notification/Notification.css';
import { FaEye, FaTrash } from 'react-icons/fa';
import { useNavigate } from 'react-router-dom';
import {deleteNotification} from "../../axios/Notification/NotificationAxios";

const Notification = ({ notifications, setNotifications }) => {

    const navigate = useNavigate();

    const handleDelete = async (notificationId) => {
        try {
            await deleteNotification(notificationId);
            setNotifications(prevNotifications =>
                prevNotifications.filter(notification => notification.notificationId !== notificationId)
            );
        } catch (error) {
            console.error("Failed to delete the notification.", error);
        }
    };

    const handleView = (notification) => {
        switch (notification.notificationType) {
            case 'FriendshipRequestSend':
                navigate('/friends/friend-request-list');
                handleDelete(notification.notificationId);
                break;
            case 'FriendshipRequestAccept':
                navigate('/friends/friend-list');
                handleDelete(notification.notificationId);
                break;
            case 'FriendshipRequestReject':
                handleDelete(notification.notificationId);
                break;
            case 'FriendshipDelete':
                navigate('/friends/friend-list');
                handleDelete(notification.notificationId);
                break;
            case 'NewPost':
                navigate('/main');
                handleDelete(notification.notificationId);
                break;
            case 'PostLiked':
                navigate('/main');
                handleDelete(notification.notificationId);
                break;
            case 'PostComment':
                navigate('/main');
                handleDelete(notification.notificationId);
                break;
            case 'GroupCreation':
                navigate('/group');
                handleDelete(notification.notificationId);
                break;
            case 'GroupMembershipDeletion':
                handleDelete(notification.notificationId);
                navigate('/group');
                break;
            case 'GroupJoin':
                navigate('/group');
                handleDelete(notification.notificationId);
                break;
            case 'GroupDeletion':
                navigate('/group');
                handleDelete(notification.notificationId);
                break;
            case 'GroupPostCreation':
                navigate('/group');
                handleDelete(notification.notificationId);
                break;
            case 'GroupPostLiked':
                navigate('/group');
                handleDelete(notification.notificationId);
                break;
            case 'GroupPostComment':
                navigate('/group');
                handleDelete(notification.notificationId);
                break;
            default:
                console.log('Unknown notification type:', notification.notificationType);
                break;
        }
    };

    return (
        <div className="notification-popup">
            {notifications.length > 0 ? (
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
                                <FaEye style={{ cursor: 'pointer' }}
                                       onClick={() => handleView(notification)}
                                />
                                <FaTrash
                                    onClick={() => handleDelete(notification.notificationId)}
                                    style={{ cursor: 'pointer' }}
                                />
                            </div>
                        </li>
                    ))}
                </ul>
            ) : (
            <div className="notification-empty">There is no new notification!</div>
            )}
        </div>
    );
};

export default Notification;
