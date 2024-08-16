import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { FaBell, FaUser } from "react-icons/fa";
import { MdMessage } from "react-icons/md";
import Notification from "./Notification";
import "../../assets/styles/Components/Notification/NotificationButton.css";
import { useSelector } from "react-redux";
import {getNotifications} from "../../axios/Notification/NotificationAxios";

const NotificationButton = () => {
	const navigate = useNavigate();
	const user = useSelector((state) => state.user.user);
	const [showNotifications, setShowNotifications] = useState(false);
	const [notifications, setNotifications] = useState([]);

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
				<div className="notification-icon-wrapper-real">
					<FaBell className="notification-icon" onClick={toggleNotifications} />
					{notifications.length > 0 && (
						<span className="notification-count">{notifications.length}</span>
					)}
				</div>
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
