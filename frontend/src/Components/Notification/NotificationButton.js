import React, {useEffect, useRef, useState} from "react";
import "../../assets/styles/Components/Notification/NotificationButton.css";
import {deleteNotification, getNotifications} from "../../axios/Notification/NotificationAxios";
import {FaBell, FaEye, FaTrash} from "react-icons/fa";
import { useSelector } from "react-redux";
import {useNavigate} from "react-router-dom";


const NotificationButton = () => {
	const navigate = useNavigate();
	const user = useSelector((state) => state.user.user);
	const [showNotifications, setShowNotifications] = useState(false);
	const [notifications, setNotifications] = useState([]);
	const timeoutRef = useRef(null);

	useEffect(() => {
		if (user) {
			const fetchNotifications = async () => {
				try {
					const data = await getNotifications(user.email);
					setNotifications(data);
				} catch (err) {
					console.error("There was an error fetching notifications!", err);
				}
			};
			fetchNotifications();
		}
	}, [user]);

	const handleMouseEnter = () => {
		if (timeoutRef.current) {
			clearTimeout(timeoutRef.current);
		}
		timeoutRef.current = setTimeout(() => {
			setShowNotifications(true);
		}, 300);
	};

	const handleMouseLeave = () => {
		if (timeoutRef.current) {
			clearTimeout(timeoutRef.current);
		}
		timeoutRef.current = setTimeout(() => {
			setShowNotifications(false);
		}, 300);
	};

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
		<div className="notification-button">
			<div className="notification-icon-wrapper" onMouseEnter={handleMouseEnter} onMouseLeave={handleMouseLeave}>
				<FaBell className="notification-icon" onClick={() => setShowNotifications(!showNotifications)} />
				{notifications.length > 0 && (<span className="notification-count">{notifications.length}</span>)}
			</div>
			{showNotifications && (
				<div className="notification-popup" onMouseEnter={handleMouseEnter} onMouseLeave={handleMouseLeave}>
					{notifications.length > 0 ? (
						<ul>
							{notifications.map((notification) => (
								<li key={notification.notificationId}>
									<div className="notification-left">
										<span className="notification-text">{notification.content}</span>
										<div className="notification-time">{new Date(notification.createTime).toLocaleString()}</div>
									</div>
									<div className="notification-buttons">
										<FaEye style={{ cursor: 'pointer' }} onClick={() => handleView(notification)}/>
										<FaTrash onClick={() => handleDelete(notification.notificationId)} style={{ cursor: 'pointer' }}/>
									</div>
								</li>
							))}
						</ul>
					) : (<div className="notification-empty">There is no new notification!</div>)}
				</div>
			)}
		</div>
	);
};

export default NotificationButton;
