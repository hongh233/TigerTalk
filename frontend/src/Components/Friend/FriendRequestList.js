import React, { useEffect, useState } from "react";
import "../../assets/styles/Components/Friend/FriendRequestList.css";
import { getAllFriendRequests } from "../../axios/Friend/FriendshipRequestAxios";
import FriendRequestComponent from "../../Components/Friend/FriendRequest";
import { useSelector } from "react-redux";

const FriendRequestList = ({ onClose }) => {
    const user = useSelector((state) => state.user.user);
    const [friendRequests, setFriendRequests] = useState([]);

    useEffect(() => {
        const fetchFriendRequests = async () => {
            if (user && user.email) {
                try {
                    const responseData = await getAllFriendRequests(user.email);
                    setFriendRequests(responseData);
                } catch (error) {
                    console.error("Failed to fetch friend requests", error);
                }
            }
        };

        fetchFriendRequests();
    }, [user]);

    return (
        <div className="friend-request-list">
            <div className="list-header">
                <h3>Friend Requests</h3>
            </div>
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
