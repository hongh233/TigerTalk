import React from "react";
import "../../assets/styles/Components/Search/SearchGroupsResult.css";
import { FaLock, FaLockOpen } from "react-icons/fa";
import { useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";


const SearchGroupsResult = ({ searchedGroups }) => {

    const navigate = useNavigate();

    return (
        <div className="search-groups-group">
            {searchedGroups.map((group) => (
                <div className="search-groups-group-header"
                     key={group.groupId}
                     onClick={(groupId) => {navigate(`/group/viewgroup/${group.groupId}`)}}
                >

                    <div className="search-groups-one-group-background-image">
                        <img src={group.groupImg} alt="Group cover" />
                    </div>

                    <div className="search-groups-group-creator-details">
                        {group.groupName} {group.isPrivate ? <FaLock /> : <FaLockOpen />}
                    </div>

                </div>

            ))}

        </div>
    );
};

export default SearchGroupsResult;
