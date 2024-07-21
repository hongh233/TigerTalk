import Header from "../components/Header";
import NavBar from "../components/NavBar";
import React, { useEffect, useState } from "react";
import { useSelector } from "react-redux";
import axios from "axios";
import "../assets/styles/FriendMessagePage.css";

const FriendMessagePage = () => {
    const user = useSelector((state) => state.user.user);
    const [friends, setFriends] = useState([]);
    const [selectedFriend, setSelectedFriend] = useState(null);
    const [searchGroup, setSearchGroup] = useState([]);
    const [messages, setMessages] = useState([]);
    const [newMessage, setNewMessage] = useState("");

    useEffect(() => {
        const fetchFriends = async () => {
            if (user && user.email) {
                try {
                    const response = await axios.get(
                        `http://localhost:8085/friendships/DTO/${user.email}`
                    );
                    if (response.data.length > 0) {
                        setFriends(response.data);
                    }
                } catch (error) {
                    console.error("Failed to fetch friends", error);
                }
            }
        };
        fetchFriends();
    }, [user]);

    const fetchMessages = async (friendshipId) => {
        try {
            const response = await axios.get(
                `http://localhost:8085/friendships/message/getAll/${friendshipId}`
            );
            setMessages(response.data);
        } catch (error) {
            console.error("Failed to fetch messages", error);
        }
    };

    const handleFriendClick = (friend) => {
        setSelectedFriend(friend);
        fetchMessages(friend.id); // Assuming each friend object has a friendshipId field
    };

    const handleSendMessage = async () => {
        if (newMessage.trim() === "" || !selectedFriend) return;
        console.error(selectedFriend.id);
        const message = {
            messageContent: newMessage,
            sender: {
                email: user.email,
            },
            receiver: {
                email: selectedFriend.email,
            },
            friendship: {
                friendshipId: selectedFriend.id,
            }
        };

        try {
            const response = await axios.post(
                'http://localhost:8085/friendships/message/create',
                message
            );
            if (response.status === 200) {
                // Clear the input field and refresh the message list
                setNewMessage("");
                fetchMessages(selectedFriend.id);
            }
        } catch (error) {
            console.error("Failed to send message", error);
        }
    };

    const handleKeyPress = (e) => {
        if (e.key === 'Enter') {
            handleSendMessage();
        }
    };

    return (
        <div className="group-page" style={{ overflow: 'hidden' }}>
            <Header />
            <div className="group-page-wrapper">
                <div className="group-nav">
                    <NavBar />
                </div>
                <div className="friend-message-content-container">
                    <div className="friend-list">
                        <h2>Messages</h2>
                        <ul>
                            {friends.map((friend) => (
                                <li key={friend.email} onClick={() => handleFriendClick(friend)}>
                                    <div className="friend">
                                        <div className="friend-header">
                                            <div className="friend-picture">
                                                <img src={friend.profilePictureUrl} alt="avatar" />
                                            </div>
                                            <div className="friend-details">
                                                <a href={"/profile/" + friend.email}>{friend.userName}</a>
                                                <p>Email: {friend.email}</p>
                                            </div>
                                        </div>
                                    </div>
                                </li>
                            ))}
                        </ul>
                    </div>
                    <div className="chat-box">
                        {selectedFriend && <div className="chat-header">{selectedFriend.userName}</div>}
                        <div className="messages">
                            {messages.length === 0 ? (
                                <div>No messages to display</div>
                            ) : (
                                messages.map((message) => (
                                    <div
                                        key={message.messageId}
                                        className={message.messageSenderEmail === user.email ? "message-right" : "message-left"}
                                    >
                                        {message.messageSenderEmail !== user.email && (
                                            <div className="friend-picture">
                                                <img src={message.messageSenderProfilePictureUrl} alt="Avatar" className="avatar" />
                                            </div>
                                        )}
                                        <div className="message-bubble">{message.messageContent}</div>
                                        {message.messageSenderEmail === user.email && (
                                            <div className="friend-picture">
                                                <img src={message.messageSenderProfilePictureUrl} alt="Avatar" className="avatar" />
                                            </div>
                                        )}
                                    </div>
                                ))
                            )}
                        </div>
                        <div className="message-input">
                            <button className="emoji-button">😊</button>
                            <input
                                type="text"
                                placeholder="Type a message..."
                                value={newMessage}
                                onChange={(e) => setNewMessage(e.target.value)}
                                onKeyPress={handleKeyPress}
                            />
                            <button className="send-button" onClick={handleSendMessage}>Send</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default FriendMessagePage;
