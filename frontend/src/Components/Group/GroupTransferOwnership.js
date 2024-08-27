import React, { useState } from "react";
import "../../assets/styles/Components/Group/GroupTransferOwnership.css";
import { transferGroupOwnership } from "../../axios/Group/GroupAxios";


const GroupTransferOwnership = ({ groupMembershipList, previousOwnerMembershipId, onClose }) => {
    const [selectedMember, setSelectedMember] = useState(null);

    const handleTransfer = async () => {
        if (!selectedMember) {
            alert("Please select a member to transfer ownership.");
            return;
        }
        const userConfirmed = window.confirm(`Are you sure you want to transfer group ownership to ${selectedMember.userProfileDTO.userName}?`);
        if (!userConfirmed) {
            return;
        }
        try {
            await transferGroupOwnership(previousOwnerMembershipId, selectedMember.groupMembershipId);
            alert("Group ownership transferred successfully!");
            onClose();
            window.location.reload();
        } catch (error) {
            console.error("Failed to transfer group ownership", error);
            alert("Error transferring group ownership");
        }

    };

    return (
        <div className="group-transfer-ownership-modal">
            <button className="group-transfer-ownership-close-button" onClick={onClose}>&times;</button>

            <div className="group-transfer-ownership-membership-box-with-title">
                <div className="group-member-list">
                    {groupMembershipList
                        .filter(member => member.groupMembershipId !== previousOwnerMembershipId)
                        .map((member) => (
                            <div
                                key={member.userProfileDTO.email}
                                onClick={() => setSelectedMember(member)}
                                className={`group-membership-item-friend ${selectedMember?.groupMembershipId === member.groupMembershipId ? 'selected' : ''}`}
                            >
                                <div className="group-membership-item-friend-header">
                                    <div className="group-membership-item-friend-picture">
                                        <img src={member.userProfileDTO.profilePictureUrl} alt="avatar" />
                                    </div>
                                    <div className="group-membership-item-friend-details">
                                        <a href={`/profile/${member.userProfileDTO.email}`}>{member.userProfileDTO.userName}</a>
                                        <p>Email: {member.userProfileDTO.email}</p>
                                    </div>
                                </div>
                            </div>
                        ))}
                </div>
            </div>

            <div className="group-transfer-ownership-button-container">
                <button onClick={handleTransfer} disabled={!selectedMember}>
                    Transfer Group Ownership
                </button>
            </div>

        </div>
    );
};

export default GroupTransferOwnership;