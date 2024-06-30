import React, { useEffect, useState } from "react";
import { useSelector, useDispatch } from "react-redux";
import Header from "../components/Header";
import NavBar from "../components/NavBar";
import Post from "../components/Post";
import PostCreation from "../components/PostCreation";
import FriendRecommendations from "../components/FriendRecommendations";
import "../assets/styles/Main.css";
import axios from "axios";

const MainPage = () => {
	const user = useSelector((state) => state.user.user);
	const dispatch = useDispatch();
	const [message, setMessage] = useState("");
	const [posts, setPosts] = useState([]);
	const [reload, setReload] = useState(false);
	const fetchCurrentUser = async (userEmail) => {
		try {
			const response = await axios.get(
				`http://localhost:8085/api/user/getByEmail/${userEmail}`
			);
			const data = response.data;
			dispatch({ type: "SET_USER", payload: data });
		} catch (error) {
			console.error("Error fetching profile user data:", error);
		}
	};
	useEffect(() => {
		if (user) {
			fetchCurrentUser(user.email);
		}
	}, []);
	useEffect(() => {
		axios
			.get(`http://localhost:8085/posts/getPostForUserAndFriends/${user.email}`)
			.then((response) => {
				const transformedPosts = response.data.map((post) => ({
					id: post.postId,
					content: post.content,
					timestamp: post.timestamp,
					email: post.email,
					userProfileUserName: post.userProfileUserName,
					likes: post.numOfLike,
					comments: [],
					profileProfileURL: post.profileProfileURL,
				}));

				setPosts(transformedPosts);
			})
			.catch((error) => {
				console.error("There was an error on posts!", error);
			});
	}, [user, reload]);

	const addPost = (postContent, tags) => {
		if (!user) {
			setMessage("User profile are not successfully loaded");
			return;
		}

		const newPost = {
			content: postContent,
			userProfile: {
				email: user.email,
				UserName: user.userName,
			},
			UserName: user.userName,
			timestamp: new Date().toISOString(),
			likes: 0,
			comments: [],
		};

		// Save the new post to the database
		axios
			.post("http://localhost:8085/posts/create", newPost)
			.then((response) => {
				setPosts([newPost, ...posts]);
				setReload(!reload);
			})
			.catch((error) => {
				console.error("There was an error creating the post!", error);
				setMessage("Error creating post");
			});
	};

	return (
		<div className="main-page">
			<Header />
			<div className="content">
				<div className="sidebar">
					<NavBar />
				</div>
				<div className="main-content">
					<div className="post-creation-section">
						<PostCreation addPost={addPost} />
					</div>
					<FriendRecommendations />
					<div className="post-list">
						{posts.map((post, index) => (
							<Post key={index} post={post} />
						))}
					</div>
					<p>{message}</p>
				</div>
			</div>
		</div>
	);
};

export default MainPage;
