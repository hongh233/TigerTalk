import React from 'react';
import "../../assets/styles/Components/Main/Navbar.css";
import {useDispatch, useSelector} from "react-redux";
import {useNavigate} from "react-router-dom";
import StatusIcon from "./StatusIcon";
import {BsChatDotsFill} from "react-icons/bs";
import {FaUserGroup, FaUserLarge} from "react-icons/fa6";
import {MdAccountCircle, MdAdminPanelSettings, MdOutlineSecurity} from "react-icons/md";
import {FaSignOutAlt} from "react-icons/fa";
import {userLogout} from "../../axios/Authentication/LoginAxios";
import {IoHomeSharp} from "react-icons/io5";

const NavBar = () => {
    const user = useSelector((state) => state.user.user);
    const dispatch = useDispatch();
    const navigate = useNavigate();


    const handleLogOut = async () => {
        try {
            const response = await userLogout(user.email);
            if (response.status === 200) {
                dispatch({ type: "SET_USER", payload: null });
                navigate("/");
            }
        } catch (error) {
            console.error("Failed to logout", error);
        }
    };


    return (
        <div className="navbar">

            <div className="navbar-profilePicture-onlineStatus-username-email-box" onClick={() => navigate(`/profile/${user.email}`)}>
                <div className="navbar-profilePicture-onlineStatus-box">
                    <img src={user.profilePictureUrl} alt="user profile"/>
                    <StatusIcon status={user.onlineStatus}/>
                </div>
                <div className="navbar-username-email-box">
                    <div id="userName">{user.userName}</div>
                    <div id="email">{user.email}</div>
                </div>
            </div>

            <hr />

            <div className="navbar-user-image-button-list" onClick={() => navigate("/main")}>
                <IoHomeSharp />Home
            </div>

            <div className="navbar-user-image-button-list" onClick={() => navigate("/friends/message")}>
                <BsChatDotsFill />Chat
            </div>

            <div className="navbar-user-image-button-list" onClick={() => navigate("/friends/friend-list")}>
                <FaUserLarge />Friend
            </div>

            <div className="navbar-user-image-button-list" onClick={() => navigate("/group")}>
                <FaUserGroup />Group
            </div>

            <hr />

            <div className="navbar-user-image-button-list" onClick={() => navigate(`/profile/${user.email}`)}>
                <MdAccountCircle /> My Profile
            </div>

            <div className="navbar-user-image-button-list" onClick={() => navigate("/security")}>
                <MdOutlineSecurity /> Security
            </div>

            {user.userLevel === "admin" && (
                <div className="navbar-user-image-button-list" onClick={() => navigate("/admin")}>
                    <MdAdminPanelSettings /> Admin
                </div>
            )}

            <div className="navbar-user-image-button-list" onClick={handleLogOut}>
                <FaSignOutAlt /> Logout
            </div>


        </div>
    );
};

export default NavBar;