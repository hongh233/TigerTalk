import React from 'react';
import axios from 'axios';
import { useUser } from '../context/UserContext';
import '../assets/styles/FriendRecommendations.css';

const FriendRecommendations = () => {
    const { user } = useUser();
    const [recommendations, setRecommendations] = React.useState([]);
    const numOfFriends = 1;

    React.useEffect(() => {
        if (user) {
            axios.get(`http://localhost:8085/friendships/recommendFriends/${user.email}/${numOfFriends}`, {
                params: {
                    numOfFriends: numOfFriends
                }
            })
            .then(response => {
                setRecommendations(response.data);
            })
            .catch(error => {
                console.error('Error fetching friend recommendations:', error);
            });
        }
    }, [user]);

    return (
        <div className="friend-recommendations">
            <h3>Friend Recommendations</h3>
            <ul>
                {recommendations.map((friend, index) => (
                    <li key={index}>
                        {friend.userName} - {friend.email}
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default FriendRecommendations;