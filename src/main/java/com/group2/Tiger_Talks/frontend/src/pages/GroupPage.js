import React from 'react';
import NavBar from '../components/NavBar';
import Header from '../components/Header';
import '../assets/styles/GroupPage.css';
import Group from '../components/Group';


const groups = [
  {
    id: 1,
    groupName: 'test',
    status: "public",
    groupOwner:"TEST",
    dateCreated: '2 hours ago',
    description: 'This is an example Group content to demonstrate the Group layout. '
  },
  {
    id: 2,
    groupName: 'test',
    status: "private",
    groupOwner:"TEST",
    dateCreated: '2 hours ago',
    description: 'This is an example Group content to demonstrate the Group layout. '
  }
];

const GroupPage = () => (
  <div className="group-page">
    <Header/>
    <div className='content'>
      <div className="group-nav">
        <NavBar/>
      </div>
      <div className="group-content">
        {groups.map(group => <Group key={group.id} group={group} />)}
      </div>
    </div>
  
  </div>
);

export default GroupPage;
