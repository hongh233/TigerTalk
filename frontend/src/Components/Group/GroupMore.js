import React, {useState} from "react";
import "../../assets/styles/Components/Group/GroupMore.css";
import { FaCog, FaUserMinus } from "react-icons/fa";
import {handleDeleteGroup} from "../../axios/Group/GroupAxios";
import { MdDelete } from "react-icons/md";
import { BiTransferAlt } from "react-icons/bi";
import GroupTransferOwnership from "./GroupTransferOwnership";
import { useNavigate } from 'react-router-dom';

const GroupMore = ({
       groupId,
       isCreator,
       isMember,
       leaveGroup,
       setShowSettingsModal,
       setShowMoreOptions,
       groupMembershipList,
       previousOwnerMembershipId
}) => {
    const [showTransferOwnership, setShowTransferOwnership] = useState(false);
    const navigate = useNavigate();

    const deleteGroup = async () => {
        const userConfirmed = window.confirm("Are you sure you want to delete this group? This action cannot be undone.");
        if (!userConfirmed) {
            return;
        }
        try {
            await handleDeleteGroup(groupId);
            alert("Group deleted successfully!");
            navigate('/group');
        } catch (error) {
            console.error("Failed to delete the group", error);
            alert("Error deleting group");
        }
    };

    return (
        <div className="group-more-options">
            {isCreator ? (
                <>
                    <button onClick={() => {
                        setShowSettingsModal(true);
                        setShowMoreOptions(false);
                    }}>
                        <FaCog className="group-more-options-icon-spc"/> Group Setting
                    </button>
                    <button type="button" onClick={deleteGroup}>
                        <MdDelete className="group-more-options-icon-spc"/> Delete Group
                    </button>
                    <button onClick={() => setShowTransferOwnership(true)}>
                        <BiTransferAlt className="group-more-options-icon-spc"/> Transfer Group
                    </button>
                </>
            ) : isMember ? (
                <button onClick={leaveGroup}>
                    <FaUserMinus className="group-more-options-icon-spc"/> Leave Group
                </button>
            ) : (
                <p>No additional options available</p>
            )}

            {showTransferOwnership && (
                <div className="group-transfer-ownership-modal-overlay">
                    <GroupTransferOwnership
                        groupMembershipList={groupMembershipList}
                        previousOwnerMembershipId={previousOwnerMembershipId}
                        onClose={() => setShowTransferOwnership(false)}
                    />
                </div>
            )}
        </div>
    );
};

export default GroupMore;