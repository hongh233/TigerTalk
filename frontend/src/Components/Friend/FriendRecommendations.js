import React from "react";
import "../../assets/styles/Components/Friend/FriendRecommendations.css";
import {recommendFriends} from "../../axios/Friend/FriendshipRecommendationAxios";
import {useSelector} from "react-redux";


const FriendRecommendations = () => {
    const user = useSelector((state) => state.user.user);
    const [recommendations, setRecommendations] = React.useState([]);
    const numOfFriends = 4;

    React.useEffect(() => {
        const fetchRecommendations = async () => {
            if (user) {
                try {
                    const response = await recommendFriends(user.email, numOfFriends);
                    setRecommendations(response.data);
                } catch (error) {
                    console.error("Error fetching friend recommendations:", error);
                }
            }
        }
        fetchRecommendations();
    }, [user]);

    return (
        <>
            <h3 className="friend-recommendations-header">Recommendations</h3>
            <div className="friend-recommendations">
                <ul>
                    {recommendations.map((friend, index) => (
                        <li key={index}>
                            <a href={`/profile/${friend.email}`} className="friend-link">
                                <div className="recommend-friend-picture">
                                    <img
                                        src={friend.profilePictureUrl}
                                        alt={friend.userName}
                                        className="friend-avatar"
                                    />
                                </div>
                                <div className="friend-name">
                                    <b>{friend.userName}</b>
                                </div>
                            </a>
                        </li>
                    ))}
                </ul>
            </div>
        </>
    );
};

export default FriendRecommendations;
