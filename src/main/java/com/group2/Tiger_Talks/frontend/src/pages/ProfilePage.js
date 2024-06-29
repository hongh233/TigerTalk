import React, {useEffect, useState} from "react";

import {useSelector} from "react-redux";
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
    const {userEmail} = useParams();
    const [profileUser, setProfileUser] = useState(null);
    const [isFriend, setIsFriend] = useState(false);
    const [friendButtonText, setFriendButtonText] = useState("Add Friend");

    useEffect(() => {
        console.log(user);
        const fetchProfileUser = async () => {
            try {
                const response = await axios.get(
                    `http://localhost:8085/api/user/getByEmail/${userEmail}`
                );
                const data = response.data;
                setProfileUser(data);

                // Check if the current user is friends with the profile user
                setIsFriend(user.friends.includes(data.email));
            } catch (error) {
                console.error("Error fetching profile user data:", error);
            }
        };

        fetchProfileUser();
    }, [userEmail, user.friends]);

    const handleAddFriend = async () => {
        try {
            await axios.post("http://localhost:8085/friendshipRequests/send", null, {
                params: {
                    senderEmail: user.email,
                    receiverEmail: profileUser.email,
                },
            });
            setFriendButtonText("Friend request sent");
        } catch (error) {
            console.error("Error sending friend request:", error);
        }
    };

    const handleRemoveFriend = () => {
        // Remove friend logic here
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
                            className={`friend-button ${isFriend ? "friend" : "not-friend"}`}
                            onClick={isFriend ? handleRemoveFriend : handleAddFriend}
                            onMouseOver={
                                isFriend
                                    ? () => console.log("Hovering over friend button")
                                    : null
                            }
                        >
                            {isFriend ? "Remove Friend" : friendButtonText}
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
