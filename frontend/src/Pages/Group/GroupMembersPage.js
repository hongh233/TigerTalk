import React, {useEffect, useState} from "react";
import "../../assets/styles/Pages/Group/GroupMemberPage.css";
import { handleAddUserToGroupByAdmin } from "../../axios/Group/GroupAdminAxios";
import { handleDeleteGroupMembership, handleGetGroupMembersByGroupId, handleGetMembershipID } from "../../axios/Group/GroupAxios";
import {FaUserPlus} from "react-icons/fa";
import {useParams} from "react-router-dom";
import Header from "../../Components/Main/Header";
import GroupMembership from "../../Components/Group/GroupMembership";
import SearchBar from "../../Components/Search/SearchBar";


const GroupMemberPage = () => {
    const {groupId} = useParams();
    const [members, setMembers] = useState(null);
    const [searchMember, setSearchMember] = useState(null);
    const [showAddUser, setShowAddUser] = useState(false);

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
        
                setShowAddUser(false);
            };
            handleAddMember(searchMember, groupId);
        }
    }, [searchMember, groupId]);


       const handleDeleteGroupMember = async (userEmail, groupId) => {
        try {
            const membershipID = await handleGetMembershipID(userEmail, groupId);
            await handleDeleteGroupMembership(membershipID);
            handleDeleteMember(userEmail); // Call handleDeleteMember after deletion
            window.alert("Member deleted successfully!");
        } catch (err) {
            window.alert("Failed to delete member. Please try again.");
            console.error(err);
        }
    };

    const handleDeleteMember = (userEmail) => {
        setMembers(members.filter((member) => member.userProfileDTO.email !== userEmail));
    };

    return (
        <div className="main-page">
            <Header />
            <div className="content">
                <div className="member-list-content">
                    <h3>Add new member:</h3>
                    <div
                        className="add-user-icon"
                        onClick={() => setShowAddUser(!showAddUser)}
                    >
                        <FaUserPlus/>
                    </div>
                    {showAddUser && (
                        <SearchBar
                            searchType="member"
                            setSearchMember={setSearchMember}
                            dropdownClassName="member"
                            searchBarClassName="member"
                            groupMembers={members}
                        />
                    )}
                    <h3>Existing members:</h3>
                    <div className="group-member-list">
                        {members && members.length > 0 ? (
                            members.map((member) => (
                                <GroupMembership
                                    key={member.userProfileDTO.email}
                                    user={member.userProfileDTO}
                                    userEmail={member.userProfileDTO.email}
                                    handleDeleteFn={() => handleDeleteGroupMember(member.userProfileDTO.email, groupId)}
                                />
                            ))
                        ) : (
                            <div className="no-members">
                                <p>This group has no members</p>
                            </div>
                        )}
                    </div>
                </div>
            </div>
        </div>
    );
};

export default GroupMemberPage;
