import React, { useEffect, useState } from "react";
import axios from "axios";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate, NavLink, useParams } from "react-router-dom";
import {
	FaUser,
	FaHome,
	FaSignOutAlt,
	FaUserShield,
	FaCog,
} from "react-icons/fa";
import "../assets/styles/ProfileNavBar.css";
import GroupTab from "./GroupTab";
import FriendsTab from "./FriendsTab";

const getStatusColor = (status) => {
	switch (status) {
		case "available":
			return "green";
		case "busy":
			return "#DC143C";
		case "away":
			return "#FDDA0D";
		default:
			return "gray";
	}
};

const ProfileNavBar = ({ profileUser }) => {
	const paramUserEmail = useParams().userEmail;
	const dispatch = useDispatch();
	const navigate = useNavigate();
	const user = useSelector((state) => state.user.user);
	const [showSettingsIcon, setShowSettingsIcon] = useState(false);

	useEffect(() => {
		if (paramUserEmail === user.email) {
			setShowSettingsIcon(true);
		} else {
			setShowSettingsIcon(false);
		}
	}, [paramUserEmail, user.email]);

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

	return (
		<nav className="profile-navbar">
			<div className="profile-section">
				<div className="profile-header">
					<div className="profile-user-picture">
						<img src={profileUser.profilePictureUrl} alt="Profile" />
					</div>
					<div className="profile-info">
						<div className="profile-name-status">
							<h3>
								{profileUser.userName}
								<span
									className="status-circle"
									style={{
										backgroundColor: getStatusColor(profileUser.onlineStatus),
									}}
								></span>
							</h3>
							{showSettingsIcon && (
								<NavLink to={`/profile/edit`}>
									<FaCog className="settings-icon" />
								</NavLink>
							)}
						</div>
						<p>{profileUser.email}</p>
					</div>
				</div>
				<div className="profile-detail-container">
					<div className="profile-detail-wrapper">
						{user.firstName && user.lastName && (
							<div className="profile-detail">
								<strong>Full name:</strong>{" "}
								<span>
									{profileUser.firstName} {profileUser.lastName}
								</span>
							</div>
						)}
						{user.biography && (
							<div className="profile-detail">
								<strong>Personal interest:</strong>{" "}
								<span>{profileUser.biography}</span>
							</div>
						)}
						{user.age && (
							<div className="profile-detail">
								<strong>Age:</strong> <span>{profileUser.age}</span>
							</div>
						)}
						{user.gender && (
							<div className="profile-detail">
								<strong>Gender:</strong> <span>{profileUser.gender}</span>
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
				<a href={`/profile/${user.email}`}>
					<FaUser />
					<span className="text-hide">My Account</span>
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
