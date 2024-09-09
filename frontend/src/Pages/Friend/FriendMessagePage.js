import React, { useEffect, useState, useRef } from "react";
import "../../assets/styles/Pages/Friend/FriendMessagePage.css";
import { createMessage, getAllMessagesByFriendshipId } from "../../axios/Friend/FriendshipMessageAxios";
import { useSelector } from "react-redux";
import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";
import StatusIcon from "../../Components/Main/StatusIcon";
import {IoMdHappy} from "react-icons/io";
import data from '@emoji-mart/data';
import Picker from '@emoji-mart/react';
import {LuSendHorizonal} from "react-icons/lu";
import {useLocation} from "react-router-dom";
const URL = process.env.REACT_APP_API_URL;


const FriendMessagePage = () => {
	const location = useLocation();
	const user = useSelector((state) => state.user.user);
	const friends = useSelector((state) => state.friends.friends);
	const [selectedFriend, setSelectedFriend] = useState(null);
	const [messages, setMessages] = useState([]);
	const [newMessage, setNewMessage] = useState("");
	const [stompClient, setStompClient] = useState(null);
	const messagesEndRef = useRef(null);
	const [showEmojiPicker, setShowEmojiPicker] = useState(false);
	const emojiPickerRef = useRef(null);

	const scrollToBottom = () => {
		messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
	};

	useEffect(() => {
		const fetchFriends = async () => {
			if (user && user.email) {
				try {
					if (friends.length > 0) {
						const savedFriendEmail = localStorage.getItem("selectedFriendEmail");
						const selectedFriendEmailFromNav = location.state?.selectedFriendEmail;

						if (selectedFriendEmailFromNav) {
							const friend = friends.find((f) => f.email === selectedFriendEmailFromNav);
							if (friend) {
								handleFriendClick(friend);
							}
						} else if (savedFriendEmail) {
							const friend = friends.find((f) => f.email === savedFriendEmail);
							if (friend) {
								setSelectedFriend(friend);
								fetchMessages(friend.id);
							} else {
								setSelectedFriend(friends[0]);
								fetchMessages(friends[0].id);
							}
						} else {
							setSelectedFriend(friends[0]);
							fetchMessages(friends[0].id);
						}
					}
				} catch (error) {
					console.error("Failed to fetch friends", error);
				}
			}
		};
		fetchFriends();
	}, [user, location.state]);

    useEffect(() => {
        const socket = new SockJS(`${URL}/ws`);
        const client = new Client({
            webSocketFactory: () => socket,
            reconnectDelay: 5000,
            debug: function (str) {
                console.log(str);
            },
        });

        client.onConnect = () => {
            console.log("Connected to WebSocket");
        };

		client.onStompError = (frame) => {
			console.error("Broker reported error: " + frame.headers["message"]);
			console.error("Additional details: " + frame.body);
		};

		client.activate();
		setStompClient(client);

        return () => {
            client.deactivate();
        };
    }, []);

    useEffect(() => {
        if (stompClient && selectedFriend && stompClient.connected) {
            const subscription = stompClient.subscribe("/topic/messages", (message) => {
                const receivedMessage = JSON.parse(message.body);
                if (receivedMessage.friendshipId === selectedFriend.id) {
                    setMessages((prevMessages) => {
                        const messageExists = prevMessages.some(msg => msg.messageId === receivedMessage.messageId);
                        if (!messageExists) {return [...prevMessages, receivedMessage];}
                        return prevMessages;
                    });
                }
            });
            return () => {subscription.unsubscribe();};
        }
    }, [stompClient, selectedFriend]);


    useEffect(() => {
        if (stompClient && selectedFriend) {
            const subscribeToMessages = () => {
                if (stompClient.connected) {
                    const subscription = stompClient.subscribe("/topic/messages", (message) => {
                        const receivedMessage = JSON.parse(message.body);
                        if (receivedMessage.friendshipId === selectedFriend.id) {
                            setMessages((prevMessages) => {
                                const messageExists = prevMessages.some(msg => msg.messageId === receivedMessage.messageId);
                                if (!messageExists) {
                                    return [...prevMessages, receivedMessage];
                                }
                                return prevMessages;
                            });
                        }
                    });

                    return () => {
                        subscription.unsubscribe();
                    };
                } else {
                    // Retry subscription after a short delay if not connected
                    setTimeout(subscribeToMessages, 500);
                }
            };
            const unsubscribe = subscribeToMessages();
            return () => {
                if (unsubscribe) unsubscribe();
            };
        }
    }, [stompClient, selectedFriend]);

	useEffect(() => {scrollToBottom()}, [messages]);

	const fetchMessages = async (friendshipId) => {
		try {
			const responseData = await getAllMessagesByFriendshipId(friendshipId);
			setMessages(responseData);
		} catch (error) {
			console.error("Failed to fetch messages", error);
		}
	};

	const handleFriendClick = (friend) => {
		setSelectedFriend(friend);
		localStorage.setItem("selectedFriendEmail", friend.email);
		fetchMessages(friend.id);
		document.querySelectorAll(".friend-list li").forEach((li) => {
			li.classList.remove("selected");});
		document.getElementById(`friend-${friend.email}`).classList.add("selected");
	};

	const handleSendMessage = async () => {
		if (newMessage.trim() === "" || !selectedFriend) return;

		const message = {
			messageContent: newMessage,
			sender: {email: user.email,},
			receiver: {email: selectedFriend.email,},
			friendship: {friendshipId: selectedFriend.id,},
		};

		try {
			if (await createMessage(message)) {
				setNewMessage("");
				const messagesResponseData = await getAllMessagesByFriendshipId(selectedFriend.id);
				const latestMessage = messagesResponseData[messagesResponseData.length - 1];
				console.log(latestMessage);

				if (latestMessage && stompClient) {
					// send new message to WebSocket
					stompClient.publish({
						destination: "/app/sendMessage",
						body: JSON.stringify(latestMessage.messageId),
					});
					setMessages((prevMessages) => [...prevMessages, latestMessage]);
				}
			}
		} catch (error) {
			console.error("Failed to send message", error);
		}
	};

	const handleKeyPress = (e) => {
		if (e.key === "Enter") {handleSendMessage();}
	};

	const toggleEmojiPicker = () => {
		setShowEmojiPicker(!showEmojiPicker);
	};
	const addEmoji = (emoji) => {
		setNewMessage((prevMessage) => prevMessage + emoji.native);
		setShowEmojiPicker(false);
	};

	useEffect(() => {
		const handleClickOutside = (event) => {
			if (emojiPickerRef.current && !emojiPickerRef.current.contains(event.target)) {
				setShowEmojiPicker(false);
			}
		};

		document.addEventListener("mousedown", handleClickOutside);

		return () => {
			document.removeEventListener("mousedown", handleClickOutside);
		};
	}, [emojiPickerRef]);


	return (
		<div className="main-page">
			<div className="content" id="friend-message-page">

				<div className="friend-message-content-container">

					<div className="friend-list">
						<ul>
							{friends.map((friend) => (
								<li key={friend.email}
									id={`friend-${friend.email}`}
									onClick={() => handleFriendClick(friend)}>

									<div className="friend-message-friend">
										<div className="friend-message-header">
											<div className="common-profile-picture-and-status-icon">
												<img src={friend.profilePictureUrl} alt="avatar" />
												<StatusIcon status={friend.onlineStatus} />
											</div>
											<div className="friend-message-details">
												<div>{friend.userName}</div>
												<p>{friend.email}</p>
											</div>
										</div>
									</div>
								</li>
							))}
						</ul>

					</div>

					<div className="chat-box">
						{selectedFriend && (
							<div className="chat-header">
								<div className="chat-header-inner-box">{selectedFriend.userName}</div>
							</div>)
						}

						<div className="messages">
							{messages.length === 0 ? (
								<div className="common-empty-message-text">
									🤔 It looks like a little quiet here... Start your first message!
								</div>
							) : (
								messages.map((message) => (
									<div key={message.messageId}
										className={message.messageSenderEmail === user.email ? "message-right" : "message-left"}>

										{message.messageSenderEmail !== user.email && (
											<a className="common-profile-picture-and-status-icon"
											   href={`/profile/${message.messageSenderEmail}`}>
												<img src={message.messageSenderProfilePictureUrl} alt="Avatar" className="avatar"/>
												<StatusIcon status={message.messageSenderOnlineStatus} />
											</a>
										)}

										<div className="message-bubble">
											{message.messageContent}
										</div>

										{message.messageSenderEmail === user.email && (
											<a className="common-profile-picture-and-status-icon"
											   href={`/profile/${user.email}`}>
												<img src={user.profilePictureUrl} alt="Avatar" className="avatar"/>
												<StatusIcon status={user.onlineStatus} />
											</a>
										)}
									</div>
								))
							)}
							<div ref={messagesEndRef} />
						</div>

						<div className="message-input">

							<button className="emoji-button" onClick={toggleEmojiPicker}>
								<IoMdHappy size={24} />
							</button>

							{showEmojiPicker && (
								<div className="emoji-picker-modal" ref={emojiPickerRef}>
									<Picker data={data} onEmojiSelect={addEmoji} />
								</div>
							)}

							<input
								type="text"
								placeholder="Type a message..."
								value={newMessage}
								onChange={(e) => setNewMessage(e.target.value)}
								onKeyPress={handleKeyPress}
							/>

							<button className="send-button" onClick={handleSendMessage}>
								<LuSendHorizonal />
							</button>
						</div>

					</div>
				</div>
			</div>
		</div>
	);
};

export default FriendMessagePage;
