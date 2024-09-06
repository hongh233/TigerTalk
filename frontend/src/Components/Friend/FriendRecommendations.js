import React from "react";
import "../../assets/styles/Components/Friend/FriendRecommendations.css";
import {recommendFriends} from "../../axios/Friend/FriendshipRecommendationAxios";
import {useSelector} from "react-redux";
import StatusIcon from "../Main/StatusIcon";


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
            <h3 className="friend-recommendations-header">
                Discover People
            </h3>
            <div className="friend-recommendations">
                <ul>
                    {recommendations.map((user, index) => (
                        <li key={index}>
                            <a href={`/profile/${user.email}`} className="friend-recommend-link">
                                <div className="common-profile-picture-and-status-icon">
                                    <img src={user.profilePictureUrl} alt="avatar" />
                                    <StatusIcon status={user.onlineStatus}/>
                                </div>
                                <div className="friend-recommend-name"><b>{user.userName}</b></div>
                            </a>
                        </li>
                    ))}
                </ul>
            </div>
        </>
    );
};

export default FriendRecommendations;
