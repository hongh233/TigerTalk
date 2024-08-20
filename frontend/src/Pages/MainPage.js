import React, { useEffect, useState } from "react";
import "../assets/styles/Pages/MainPage.css";
import {createPost, fetchPosts} from "../axios/Post/PostAxios";
import {getCurrentUser} from "../axios/UserAxios";
import { useDispatch, useSelector } from "react-redux";
import Header from "../Components/Main/Header";
import Post from "../Components/Post/Post";
import PostCreation from "../Components/Post/PostCreation";
import FriendRecommendations from "../Components/Friend/FriendRecommendations";


const MainPage = () => {
	const user = useSelector((state) => state.user.user);
	const dispatch = useDispatch();
	const [message, setMessage] = useState("");
	const [posts, setPosts] = useState([]);
	const [reload, setReload] = useState(false);

	useEffect(() => {
		if (user) {
			fetchPosts(user.email)
				.then(transformedPosts => setPosts(transformedPosts))
				.catch(error => setMessage("Error fetching posts"));
		}
	}, [user]);

	useEffect(() => {
		if (user && reload) {
			getCurrentUser(user.email)
				.then(data => {
					setReload(false);
					dispatch({ type: "SET_USER", payload: data });
				})
				.catch(error => console.error("Error fetching profile user data:", error));
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
		try {
			const createdPost = await createPost(newPost);
			setPosts((prevPosts) => [createdPost, ...prevPosts]);
			setReload(!reload);
		} catch (error) {
			setMessage("Error creating post");
		}
	};
	const handleDeletePost = (postId) => {
        setPosts(posts.filter(post => post.id !== postId));
    };

	return (
		<div className="main-page">
			<Header />
			<div className="content">
				<div className="main-content">
					<div className="post-creation-section">
						<PostCreation addPost={addPost} />
					</div>
					<FriendRecommendations />
					<div className="post-list">
						{posts.map((post) => (<Post key={post.id} post={post} user={user} removePost={handleDeletePost} />))}
					</div>
					<p>{message}</p>
				</div>
			</div>
		</div>
	);
};

export default MainPage;
