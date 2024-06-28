import React, { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import Header from '../components/Header';
import NavBar from '../components/NavBar';
import Post from '../components/Post';
import PostCreation from '../components/PostCreation';
import '../assets/styles/Main.css';
import axios from 'axios';

const MainPage = () => {
    const location = useLocation();
    const [message, setMessage] = useState('');
    const [posts, setPosts] = useState([]);
    const userProfile = location.state?.userProfile;
    
    useEffect(() => {
        if (userProfile) {
            axios.get(`http://localhost:8085/posts/getPostForUserAndFriends/${userProfile.email}`)
                .then(response => {
                    const transformedPosts = response.data.map(post => ({
                        id: post.postId,
                        content: post.content,
                        timestamp: post.timestamp,
                        userProfileUserName: post.userProfileUserName,
                        likes: post.numOfLike,
                        comments: []
                    }));
                    console.log('Transformed Posts:', transformedPosts);
                    setPosts(transformedPosts);
                })
                .catch(error => {
                    console.error('There was an error on posts!', error);
                });
        }
    }, [userProfile]);

    const addPost = (postContent, tags) => {
        if (!userProfile) {
            setMessage('User profile are not successfully loaded');
            return;
        }

        const newPost = {
            content: postContent,
            userProfile: {
                email: userProfile.email,
                UserName: userProfile.UserName,
            },
            UserName: userProfile.UserName,
            timestamp: new Date().toISOString(),
            likes: 0,
            comments: []
        };

        // Save the new post to the database
        axios.post('http://localhost:8085/posts/create', newPost)
            .then(response => {
                setPosts([newPost, ...posts]);
                setMessage('Post are created successfully');
            })
            .catch(error => {
                console.error('There was an error creating the post!', error);
                setMessage('Error creating post');
            });
            
    };

    if (!userProfile) {
        return <div>Loading...</div>;
    }

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