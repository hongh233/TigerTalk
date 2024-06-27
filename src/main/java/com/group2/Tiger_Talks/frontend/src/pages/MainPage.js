import React, {useEffect, useState} from 'react';
import ApiService from '../services/ApiService';
import Header from '../components/Header';
import NavBar from '../components/NavBar';
import Post from '../components/Post';
import PostCreation from '../components/PostCreation';
import '../assets/styles/Main.css';
import { useNavigate } from 'react-router-dom';

const examplePost = {
    likes: 0,
    userName: 'User',
    time: '2 hours ago',
    content: 'Lorem ipsum, This is dummy text to show the content of a post, might be text, or image, of video'
};


const MainPage = () => {

    const [message, setMessage] = useState('');
    const [posts, setPosts] = useState([]);

    useEffect(() => {
        ApiService.getHello().then(response => {
            setMessage(response.data);
        }).catch(error => {
            console.error('There was an error!', error);
        });
    }, []);

    const addPost = (postContent, tags) => {
        const newPost = {
            content: postContent,
            tags: tags,
            username: "Current User", // Replace with actual username logic
            time: new Date().toLocaleString(),
            likes: 0,
            comments: [],
        };
        setPosts([newPost, ...posts]);
    };


    return (
        <div className="main-page">
            <Header/>
            <div className="content">
                <div className="sidebar">
                    <NavBar/>
                </div>
                <div className="main-content">
                    {/*<PostCreation/>
                    <Post post={examplePost}/>
                    <Post post={examplePost}/>
                    <Post post={examplePost}/>
                    <Post post={examplePost}/>
                    <Post post={examplePost}/>
                    <Post post={examplePost}/>*/}
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
