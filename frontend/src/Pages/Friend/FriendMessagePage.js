import React, { useEffect, useState, useRef } from "react";
import "../../assets/styles/Pages/Friend/FriendMessagePage.css";
import {getAllFriendsDTO} from "../../axios/Friend/FriendshipAxios";
import { createMessage, getAllMessagesByFriendshipId } from "../../axios/Friend/FriendshipMessageAxios";
import { useSelector } from "react-redux";
import SockJS from "sockjs-client";
import { Client } from "@stomp/stompjs";
import StatusIcon from "../../Components/Main/StatusIcon";
const URL = process.env.REACT_APP_API_URL;


const FriendMessagePage = () => {
	const user = useSelector((state) => state.user.user);
	const [friends, setFriends] = useState([]);
	const [selectedFriend, setSelectedFriend] = useState(null);
	const [messages, setMessages] = useState([]);
	const [newMessage, setNewMessage] = useState("");
	const [stompClient, setStompClient] = useState(null);

	const messagesEndRef = useRef(null);

	const scrollToBottom = () => {
		messagesEndRef.current?.scrollIntoView({ behavior: "smooth" });
	};

	useEffect(() => {
		const fetchFriends = async () => {
			if (user && user.email) {
				try {
					const responseData = await getAllFriendsDTO(user.email);
					if (responseData.length > 0) {
						setFriends(responseData);
						const savedFriendEmail = localStorage.getItem(
							"selectedFriendEmail"
						);
						if (savedFriendEmail) {
							const friend = responseData.find(
								(f) => f.email === savedFriendEmail
							);
							if (friend) {
								setSelectedFriend(friend);
								fetchMessages(friend.id);
							} else {
								setSelectedFriend(responseData[0]);
								fetchMessages(responseData[0].id);
							}
						} else {
							setSelectedFriend(responseData[0]);
							fetchMessages(responseData[0].id);
						}
					}
				} catch (error) {
					console.error("Failed to fetch friends", error);
				}
			}
		};
		fetchFriends();
	}, [user]);

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


	useEffect(() => {
		scrollToBottom();
	}, [messages]);

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
			li.classList.remove("selected");
		});
		document.getElementById(`friend-${friend.email}`).classList.add("selected");
	};

	const handleSendMessage = async () => {
		if (newMessage.trim() === "" || !selectedFriend) return;

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
			},
		};

		try {
			if (await createMessage(message)) {
				setNewMessage("");

				const messagesResponseData = await getAllMessagesByFriendshipId(
					selectedFriend.id
				);
				const latestMessage =
					messagesResponseData[messagesResponseData.length - 1];
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
		if (e.key === "Enter") {
			handleSendMessage();
		}
	};

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
							<div className="chat-header">{selectedFriend.userName}</div>)
						}

						<div className="messages">
							{messages.length === 0 ? (
								<div>No messages to display</div>
							) : (
								messages.map((message) => (
									<div key={message.messageId}
										className={message.messageSenderEmail === user.email ? "message-right" : "message-left"}>

										{message.messageSenderEmail !== user.email && (
											<div className="common-profile-picture-and-status-icon">
												<img src={message.messageSenderProfilePictureUrl} alt="Avatar" className="avatar"/>
												<StatusIcon status={message.messageSenderOnlineStatus} />
											</div>
										)}

										<div className="message-bubble">
											{message.messageContent}
										</div>

										{message.messageSenderEmail === user.email && (
											<div className="common-profile-picture-and-status-icon">
												<img src={user.profilePictureUrl} alt="Avatar" className="avatar"/>
												<StatusIcon status={user.onlineStatus} />
											</div>
										)}
									</div>
								))
							)}
							<div ref={messagesEndRef} />
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

							<button className="send-button" onClick={handleSendMessage}>
								Send
							</button>
						</div>

					</div>
				</div>
			</div>
		</div>
	);
};

export default FriendMessagePage;
