import React, {useEffect, useState} from "react";
import "../../assets/styles/Components/Group/GroupInvitePeopleModal.css";
import {useSelector} from "react-redux";
import {getAllFriendsDTO} from "../../axios/Friend/FriendshipAxios";
import {handleAddUsersToGroupByAdmin} from "../../axios/Group/GroupAdminAxios";
import StatusIcon from "../Main/StatusIcon";

const GroupInvitePeopleModal = ({members, onClose, groupId}) => {

    const user = useSelector((state) => state.user.user);
    const [friends, setFriends] = useState([]);
    const [selectedFriends, setSelectedFriends] = useState([]);

    useEffect(() => {
        const fetchFriends = async () => {
            if (user && user.email) {
                try {
                    setFriends(await getAllFriendsDTO(user.email));
                } catch (error) {
                    console.error("Failed to fetch friends", error);
                }
            }
        };
        fetchFriends();
    }, [user]);

    const isMember = (friendEmail) => {
        return members.some(member => member.userProfileDTO.email === friendEmail);
    };

    const handleSelectFriend = (friend) => {
        if (isMember(friend.email)) {return;}
        if (selectedFriends.includes(friend)) {
            setSelectedFriends(selectedFriends.filter(f => f !== friend));
        } else {
            setSelectedFriends([...selectedFriends, friend]);
        }
    };

    const handleInvite = async () => {
        if (selectedFriends.length === 0) {
            alert("Please select at least one friend to invite.");
            return;
        }
        try {
            const friendEmails = selectedFriends.map(friend => friend.email);
            await handleAddUsersToGroupByAdmin(friendEmails, groupId);
            alert("Friends invited successfully!");
            onClose();
            window.location.reload();
        } catch (error) {
            console.error("Failed to invite friends", error);
            alert("Error inviting friends");
        }
    };




    return (
        <div className="group-invite-modal">

            <button className="group-invite-close-button" onClick={onClose}>
                &times;
            </button>

            <div className="group-invite-membership-box-with-title">
                <div className="group-friend-list-invite">
                    {friends.map((friend) => (
                        <div key={friend.email}
                             className="group-membership-item-friend-invite"
                             onClick={() => handleSelectFriend(friend)}
                             style={{ cursor: isMember(friend.email) ? "not-allowed" : "pointer" }}
                        >

                            <div className="group-membership-item-friend-header-invite">
                                <div className="common-profile-picture-and-status-icon">
                                    <img src={friend.profilePictureUrl} alt="avatar" />
                                    <StatusIcon status={friend.onlineStatus}/>
                                </div>
                                <div className="group-membership-item-friend-details-invite">
                                    <div className="userName">{friend.userName}</div>
                                    <p>{friend.email}</p>
                                </div>
                            </div>

                            <label className={`custom-checkbox ${isMember(friend.email) ? 'disabled' : ''}`}
                                   onClick={(e) => {
                                       e.stopPropagation();
                                       handleSelectFriend(friend);
                                   }}
                            >
                                <input
                                    type="checkbox"
                                    checked={selectedFriends.includes(friend)}
                                    disabled={isMember(friend.email)}
                                    onClick={(e) => e.stopPropagation()}
                                />
                                <span className="checkmark"></span>
                            </label>


                        </div>
                    ))}
                </div>
            </div>

            <div className="group-invite-button-container">
                <button onClick={handleInvite} disabled={selectedFriends.length === 0}>
                    Invite Selected Friends
                </button>
            </div>

        </div>
    );
};



export default GroupInvitePeopleModal;