import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import Header from "../components/Header";
import NavBar from "../components/NavBar";
import Post from "../components/Post";
import PostCreation from "../components/PostCreation";
import FriendRecommendations from "../components/FriendRecommendations";
import "../assets/styles/Main.css";
import axios from "axios";
import { formatPost } from "../utils/formatPost";
const MainPage = () => {
	const user = useSelector((state) => state.user.user);
	const dispatch = useDispatch();
	const [message, setMessage] = useState("");
	const [posts, setPosts] = useState([]);
	const [reload, setReload] = useState(false);

	useEffect(() => {
		axios
			.get(`http://localhost:8085/posts/getPostForUserAndFriends/${user.email}`)
			.then((response) => {
				const transformedPosts = formatPost(response.data);
				setPosts(transformedPosts);
			})
			.catch((error) => {
				console.error("There was an error on posts!", error);
			});
	}, [user]);

	useEffect(() => {
		if (user && reload) {
			const fetchCurrentUser = async (userEmail) => {
				try {
					const response = await axios.get(
						`http://localhost:8085/api/user/getByEmail/${userEmail}`
					);
					const data = response.data;
					setReload(false);
					await dispatch({ type: "SET_USER", payload: data });
				} catch (error) {
					console.error("Error fetching profile user data:", error);
				}
			};
			fetchCurrentUser(user.email);
		}
	}, [user, dispatch, reload]);

	const addPost = async (postContent, imageURL, tags) => {
		if (!user) {
			setMessage("User profile are not successfully loaded");
			return;
		}

		const newPost = {
			userProfile: {
				email: user.email,
				UserName: user.userName,
			},
			content: postContent,
			associatedImageURL: imageURL,
		};
		
		// Save the new post to the database
		await axios
			.post("http://localhost:8085/posts/create", newPost)
			.then((response) => {
				const createdPost = response.data;
				setPosts((prevPosts) => [createdPost, ...prevPosts]);
				setReload(!reload);
			})
			.catch((error) => {
				console.error("There was an error creating the post!", error);
				setMessage("Error creating post");
			});
	};
	const handleDeletePost = (postId) => {
        setPosts(posts.filter(post => post.id !== postId));
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
						{posts.map((post) => (
							<Post key={post.id} post={post} user={user} removePost={handleDeletePost} />
						))}
					</div>
					<p>{message}</p>
				</div>
			</div>
		</div>
	);
};

export default MainPage;
