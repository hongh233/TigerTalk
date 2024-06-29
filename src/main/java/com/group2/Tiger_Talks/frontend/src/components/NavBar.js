import React from "react";
import {useSelector} from "react-redux";
import {NavLink} from "react-router-dom";
import {FaHome, FaSignOutAlt, FaUser, FaUserShield} from "react-icons/fa";
import GroupTab from "./GroupTab";
import FriendsTab from "./FriendsTab";
import "../assets/styles/NavBar.css";

const NavBar = () => {
    const user = useSelector((state) => state.user.user);
    const handleLogOut = () => {
        localStorage.removeItem("user");
    };

    return (
        <nav className="navbar">
            <NavLink to="/main">
                <FaHome/>
                <span className="text-hide">Home</span>
            </NavLink>
            <GroupTab/>
            <FriendsTab/>
            <NavLink to={`/profile/${user.email}`}>
                <FaUser/>
                <span className="text-hide">Account</span>
            </NavLink>
            {user.userLevel === "admin" && (
                <NavLink to="/admin">
                    <FaUserShield/> <span className="text-hide">Admin</span>
                </NavLink>
            )}
            <NavLink to="/" onClick={handleLogOut}>
                <FaSignOutAlt/> <span className="text-hide">Logout</span>
            </NavLink>
        </nav>
    );
};
export default NavBar;
