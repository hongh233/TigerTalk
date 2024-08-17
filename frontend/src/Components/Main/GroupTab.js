import React, {useState} from "react";
// Icon:
import {HiAcademicCap, HiPlus, } from "react-icons/hi";
import {FaComments} from "react-icons/fa";
// CSS:
import "../../assets/styles/Components/Group/GroupTab.css";


const GroupTab = () => {
    const [showGroupTab, setShowGroupTab] = useState(false);

    const toggleGroupTab = () => {
        setShowGroupTab(!showGroupTab);
    };

    return (
        <div className="group-tab">
            <button onClick={toggleGroupTab} className="group-tab-button">
                <HiAcademicCap/> <span className="text-hide">Groups</span>
            </button>
            <div className={`group-tab-dropdown ${showGroupTab ? "show" : ""}`}>
                <a href="/group/creategroup/" className="group-tab-item">
                    <HiPlus/> <span className="text-hide">Create a new group</span>
                </a>
                <a href="/group" className="group-tab-item">
                    <FaComments/>
                    <span className="text-hide"> View groups</span>
                </a>
            </div>
        </div>
    );
};

export default GroupTab;
