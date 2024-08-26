import React from "react";
import "../../assets/styles/Components/Group/GroupMembership.css";


const GroupMembership = ({ user, handleDeleteFn  }) => {


    return (
        <div className="group-membership-item-friend">
            <div className="group-membership-item-friend-header">
                <div className="group-membership-item-friend-picture"><img src={user.profilePictureUrl} alt="avatar" /></div>
                <div className="group-membership-item-friend-details"><a href={"/profile/" + user.email}>{user.userName}</a><p>Email: {user.email}</p></div>
            </div>
            <div className="group-membership-item-friend-actions">
                <button className="group-membership-item-delete-button" onClick={handleDeleteFn}>Delete</button>
            </div>
        </div>
    );
};

export default GroupMembership;
