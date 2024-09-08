import React from "react";
import "../../assets/styles/Components/Group/GroupMembership.css";
import {formatLocalDate} from "../../utils/formatDate";
import StatusIcon from "../Main/StatusIcon";


const GroupMembership = ({ member, handleDeleteFn, isCreator, groupMembershipId }) => {
    return (
        <div className="group-membership-item-friend">
            <div className="group-membership-item-friend-header">
                <div className="common-profile-picture-and-status-icon">
                    <a href={"/profile/" + member.userProfileDTO.email}>
                        <img src={member.userProfileDTO.profilePictureUrl} alt="avatar"/>
                        <StatusIcon status={member.userProfileDTO.onlineStatus}/>
                    </a>
                </div>
                <div className="group-membership-item-friend-details">
                    <a href={"/profile/" + member.userProfileDTO.email}>
                        {member.userProfileDTO.userName}
                    </a>
                    <p>{member.userProfileDTO.email}</p>
                    <p>Join Date: {formatLocalDate(member.joinTime)}</p>
                </div>
            </div>
            <div className="group-membership-item-friend-actions">
                {isCreator && !member.isCreator &&
                    <button className="group-membership-item-delete-button" onClick={handleDeleteFn}>
                        Delete
                    </button>
                }
                {member.isCreator &&
                    <button className="group-membership-item-owner-button">
                        Owner
                    </button>
                }
                {!isCreator && member.groupMembershipId === groupMembershipId &&
                    <button className="group-membership-item-user-button">
                        You
                    </button>
                }
            </div>
        </div>
    );
};

export default GroupMembership;
