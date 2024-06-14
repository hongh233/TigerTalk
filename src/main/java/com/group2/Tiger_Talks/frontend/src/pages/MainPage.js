import React, { useEffect, useState } from 'react';
import ApiService from '../services/ApiService';
import Header from '../components/Header';
import Footer from '../components/Footer';
import NavBar from '../components/NavBar';
import Post from '../components/Post';
import PostCreation from '../components/PostCreation';
import '../assets/styles/Main.css';

const examplePost = {
  likes:0,
  username: 'User',
  time: '2 hours ago',
  content: 'Lorem ipsum, This is dummy text to show the content of a post, might be text, or image, of video'
};

const MainPage = () => {
  const [message, setMessage] = useState('');

  useEffect(() => {
    ApiService.getHello().then(response => {
      setMessage(response.data);
    }).catch(error => {
      console.error('There was an error!', error);
    });
  }, []);

  return (
    <div className="main-page">
      <Header />
      <div className="content">
        <div className="sidebar">
          <NavBar />
        </div>
        <div className="main-content">
          <PostCreation />
          <Post post={examplePost} />
          <Post post={examplePost} />
          <Post post={examplePost} />
          <Post post={examplePost} />
          <Post post={examplePost} />
          <Post post={examplePost} /> 
          <p>{message}</p>       
        </div>
      </div>
      {/* <Footer /> */}
    </div>
  );
};

export default MainPage;
