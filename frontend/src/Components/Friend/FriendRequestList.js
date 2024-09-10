import React, {useEffect, useState} from "react";
import "../../assets/styles/Components/Friend/FriendRequestList.css";
import {useDispatch} from "react-redux";
import {addFriend} from "../../redux/actions/friendActions";
import {acceptFriendRequest, rejectFriendRequest} from "../../axios/Friend/FriendshipRequestAxios";
import StatusIcon from "../Main/StatusIcon";
import {formatDate} from "../../utils/formatDate";


const FriendRequest = ({request, onRequestHandled}) => {
    const dispatch = useDispatch();
    const handleAccept = async () => {
        const responseData = await acceptFriendRequest(request.id);
        console.log("Friend accepted:", responseData);
        dispatch(addFriend(responseData));
        onRequestHandled(request.id);
    };

    const handleReject = async () => {
        await rejectFriendRequest(request.id);
        onRequestHandled(request.id);
    };

    return (
        <div className="friend-request">
            <div className="friend-request-header">
                <a className="common-profile-picture-and-status-icon"
                   href={`/profile/${request.senderEmail}`}>
                    <img src={request.senderProfilePictureUrl} alt="avatar"/>
                    <StatusIcon status={request.senderOnlineStatus}/>
                </a>
                <div className="friend-request-friend-details">
                    <a href={"/profile/" + request.senderEmail}>{request.senderName}</a>
                    <div>{request.senderEmail}</div>
                    <div>{formatDate(request.createTime)}</div>
                </div>
            </div>
            <div className="friend-request-actions">
                <button className="accept-button" onClick={handleAccept}>Accept</button>
                <button className="reject-button" onClick={handleReject}>Reject</button>
            </div>
        </div>
    );
};


const FriendRequestList = ({ friendRequests, setFriendRequests, fetchFriendData }) => {
    const [requests, setRequests] = useState([]);

    useEffect(() => {
        setRequests(friendRequests);
    }, [friendRequests]);

    const handleRequestHandled = (requestId) => {
        const updatedRequests = requests.filter((request) => request.id !== requestId);
        setRequests(updatedRequests);
        setFriendRequests(updatedRequests);
        fetchFriendData();
    };

    return (
        <div className="friend-request-list">
            <div className="friend-request-list-content">
                {friendRequests.length > 0 ? (
                    friendRequests.map((request) => (
                        <FriendRequest
                            key={request.id}
                            request={request}
                            onRequestHandled={handleRequestHandled}
                        />
                    ))
                ) : (
                    <div className="no-friend-requests">
                        <p>There is no friend request.</p>
                    </div>
                )}
            </div>
        </div>
    );
};

export default FriendRequestList;
