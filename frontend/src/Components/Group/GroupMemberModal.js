import React, {useEffect, useState} from "react";
import "../../assets/styles/Components/Group/GroupMemberModal.css";
import { handleDeleteGroupMembership, handleGetGroupMembersByGroupId, handleGetMembershipID } from "../../axios/Group/GroupAxios";
import GroupMembership from "../../Components/Group/GroupMembership";
import GroupInvitePeopleModal from "./GroupInvitePeopleModal";

const GroupMemberModal = ({ groupId, isCreator, groupMembershipId }) => {
    const [members, setMembers] = useState(null);
    const [showInviteModal, setShowInviteModal] = useState(false);

    useEffect(() => {
        if (groupId) {
            const getAllMembers = async () => {
                try {
                    const data = await handleGetGroupMembersByGroupId(groupId);
                    setMembers(data);
                } catch (error) {
                    console.error(error);
                }
            };
            getAllMembers();
        }

    }, [groupId]);

    const handleDeleteGroupMember = async (userEmail, groupId) => {
        const userConfirmed = window.confirm(`Are you sure you want to delete ${userEmail}? This action cannot be undone!`);
        if (!userConfirmed) {
            return;
        }
        try {
            const membershipID = await handleGetMembershipID(userEmail, groupId);
            await handleDeleteGroupMembership(membershipID);
            setMembers(members.filter((member) => member.userProfileDTO.email !== userEmail));
            window.alert("Member deleted successfully!");
            window.location.reload();
        } catch (err) {
            window.alert("Failed to delete member. Please try again.");
            console.error(err);
        }
    };


    return (
        <div className="group-member-modal-overlay">
            <div className="group-member-modal-content">
                <div className="group-member-member-list-content">

                    <div className="group-member-group-member-list">
                        {members && members.length > 0 ? (
                            members.map((member) => (
                                <GroupMembership
                                    member={member}
                                    handleDeleteFn={() => handleDeleteGroupMember(member.userProfileDTO.email, groupId)}
                                    isCreator={isCreator}
                                    groupMembershipId={groupMembershipId}
                                />
                            ))
                        ) : (
                            <div className="group-member-no-members"><p>This group has no members</p></div>
                        )}
                    </div>

                    <hr/>

                    <div className="group-member-fixed-add">
                        <button onClick={() => setShowInviteModal(true)}>
                            Invite People
                        </button>
                    </div>

                </div>

                {showInviteModal && (
                    <div className="group-invite-modal-overlay">
                        <GroupInvitePeopleModal
                            members={members}
                            groupId={groupId}
                            onClose={() => setShowInviteModal(false)} // Close modal handler
                        />
                    </div>
                )}

            </div>
        </div>
    );
};

export default GroupMemberModal;