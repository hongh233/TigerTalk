import React from "react";
import axios from "axios";
import { useDispatch } from "react-redux";
import { useNavigate } from "react-router-dom";
import { FaHammer, FaHome, FaSignOutAlt, FaUserShield } from "react-icons/fa";
import "../assets/styles/ProfileNavBar.css";
import GroupTab from "./GroupTab";
import FriendsTab from "./FriendsTab";
import { persistor } from "../redux/store";

const getStatusColor = (status) => {
	switch (status) {
		case "available":
			return "green";
		case "busy":
			return "red";
		case "away":
			return "#e5de00";
		default:
			return "gray";
	}
};

const ProfileNavBar = ({ user }) => {
	const dispatch = useDispatch();
	const navigate = useNavigate();
	const handleLogOut = async () => {
		try {
			const response = await axios.post(
				`http://localhost:8085/api/logIn/userLogOut?email=${user.email}`
			);
			if (response.status === 200) {
				localStorage.removeItem("user");
				dispatch({ type: "SET_USER", payload: null });
				navigate("/");
			}
		} catch (error) {
			console.error("Failed to logout", error);
		}
	};
	console.log(user);

	return (
		<nav className="profile-navbar">
			<div className="profile-section">
				<div className="profile-header">
					<div className="profile-user-picture">
						{user.profilePictureUrl && (
							<img src={user.profilePictureUrl} alt="Profile" />
						)}
					</div>
					<div className="profile-info">
						<h3>
							{user.userName}
							<span
								className="status-circle"
								style={{ backgroundColor: getStatusColor(user.onlineStatus) }}
							></span>
						</h3>
						<p>{user.email}</p>
					</div>
				</div>
				<div className="profile-detail-container">
					<div className="profile-detail-wrapper">
						{user.firstName && user.lastName && (
							<div className="profile-detail">
								<strong>Full name:</strong>{" "}
								<span>
									{user.firstName} {user.lastName}
								</span>
							</div>
						)}
						{user.biography && (
							<div className="profile-detail">
								<strong>Personal interest:</strong>{" "}
								<span>{user.biography}</span>
							</div>
						)}
						{user.age && (
							<div className="profile-detail">
								<strong>Age:</strong> <span>{user.age}</span>
							</div>
						)}
						{user.gender && (
							<div className="profile-detail">
								<strong>Gender:</strong> <span>{user.gender}</span>
							</div>
						)}
					</div>
				</div>
			</div>
			<div className="profile-links">
				<a href="/main">
					<FaHome />
					<span className="text-hide">Home</span>
				</a>
				<a href="/profile/edit">
					<FaHammer />
					<span className="text-hide">Profile Setting</span>
				</a>
				<GroupTab />
				<FriendsTab />
				{user.userLevel === "admin" && (
					<a href="/admin">
						<FaUserShield />
						<span className="text-hide">Admin</span>
					</a>
				)}
				<a href="/" onClick={handleLogOut}>
					<FaSignOutAlt />
					<span className="text-hide">Logout</span>
				</a>
			</div>
		</nav>
	);
};

export default ProfileNavBar;
