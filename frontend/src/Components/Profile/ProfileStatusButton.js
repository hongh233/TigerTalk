import React, { useState, useRef } from "react";
import "../../assets/styles/Components/Profile/ProfileStatusButton.css";
import { MdCheckCircle, MdRemoveCircle, MdAccessTimeFilled } from 'react-icons/md';
import { IoMdCloseCircle } from "react-icons/io";
import {updateUserProfileSetOnlineStatus} from "../../axios/UserAxios";
import { useDispatch } from "react-redux";

const ProfileStatusButton = ({ profileUser, paramUserEmail, user }) => {
    const [showStatusMenu, setShowStatusMenu] = useState(false);
    const hoverTimeout = useRef(null);
    const dispatch = useDispatch();

    const getStatusClass = (status) => {
        switch (status) {
            case "available":
                return <MdCheckCircle
                    style={{ color: '#4caf50' }}
                    className="status-adjust-animation-temp"
                />;
            case "busy":
                return <MdRemoveCircle
                    style={{ color: '#f44336' }}
                    className="status-adjust-animation-temp"
                />;
            case "away":
                return <MdAccessTimeFilled
                    style={{ color: '#ff9800' }}
                    className="status-adjust-animation-temp"
                />;
            default:
                return <IoMdCloseCircle
                    style={{ color: '#9e9e9e' }}
                    className="status-adjust-animation-temp"
                />;
        }
    };

    const getStatusText = (status) => {
        switch (status) {
            case "available":
                return "Available";
            case "busy":
                return "Busy";
            case "away":
                return "Away";
            default:
                return "Offline";
        }
    };

    const handleStatusChange = async (status) => {
        try {
            await updateUserProfileSetOnlineStatus(profileUser.email, status);
            dispatch({ type: "SET_USER", payload: { ...profileUser, onlineStatus: status } });
            profileUser.onlineStatus = status;
            setShowStatusMenu(false);
        } catch (error) {
            console.error("Error updating user status:", error);
        }
    };

    const handleMouseEnter = () => {
        clearTimeout(hoverTimeout.current);
        hoverTimeout.current = setTimeout(() => {
            setShowStatusMenu(true);
        }, 300);
    };

    const handleMouseLeave = () => {
        clearTimeout(hoverTimeout.current);
        hoverTimeout.current = setTimeout(() => {
            setShowStatusMenu(false);
        }, 300);
    };

    const handleToggleClick = () => {
        clearTimeout(hoverTimeout.current);
        setShowStatusMenu(prev => !prev);
    };

    return (
        <h2 className="profile-page-profile-name-status">

            <div className="profile-status-button-user-name">
                {profileUser.userName}
            </div>

            {(profileUser.gender !== "Don't specify") &&
                <span className="profile-user-gender">&middot; {profileUser.gender}</span>
            }

            {paramUserEmail === user.email ? (
                <span
                    className="profile-page-status-icon"
                    onMouseEnter={handleMouseEnter}
                    onMouseLeave={handleMouseLeave}
                    onClick={handleToggleClick}
                >
                    {getStatusClass(profileUser.onlineStatus)}

                    {showStatusMenu && (
                        <div
                            className={`status-menu ${showStatusMenu ? 'show' : ''}`}
                            onMouseEnter={handleMouseEnter}
                            onMouseLeave={handleMouseLeave}
                        >
                            <div onClick={() => handleStatusChange("available")}>
                                <MdCheckCircle style={{ color: '#4caf50' }} />
                                <span>&nbsp;Available</span>
                            </div>
                            <div onClick={() => handleStatusChange("busy")}>
                                <MdRemoveCircle style={{ color: '#f44336' }} />
                                <span>&nbsp;Busy</span>
                            </div>
                            <div onClick={() => handleStatusChange("away")}>
                                <MdAccessTimeFilled style={{ color: '#ff9800' }} />
                                <span>&nbsp;Away</span>
                            </div>
                            <div onClick={() => handleStatusChange("offline")}>
                                <IoMdCloseCircle style={{ color: '#9e9e9e' }} />
                                <span>&nbsp;Offline</span>
                            </div>
                        </div>
                    )}
                </span>
            ) : (
                <span
                    className="profile-page-status-icon"
                    title={getStatusText(profileUser.onlineStatus)}
                >
                    {getStatusClass(profileUser.onlineStatus)}
                </span>
            )}
        </h2>
    );
};

export default ProfileStatusButton;