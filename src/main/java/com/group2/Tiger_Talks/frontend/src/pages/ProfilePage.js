import React from 'react';
import ProfileNavBar from '../components/ProfileNavBar';
import Post from '../components/Post';
import Header from '../components/Header';
import '../assets/styles/ProfilePage.css';

const user = {
  name: 'User',
  email: 'user@example.com',
};

const posts = [
  {
    id: 1,
    username: 'User',
    time: '2 hours ago',
    content: 'This is an example post content to demonstrate the post layout. It can include text, images, and other media.'
  },
  {
    id: 2,
    username: 'User',
    time: '1 day ago',
    content: 'Another example post to show how multiple posts would look on the profile page.'
  }
];

const ProfilePage = () => (
  <div className="profile-page">
    <Header/>
    <div className='profile-content'>
      <div className="profile-nav">
        <ProfileNavBar user={user}/>
      </div>
    
      <div className="profile-main-content">
        {posts.map(post => <Post key={post.id} post={post} />)}
      </div>
    </div>

  </div>
);

export default ProfilePage;
