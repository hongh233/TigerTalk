import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import axios from "axios";
import { useParams } from "react-router-dom";
import ProfileNavBar from "../components/ProfileNavBar";
import Post from "../components/Post";
import Header from "../components/Header";
import "../assets/styles/ProfilePage.css";
import { formatPost } from "./../utils/formatPost";
const ProfilePage = () => {
	const user = useSelector((state) => state.user.user);
	const dispatch = useDispatch();
	const { userEmail } = useParams();
	const [profileUser, setProfileUser] = useState(null);
	const [isFriend, setIsFriend] = useState(false);
	const [posts, setPosts] = useState([]);
	const [friendButtonText, setFriendButtonText] = useState("Add Friend");
	const [message, setMessage] = useState("");

    useEffect(() => {
        if (user?.email && userEmail) {
            const fetchCurrentUser = async (email) => {
                try {
                    const response = await axios.get(
                        `http://localhost:8085/api/user/getByEmail/${email}`
                    );
                    const data = response.data;
                    dispatch({type: "SET_USER", payload: data});
                } catch (error) {
                    console.error("Error fetching current user data:", error);
                }
            };
        
            const fetchProfileUser = async (email) => {
                try {
                    const response = await axios.get(
                        `http://localhost:8085/api/user/getByEmail/${email}`
                    );
                    const data = response.data;
                    setProfileUser(data);
                } catch (error) {
                    console.error("Error fetching profile user data:", error);
                }
            };
            fetchCurrentUser(user.email);
            fetchProfileUser(userEmail);
        }
    }, [user?.email, userEmail,dispatch]);

    useEffect(() => {
        if (profileUser && user) {
            const checkAreFriends = async () => {
                try {
                    const response = await axios.get(
                        `http://localhost:8085/friendships/areFriends/${userEmail}/${user.email}`
                    );
                    setIsFriend(response.data);
                    if (response.data) {
                        setFriendButtonText("You are friends");
                    }
                } catch (error) {
                    console.error("Error checking friendship status:", error);
                }
            };
        
            checkAreFriends();
        }
    }, [profileUser, user,userEmail]);

	useEffect(() => {
		if (profileUser && user) {
			if (profileUser.email === user.email || isFriend) {
				fetchPosts(profileUser.email);
				setMessage("");
			} else {
				setMessage("You are not friends so you can't see this person's posts.");
			}
		}
	}, [profileUser, user, isFriend]);


	const fetchPosts = async (email) => {
		try {
			const response = await axios.get(
				`http://localhost:8085/posts/getPostForUser/${email}`
			);
			const transformedPosts = formatPost(response.data);
			setPosts(transformedPosts);
		} catch (err) {
			console.error("There was an error fetching posts!", err);
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

	const handleDeletePost = (postId) => {
        setPosts(posts.filter(post => post.id !== postId));
    };
	return (
		<div className="profile-page">
			<Header />
			<div className="profile-content">
				<div className="profile-nav">
					{profileUser && <ProfileNavBar profileUser={profileUser} />}
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
					{message.length > 0 ? (
						<p>{message}</p>
					) : (
						posts.map((post) => <Post key={post.id} post={post} user={user} removePost={handleDeletePost} />)
					)}
				</div>
			</div>
		</div>
	);
};

export default ProfilePage;
