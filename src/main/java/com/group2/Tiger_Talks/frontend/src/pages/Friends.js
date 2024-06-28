// src/pages/Friends.js
import React, {useState} from 'react';
import {acceptFriendRequest, getAllFriends, rejectFriendRequest, sendFriendRequest} from './FriendApi';

const Friends = () => {
    const [senderEmail, setSenderEmail] = useState('');
    const [receiverEmail, setReceiverEmail] = useState('');
    const [email, setEmail] = useState('');
    const [friends, setFriends] = useState([]);

    const handleSendRequest = () => {
        sendFriendRequest(senderEmail, receiverEmail)
            .then(response => alert(response.data))
            .catch(error => alert(error.response.data));
    };

    const handleAcceptRequest = (friendshipRequestId) => {
        acceptFriendRequest(friendshipRequestId)
            .then(response => alert(response.data))
            .catch(error => alert(error.response.data));
    };

    const handleRejectRequest = (friendshipRequestId) => {
        rejectFriendRequest(friendshipRequestId)
            .then(response => alert(response.data))
            .catch(error => alert(error.response.data));
    };

    const handleGetAllFriends = () => {
        getAllFriends(email)
            .then(response => setFriends(response.data))
            .catch(error => alert(error.response.data));
    };

    return (
        <div>
            <h1>Friendship Management</h1>

            <div>
                <h2>Send Friend Request</h2>
                <input
                    type="email"
                    placeholder="Sender Email"
                    value={senderEmail}
                    onChange={(e) => setSenderEmail(e.target.value)}
                />
                <input
                    type="email"
                    placeholder="Receiver Email"
                    value={receiverEmail}
                    onChange={(e) => setReceiverEmail(e.target.value)}
                />
                <button onClick={handleSendRequest}>Send Request</button>
            </div>

            <div>
                <h2>Get All Friends</h2>
                <input
                    type="email"
                    placeholder="Your Email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                />
                <button onClick={handleGetAllFriends}>Get Friends</button>

                <ul>
                    {friends.map(friend => (
                        <li key={friend.friendshipId}>
                            {friend.userFriendshipSender} - {friend.userFriendshipReceiver}
                            {/* These buttons assume you have access to the friendship request ID */}
                            <button onClick={() => handleAcceptRequest(friend.friendshipRequestId)}>Accept</button>
                            <button onClick={() => handleRejectRequest(friend.friendshipRequestId)}>Reject</button>
                        </li>
                    ))}
                </ul>
            </div>
        </div>
    );
};

export default Friends;
