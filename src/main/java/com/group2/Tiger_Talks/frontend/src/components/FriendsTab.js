import React, {useState} from "react";
import {HiAnnotation, HiChatAlt, HiChatAlt2, HiUserGroup} from "react-icons/hi";
import {FaUserFriends, FaUserPlus} from "react-icons/fa";
import "../assets/styles/FriendsTab.css";

const FriendsTab = () => {
    const [showGroupTab, setShowGroupTab] = useState(false);

    const toggleGroupTab = () => {
        setShowGroupTab(!showGroupTab);
    };

    return (
        <div className="group-tab">
            <button onClick={toggleGroupTab} className="group-tab-button">
                <FaUserFriends/> <span className="text-hide">Friends</span>
            </button>
            <div className={`group-tab-dropdown ${showGroupTab ? "show" : ""}`}>
                <a href="/friends/friend-request-list" className="group-tab-item">
                    <FaUserPlus/>
                    <span className="text-hide">Friend request list</span>
                </a>
                <a href="/friends/friend-list" className="group-tab-item">
                    <HiUserGroup/>
                    <span className="text-hide"> View Friends</span>
                </a>
                <a href="/friends/message" className="group-tab-item">
                    <HiChatAlt/>
                    <span className="text-hide"> Message</span>
                </a>
            </div>
        </div>
    );
};

export default FriendsTab;
