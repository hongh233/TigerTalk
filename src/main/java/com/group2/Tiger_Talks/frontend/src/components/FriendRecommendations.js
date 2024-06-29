import React from "react";
import axios from "axios";

import {useSelector} from "react-redux";
import "../assets/styles/FriendRecommendations.css";

const FriendRecommendations = () => {
    const user = useSelector((state) => state.user.user);
    const [recommendations, setRecommendations] = React.useState([]);
    const numOfFriends = 4;

    React.useEffect(() => {
        if (user) {
            axios
                .get(
                    `http://localhost:8085/friendships/recommendFriends/${user.email}/${numOfFriends}`,
                    {
                        params: {
                            numOfFriends: numOfFriends,
                        },
                    }
                )
                .then((response) => {
                    setRecommendations(response.data);
                })
                .catch((error) => {
                    console.error("Error fetching friend recommendations:", error);
                });
        }
    }, [user]);

    return (
        <div className="friend-recommendations">
            <h3>Friend Recommendations</h3>
            <ul>
                {recommendations.map((friend, index) => (
                    <li key={index}>
                        <a href={`/profile/${friend.email}`}>{friend.userName} - {friend.email}</a>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default FriendRecommendations;
