import React, {useEffect, useState} from "react";
import {useSelector} from "react-redux";
import axios from "axios";
import {useParams} from "react-router-dom";
import ProfileNavBar from "../components/ProfileNavBar";
import Post from "../components/Post";
import Header from "../components/Header";
import "../assets/styles/ProfilePage.css";

const ProfilePage = () => {
    const user = useSelector((state) => state.user.user);
    const friendRequests = useSelector(
        (state) => state.friendRequests.sentRequests
    );
    const {userEmail} = useParams();
    const [profileUser, setProfileUser] = useState(null);
    const [isFriend, setIsFriend] = useState(false);
    const [friendButtonText, setFriendButtonText] = useState("Add Friend");
    const [posts, setPosts] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            await fetchProfileUser();
            await checkAreFriends();
            updateFriendButtonText();
            // Ensure user and profileUser are defined before making the axios call
            if (user && profileUser && (profileUser.email === user.email || isFriend)) {
                axios.get(`http://localhost:8085/posts/getPostForUser/${profileUser.email}`)
                    .then((response) => {
                        setPosts(response.data);
                    })
                    .catch((err) => {
                        console.error("There was an error fetching posts!\n", err);
                    });
            }
        };

        fetchData();
    }, [userEmail, friendRequests, profileUser, isFriend]);

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
                `http://localhost:8085/friendships/areFriends/${userEmail}/${user?.email}`
            );
            setIsFriend(response.data);
            if (response.data) {
                setFriendButtonText("You are friend");
            }
        } catch (error) {
            console.error("Error checking friendship status:", error);
        }
    };

    const handleAddFriend = async () => {
        try {
            await axios.post("http://localhost:8085/friendshipRequests/send", null, {
                params: {
                    senderEmail: user?.email,
                    receiverEmail: profileUser?.email,
                },
            });

            setFriendButtonText("Friend request sent");
        } catch (error) {
            if (error.response?.data === "Friendship request has already existed between these users.") {
                setFriendButtonText("Friend request pending");
            }
            console.error("Error sending friend request:", error);
        }
    };

    const updateFriendButtonText = () => {
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
                    {profileUser && profileUser.email !== user?.email && (
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
