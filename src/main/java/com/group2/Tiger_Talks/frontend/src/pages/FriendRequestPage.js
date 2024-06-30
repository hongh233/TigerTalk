import React, {useEffect, useState} from "react";
import axios from "axios";
import NavBar from "../components/NavBar";
import Header from "../components/Header";
import "../assets/styles/FriendRequestPage.css"; // Update to the new CSS file
import FriendRequestComponent from "../components/FriendRequestComponent";
import {useDispatch, useSelector} from "react-redux";

const FriendRequestPage = () => {
    const user = useSelector((state) => state.user.user);
    const [friendRequests, setFriendRequests] = useState([]);
    const dispatch = useDispatch();

    useEffect(() => {
        const fetchFriendRequests = async () => {
            if (user && user.email) {
                try {
                    const response = await axios.get(
                        `http://localhost:8085/friendshipRequests/${user.email}`
                    );
                    setFriendRequests(response.data);
                    // dispatch({ type: "SET_FRIEND_REQUESTS", payload: response.data });
                } catch (error) {
                    console.error("Failed to fetch friend requests", error);
                }
            }
        };

        fetchFriendRequests();
    }, [user]);

    return (
        <div className="friend-request-list-page">
            <Header/>
            <div className="content">
                <div className="friend-request-list-nav">
                    <NavBar/>
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
                            <p>You have no friend requests</p>
                        </div>
                    )}
                </div>
            </div>
        </div>
    );
};

export default FriendRequestPage;
