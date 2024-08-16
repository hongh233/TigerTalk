import React, {useEffect, useState} from "react";
import NavBar from "../../Components/Main/NavBar";
import Header from "../../Components/Main/Header";
import "../../assets/styles/Pages/Friend/FriendRequestPage.css"; // Update to the new CSS file
import FriendRequestComponent from "../../Components/Friend/FriendRequestComponent";
import {useSelector} from "react-redux";
import {getAllFriendRequests} from "../../axios/FriendAxios";

const FriendRequestPage = () => {
    const user = useSelector((state) => state.user.user);
    const [friendRequests, setFriendRequests] = useState([]);

    useEffect(() => {
        const fetchFriendRequests = async () => {
            if (user && user.email) {
                try {
                    const responseData = await getAllFriendRequests(user.email);
                    setFriendRequests(responseData);
                    // dispatch({ type: "SET_FRIEND_REQUESTS", payload: response.data });
                } catch (error) {
                    console.error("Failed to fetch friend requests", error);
                }
            }
        };

        fetchFriendRequests();
    }, [user]);

    return (
        <div className="main-page">
            <Header />
            <div className="content">
                <div className="sidebar">
                    <NavBar />
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
