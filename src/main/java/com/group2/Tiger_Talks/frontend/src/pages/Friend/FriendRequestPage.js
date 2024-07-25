import React, {useEffect, useState} from "react";
import NavBar from "../../components/NavBar";
import Header from "../../components/Header";
import "../../assets/styles/FriendRequestPage.css"; // Update to the new CSS file
import FriendRequestComponent from "../../components/FriendRequestComponent";
import {useSelector} from "react-redux";
import {getAllFriendRequests} from "../../axios/FriendAxios";

const FriendRequestPage = () => {
    const user = useSelector((state) => state.user.user);
    const [friendRequests, setFriendRequests] = useState([]);

    const [isNavVisible, setIsNavVisible] = useState(false);

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
        <div className="friend-request-list-page">
            <Header/>
            <div className="menu-toggle" onClick={() => setIsNavVisible(!isNavVisible)}>
                <div></div>
                <div></div>
                <div></div>
            </div>


			<div className={`content ${isNavVisible ? "nav-visible" : ""}`}>
                <div className={`sidebar ${isNavVisible ? "visible" : ""}`}>
                    <button className="close-btn" onClick={() => setIsNavVisible(false)}>×</button>
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
