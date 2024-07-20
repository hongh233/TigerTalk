import React, {useEffect, useState} from "react";
import {useSelector} from "react-redux";
import {useParams} from "react-router-dom";
import NavBar from "../components/NavBar";
import Header from "../components/Header";
import "../assets/styles/GroupMemberPage.css";
import UserComponent from "../components/UserComponent";
import SearchBar from "../components/SearchBar";
import {FaUserPlus} from "react-icons/fa";
import {
    handleAddUserToGroupByAdmin,
    handleDeleteGroupMembership,
    handleGetGroupMembersByGroupId, handleGetMembershipID,
} from "./../axios/GroupAxios";

const GroupMemberPage = () => {
    const user = useSelector((state) => state.user.user);
    const {groupId} = useParams();
    const [members, setMembers] = useState(null);
    const [searchMember, setSearchMember] = useState(null);
    const [showAddUser, setShowAddUser] = useState(false);

    useEffect(() => {
        if (groupId) {
            getAllMembers();
            console.log(members);
        }
        if (searchMember) {
            handleAddMember(searchMember, groupId);
        }
    }, [searchMember, groupId]);
    const handleDeleteMember = (id) => {
        setMembers(members.filter((member) => member.id !== id));
    };

    const getAllMembers = async () => {
        try {
            const data = await handleGetGroupMembersByGroupId(groupId);
            setMembers(data);
        } catch (error) {
            console.error(error);
        }
    };

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

    const handleDeleteGroupMember = async (userEmail, groupId) => {
        try {
            const membershipID = await handleGetMembershipID(userEmail, groupId);
            handleDeleteGroupMembership(membershipID);
        }catch (err) {
            console.error(err)
        }
    }

    return (
        <div className="member-list-page">
            <Header/>
            <div className="content">
                <div className="member-list-nav">
                    <NavBar/>
                </div>
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
                                <UserComponent
                                    key={member.userProfileDTO.email}
                                    user={member.userProfileDTO}
                                    userEmail={member.userProfileDTO.email}
                                    onDelete={handleDeleteMember}
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
