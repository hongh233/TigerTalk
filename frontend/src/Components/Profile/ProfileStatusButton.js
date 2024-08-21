import React, { useState, useRef } from "react";
import "../../assets/styles/Components/Profile/ProfileStatusButton.css";
import { MdCheckCircle, MdRemoveCircle, MdAccessTimeFilled } from 'react-icons/md';
import { IoMdCloseCircle } from "react-icons/io";
import { updateUser } from "../../axios/UserAxios";
import { useDispatch } from "react-redux";

const ProfileStatusButton = ({ profileUser, paramUserEmail, user }) => {
    const [showStatusMenu, setShowStatusMenu] = useState(false);
    const [currentStatus, setCurrentStatus] = useState(user.onlineStatus || "offline");
    const hoverTimeout = useRef(null);
    const dispatch = useDispatch();

    const getStatusClass = (status) => {
        switch (status) {
            case "available":
                return <MdCheckCircle style={{ color: '#4caf50' }} />;
            case "busy":
                return <MdRemoveCircle style={{ color: '#f44336' }} />;
            case "away":
                return <MdAccessTimeFilled style={{ color: '#ff9800' }} />;
            default:
                return <IoMdCloseCircle style={{ color: '#9e9e9e' }} />;
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
        setCurrentStatus(status);
        setShowStatusMenu(false);

        const updatedUser = { ...profileUser, onlineStatus: status };
        try {
            const responseData = await updateUser(updatedUser);
            dispatch({ type: "SET_USER", payload: responseData });
            window.location.reload();
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
            <div className="profile-status-button-user-name">{profileUser.userName}</div>
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
                                <MdCheckCircle style={{ color: '#4caf50' }} />&nbsp;Available
                            </div>
                            <div onClick={() => handleStatusChange("busy")}>
                                <MdRemoveCircle style={{ color: '#f44336' }} />&nbsp;Busy
                            </div>
                            <div onClick={() => handleStatusChange("away")}>
                                <MdAccessTimeFilled style={{ color: '#ff9800' }} />&nbsp;Away
                            </div>
                            <div onClick={() => handleStatusChange("offline")}>
                                <IoMdCloseCircle style={{ color: '#9e9e9e' }} />&nbsp;Offline
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