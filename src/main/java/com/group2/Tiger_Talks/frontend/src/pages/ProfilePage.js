import React, {useEffect, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import axios from "axios";
import {useParams} from "react-router-dom";
import ProfileNavBar from "../components/ProfileNavBar";
import Post from "../components/Post";
import Header from "../components/Header";
import "../assets/styles/ProfilePage.css";

const posts = [
    {
        id: 1,
        userName: "User",
        time: "2 hours ago",
        content:
            "This is an example post content to demonstrate the post layout. It can include text, images, and other media.",
    },
    {
        id: 2,
        userName: "User",
        time: "1 day ago",
        content:
            "Another example post to show how multiple posts would look on the profile page.",
    },
];

const ProfilePage = () => {
    const user = useSelector((state) => state.user.user);
    const friendRequests = useSelector(
        (state) => state.friendRequests.sentRequests
    );
    const dispatch = useDispatch();
    const {userEmail} = useParams();
    const [profileUser, setProfileUser] = useState(null);
    const [isFriend, setIsFriend] = useState(false);
    const [friendButtonText, setFriendButtonText] = useState("Add Friend");

    useEffect(() => {
        fetchProfileUser();
        checkAreFriends();
        updateFriendButtonText();
    }, [userEmail, friendRequests]);

    const fetchProfileUser = async () => {
        try {
            const response = await axios.get(
                `http://localhost:8085/api/user/getByEmail/${userEmail}`
            );
            const data = response.data;
            setProfileUser(data);
        } catch (error) {
            console.error("Error fetching profile user data:", error);
        }
    };

    const checkAreFriends = async () => {
        try {
            const response = await axios.get(
                `http://localhost:8085/friendships/areFriends/${userEmail}/${user.email}`
            );
            setIsFriend(response.data);
            if (response.data) {
                setFriendButtonText("You are friend");
            }
        } catch (error) {
            console.error("Error fetching profile user data:", error);
        }
    };

    const handleAddFriend = async () => {
        try {
            await axios.post("http://localhost:8085/friendshipRequests/send", null, {
                params: {
                    senderEmail: user.email,
                    receiverEmail: profileUser.email,
                },
            });

            setFriendButtonText("Friend request sent");
            // dispatch(sendFriendRequest(user.email, profileUser.email));
        } catch (error) {
            if (
                error.response.data ===
                "Friendship request has already existed between these users."
            ) {
                setFriendButtonText("Friend request pending");
            }
            console.error("Error sending friend request:", error);
        }
    };

    const updateFriendButtonText = () => {
        // const sentRequest = friendRequests.find(
        // 	(req) => req.senderEmail === user.email && req.receiverEmail === userEmail
        // );
        // const receivedRequest = friendRequests.find(
        // 	(req) => req.senderEmail === userEmail && req.receiverEmail === user.email
        // );

        // if (sentRequest) {
        // 	setFriendButtonText("Friend request sent");
        // } else if (receivedRequest) {
        // 	setFriendButtonText("Friend request pending");
        // } else {
        // 	setFriendButtonText("Add Friend");
        // }
        setFriendButtonText("Add Friend");
    };

    return (
        <div className="profile-page">
            <Header/>
            <div className="profile-content">
                <div className="profile-nav">
                    {profileUser && <ProfileNavBar user={profileUser}/>}
                </div>
                <div className="profile-main-content">
                    {profileUser && profileUser.email !== user.email && (
                        <button
                            className={`friend-button-${isFriend ? "friend" : "not-friend"}`}
                            onClick={handleAddFriend}
                        >
                            {friendButtonText}
                        </button>
                    )}
                    {posts.map((post) => (
                        <Post
                            key={post.id}
                            post={{...post, userName: profileUser?.name}}
                        />
                    ))}
                </div>
            </div>
        </div>
    );
};

export default ProfilePage;
