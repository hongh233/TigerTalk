import React from "react";
import { FaHome, FaHammer, FaSignOutAlt } from "react-icons/fa";
import "../assets/styles/ProfileNavBar.css";
import GroupTab from "./GroupTab";

const ProfileNavBar = ({ user }) => {
	const handleLogOut = () => {
		localStorage.removeItem("user");
	};
	return (
		<nav className="profile-navbar">
			<div className="profile-header">
				<div className="profile-user-picture">
					{user.profilePictureUrl && (
						<img src={user.profilePictureUrl} alt="Profile" />
					)}
				</div>
				<div className="profile-info">
					<h3>
						{user.firstName} {user.lastName}
					</h3>
					<p>{user.email}</p>
				</div>
			</div>
			<div className="profile-detail-container">
				{user.biography && (
					<div className="profile-detail">
						<strong>Bio:</strong> <span>{user.biography}</span>
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
			<div className="profile-links">
				<a href="/main">
					<FaHome />
					<span className="text-hide">Home</span>
				</a>
				<a href="/profile/1">
					<FaHammer />
					<span className="text-hide">Profile Setting</span>
				</a>

				<GroupTab />
				<a href="/" onClick={handleLogOut}>
					<FaSignOutAlt />
					<span className="text-hide">Logout</span>
				</a>
			</div>
		</nav>
	);
};
export default ProfileNavBar;
