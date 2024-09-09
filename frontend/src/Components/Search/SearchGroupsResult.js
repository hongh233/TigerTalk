import React from "react";
import "../../assets/styles/Components/Search/SearchGroupsResult.css";
import { FaLock, FaLockOpen } from "react-icons/fa";
import { useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";
import {handleJoinGroup} from "../../axios/Group/GroupAxios";


const SearchGroupsResult = ({ searchedGroups }) => {

    const user = useSelector((state) => state.user.user);
    const navigate = useNavigate();

    const isMember = (group) => {
        return group.groupMemberList.some(member => member.userProfileDTO.email === user.email);
    };

    const handleJoined = (e) => {
        e.stopPropagation();
    };

    const handleJoinGroupClick = async (e, groupId) => {
        e.stopPropagation();
        try {
            const confirmation = window.confirm("Are you sure you want to join this group?");
            if (confirmation) {
                await handleJoinGroup(user.email, groupId);
                window.alert("Successfully joined the group!");
                window.location.reload();
            }
        } catch (error) {
            console.error("Failed to join group:", error);
        }
    };

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

                    <div className="search-groups-group-action-button">
                        {isMember(group) ? (
                            <button className="group-button joined" onClick={handleJoined}>Joined</button>
                        ) : (
                            <button className="group-button join"
                                    onClick={(e) => handleJoinGroupClick(e, group.groupId)}>
                                Join Group
                            </button>
                        )}
                    </div>

                </div>
            ))}
        </div>
    );
};

export default SearchGroupsResult;
