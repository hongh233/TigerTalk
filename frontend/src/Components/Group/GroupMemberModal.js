import React, {useEffect, useState} from "react";
import "../../assets/styles/Components/Group/GroupMemberModal.css";
import { handleAddUserToGroupByAdmin } from "../../axios/Group/GroupAdminAxios";
import { handleDeleteGroupMembership, handleGetGroupMembersByGroupId, handleGetMembershipID } from "../../axios/Group/GroupAxios";
import SearchBar from "../../Components/Search/SearchBar";
import GroupMembership from "../../Components/Group/GroupMembership";

const GroupMemberModal = ({ groupId, isCreator, groupMembershipId }) => {
    const [members, setMembers] = useState(null);
    const [searchMember, setSearchMember] = useState(null);

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
        if (searchMember) {
            const handleAddMember = async (email, groupId) => {
                try {
                    if (window.confirm("Are you sure to add this user to the group?")) {
                        await handleAddUserToGroupByAdmin(email, groupId);
                        window.alert("User successfully joined the group!");
                        setSearchMember(null);
                    }
                } catch (error) {
                    console.error(error);
                }
            };
            handleAddMember(searchMember, groupId);
        }
    }, [searchMember, groupId]);

    const handleDeleteGroupMember = async (userEmail, groupId) => {
        const userConfirmed = window.confirm(`Are you sure you want to delete ${userEmail}? This action cannot be undone!`);
        if (!userConfirmed) {
            return;
        }
        try {
            const membershipID = await handleGetMembershipID(userEmail, groupId);
            await handleDeleteGroupMembership(membershipID);
            handleDeleteMember(userEmail); // Call handleDeleteMember after deletion
            window.alert("Member deleted successfully!");
            window.location.reload();
        } catch (err) {
            window.alert("Failed to delete member. Please try again.");
            console.error(err);
        }
    };

    const handleDeleteMember = (userEmail) => {
        setMembers(members.filter((member) => member.userProfileDTO.email !== userEmail));
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
                        <h3>Add People:</h3>
                        <SearchBar searchType="member"
                                   setSearchMember={setSearchMember}
                                   dropdownClassName="member"
                                   searchBarClassName="member"
                                   groupMembers={members}
                        />
                    </div>
                </div>


            </div>
        </div>
    );
};

export default GroupMemberModal;