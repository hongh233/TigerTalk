import React from "react";
import "../../assets/styles/Components/Friend/FriendRequestList.css";
import FriendRequestComponent from "../../Components/Friend/FriendRequest";


const FriendRequestList = ({ friendRequests }) => {

    return (
        <div className="friend-request-list">
            <div className="friend-request-list-content">
                {friendRequests.length > 0 ? (
                    friendRequests.map((request) => (
                        <FriendRequestComponent
                            key={request.friendshipRequestId}
                            request={request}
                        />
                    ))
                ) : (
                    <div className="no-friend-requests">
                        <p>There is no friend request.</p>
                    </div>
                )}
            </div>
        </div>
    );
};

export default FriendRequestList;
