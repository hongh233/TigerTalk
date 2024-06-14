import React, { useState } from 'react';
import { HiAcademicCap, HiUserGroup, HiPlus } from "react-icons/hi";
import '../assets/styles/GroupTab.css';

const GroupTab = () => {
  const [showGroupTab, setShowGroupTab] = useState(false);

  const toggleGroupTab = () => {
    setShowGroupTab(!showGroupTab);
  };

  return (
    <div className="group-tab">
      <button onClick={toggleGroupTab} className="group-tab-button">
        <HiAcademicCap /> Groups
      </button>
      <div className={`group-tab-dropdown ${showGroupTab ? 'show' : ''}`}>
        <a href="/group/creategroup/"className='group-tab-item'><HiPlus/>Create a new group</a>
        <a href="/group"className='group-tab-item'><HiUserGroup/> View groups</a>
      </div>
    </div>
  );
};

export default GroupTab;
