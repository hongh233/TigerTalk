import React from 'react';
import Post from '../components/Post';
import Header from '../components/Header';
import '../assets/styles/GroupPage.css';
import GroupNavBar from '../components/GroupNavBar';


const posts = [
    {
      id: 1,
      userName: 'User',
      time: '2 hours ago',
      content: 'This is an example post content to demonstrate the post layout. It can include text, images, and other media.'
    },
    {
      id: 2,
      userName: 'User',
      time: '1 day ago',
      content: 'Another example post to show how multiple posts would look on the view group page.'
    }
  ];

const ViewGroupPage = () => (
  <div className="group-page">
    <Header/>
    <div className="content">
      <div className="group-nav">
        <GroupNavBar/>
      </div>
    
      <div className="group-content">
        {posts.map(post => <Post key={post.id} post={post} />)}
      </div>
    </div>

  </div>
);

export default ViewGroupPage;
