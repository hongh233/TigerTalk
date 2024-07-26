import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { FaBell, FaUser } from "react-icons/fa";
import { MdMessage } from "react-icons/md";
import Notification from "./Notification";
import "../assets/styles/NotificationButton.css";
import axios from "axios";
import { useSelector } from "react-redux";

const NotificationButton = () => {
	const navigate = useNavigate();
	const user = useSelector((state) => state.user.user);
	const [showNotifications, setShowNotifications] = useState(false);
	const [notifications, setNotifications] = useState([]);

	useEffect(() => {
		if (user) {
			const fetchNotifications = async () => {
				try {
					const response = await axios.get(
						`http://localhost:8085/api/notification/get/${user.email}`
					);
					setNotifications(response.data);
				} catch (err) {
					console.error("There was an error fetching notifications!", err);
				}
			};
			fetchNotifications();
		}
	}, [user]);

	const toggleNotifications = () => {
		setShowNotifications(!showNotifications);
	};

	return (
		<div className="notification-button">
			<div className="notification-icon-wrapper">
				<FaUser
					className="notification-icon"
					onClick={() => navigate(`/profile/${user.email}`)}
				/>
				<FaBell className="notification-icon" onClick={toggleNotifications} />
				{notifications.length > 0 && (
					<span className="notification-count">{notifications.length}</span>
				)}
				<MdMessage
					className="notification-icon"
					onClick={() => navigate(`/friends/message`)}
				/>
			</div>
			{showNotifications && (
				<Notification
					notifications={notifications}
					setNotifications={setNotifications}
				/>
			)}
		</div>
	);
};

export default NotificationButton;
