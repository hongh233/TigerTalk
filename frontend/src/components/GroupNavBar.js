import React from "react";
import {FaHome, FaSignOutAlt, FaUserShield} from "react-icons/fa";
import "../assets/styles/GroupNavBar.css";
import GroupTab from "./GroupTab";
import {useDispatch, useSelector} from "react-redux";

const GroupNavBar = () => {
    const dispatch = useDispatch();
    const user = useSelector((state) => state.user.user);
    const handleLogOut = () => {
        localStorage.removeItem("user");
    };

    return (
        <nav className="group-navbar">
            <div className="group-links">
                <a href="/main">
                    <FaHome/>
                    <span className="text-hide">Home</span>
                </a>
                <GroupTab/>
                {user.userLevel === "admin" && (
                    <a href="/admin">
                        <FaUserShield/>
                        <span className="text-hide">Admin</span>
                    </a>
                )}
                <a href="/" onClick={handleLogOut}>
                    <FaSignOutAlt/>
                    <span className="text-hide">Logout</span>
                </a>
            </div>
        </nav>
    );
};
export default GroupNavBar;
