import React, {useEffect, useState} from "react";
import "../../assets/styles/Pages/Profile/ProfilePage.css";
import { sendFriendRequest, areFriendshipRequestExist } from "../../axios/Friend/FriendshipRequestAxios";
import { areFriends } from "../../axios/Friend/FriendshipAxios";
import {getCurrentUser, updateUserProfileSetProfilePicture} from "../../axios/UserAxios";
import { FetchPostsOfOneUser } from "../../axios/Post/PostAxios";
import { useDispatch, useSelector } from "react-redux";
import {useNavigate, useParams} from "react-router-dom";
import Post from "../../Components/Post/Post";
import { formatPost } from "../../utils/formatPost";
import {uploadImageToCloudinary} from "../../utils/cloudinaryUtils";
import ProfileStatusButton from "../../Components/Profile/ProfileStatusButton";
import ProfileEditModal from "../../Components/Profile/ProfileEditModal";
import {FaUserPlus} from "react-icons/fa";
import {AiFillEdit} from "react-icons/ai";
import {BsChatDotsFill} from "react-icons/bs";
import {MdOutlinePendingActions, MdPhotoCamera} from 'react-icons/md';
import {addFriendshipRequest} from "../../redux/actions/friendActions";


const ProfilePage = () => {
	const user = useSelector((state) => state.user.user);
	const dispatch = useDispatch();
	const { userEmail } = useParams();
	const [profileUser, setProfileUser] = useState(null);
	const [isFriend, setIsFriend] = useState(false);
	const [requestPending, setRequestPending] = useState(false);
	const [posts, setPosts] = useState([]);
	const [friendButtonText, setFriendButtonText] = useState("Add Friend");
	const [message, setMessage] = useState("");
	const paramUserEmail = useParams().userEmail;
	const [showSetting, setShowSetting] = useState(false);
	const [uploading, setUploading] = useState(false);
	const [isEditModalOpen, setIsEditModalOpen] = useState(false);
	const navigate = useNavigate();

	useEffect(() => {
		if (paramUserEmail === user.email) {
			setShowSetting(true);
		} else {
			setShowSetting(false);
		}
	}, [paramUserEmail, user.email]);


	useEffect(() => {
		if (user?.email && userEmail) {
			const fetchCurrentUser = async (email) => {
				try {
					const response = await getCurrentUser(email);
					dispatch({ type: "SET_USER", payload: response });
				} catch (error) {
					console.error("Error fetching current user data:", error);
				}
			};

			const fetchProfileUser = async (email) => {
				try {
					const response = await getCurrentUser(email);
					setProfileUser(response);
				} catch (error) {
					console.error("Error fetching profile user data:", error);
				}
			};
			fetchCurrentUser(user.email);
			fetchProfileUser(userEmail);
		}
	}, [user?.email, userEmail, dispatch]);


	useEffect(() => {
		if (profileUser && user) {
			const checkAreFriends = async () => {
				try {
					const response_friend = await areFriends(userEmail, user.email);
					const response_friendRequest = await areFriendshipRequestExist(userEmail, user.email);

					setIsFriend(response_friend);
					setRequestPending(response_friendRequest);

					if (response_friend) {
						setFriendButtonText("Chat");
					} else if (response_friendRequest) {
						setFriendButtonText("Pending");
					} else {
						setFriendButtonText("Add");
					}
				} catch (error) {
					console.error("Error checking friendship and friendship request status:", error);
				}
			};
			checkAreFriends();
		}
	}, [profileUser, user, userEmail, friendButtonText]);


	useEffect(() => {
		if (profileUser && user) {
			const checkAndFetchPosts = async () => {
				if (profileUser.email === user.email || isFriend) {
					try {
						const response = await FetchPostsOfOneUser(profileUser.email);
						const transformedPosts = formatPost(response);
						setPosts(transformedPosts);
						setMessage("");
					} catch (err) {
						console.error("There was an error fetching posts!", err);
					}
				} else {
					setMessage("You are not friends so you can't see this person's posts.");
				}
			}
			checkAndFetchPosts();
		}
	}, [profileUser, user, isFriend]);

	

	const handleFriendShip = async () => {
		try {
			if (isFriend) {
				navigate("/friends/message", { state: { selectedFriendEmail: profileUser.email } });
			} else if (!requestPending) {
				let params = {
					senderEmail: user.email,
					receiverEmail: profileUser.email,
				};
				const response = await sendFriendRequest(params);
				dispatch(addFriendshipRequest(response));
				setFriendButtonText("Pending");
			}
		} catch (error) {
			console.log("Error sending friend request:", error);
		}
	};


	const handleDeletePost = (postId) => {
		setPosts(posts.filter((post) => post.id !== postId));
	};


	const handleUpdateProfilePicture = async (e) => {
		const file = e.target.files[0];
		if (file) {
			setUploading(true);
			try {
				const imageUrl = await uploadImageToCloudinary(file);
				await updateUserProfileSetProfilePicture(profileUser.email, imageUrl);

				const updatedUser = { ...profileUser, profilePictureUrl: imageUrl };
				dispatch({ type: "SET_USER", payload: updatedUser });
				setProfileUser(updatedUser); // Update the local profileUser state
				setUploading(false);
			} catch (error) {
				console.error("Error updating profile picture:", error);
				setUploading(false);
			}
		}
	};

	const openEditModal = () => {
		setIsEditModalOpen(true);
	};

	const closeEditModal = () => {
		setIsEditModalOpen(false);
	};



	return (
		<div className="main-page">
			<div className="content">

				{profileUser && (
					<div className="profile-main-content">

						<div className="profile-page-user-info">
							<div className="profile-page-user-info-container">

								{/* profile picture setting */}
								<div className="profile-page-user-info-picture-container"
									 title="Change or add profile picture">
									<img src={profileUser && profileUser.profilePictureUrl}
										 alt="user profile"
										 className="profile-page-user-info-picture" />
									{showSetting && (
										<>
											<div
												className="profile-picture-overlay"
												onClick={() => document.getElementById('update-profile-picture').click()}>
												<MdPhotoCamera className="profile-picture-overlay-icon" />
											</div>
											<input
												type="file"
												id="update-profile-picture"
												style={{ display: "none" }}
												onChange={handleUpdateProfilePicture}
											/>
											{uploading &&
												<p className="profile-picture-uploading-text">Uploading...</p>
											}
										</>
									)}
								</div>

								{/* profile info display */}
								<div className="profile-page-user-info-text">
									<div className="profile-status-and-edit-button-box">
										<ProfileStatusButton
											profileUser={profileUser}
											paramUserEmail={paramUserEmail}
											user={user}
										/>
										{showSetting ? (
											<button className="edit-profile-button" id="edit" onClick={openEditModal}>
												<div className="profile-page-pending-chat-add-box" ><AiFillEdit />&nbsp;Edit</div>
											</button>
										) : (
											profileUser && profileUser.email !== user.email && (
												<>
													{friendButtonText === "Pending" &&
														<button className="edit-profile-button" id="pending" onClick={handleFriendShip}>
															<div className="profile-page-pending-chat-add-box"><MdOutlinePendingActions />&nbsp;{friendButtonText}</div>
														</button>
													}
													{friendButtonText === "Chat" &&
														<button className="edit-profile-button" id="chat" onClick={handleFriendShip}>
															<div className="profile-page-pending-chat-add-box"><BsChatDotsFill />&nbsp;{friendButtonText}</div>
														</button>
													}
													{friendButtonText === "Add" &&
														<button className="edit-profile-button" id="add" onClick={handleFriendShip}>
															<div className="profile-page-pending-chat-add-box"><FaUserPlus />&nbsp;{friendButtonText}</div>
														</button>
													}
												</>
											)
										)}
									</div>

									<p className="profile-personal-information">
										<span><strong>Posts: </strong>{profileUser.numOfPosts} posts</span>
										<span><strong>Friends: </strong>{profileUser.numOfFriends} friends</span>
										<span><strong>Groups: </strong>{profileUser.numOfGroups} groups</span>
									</p>
									<p className="profile-personal-information">
										{(profileUser.firstName !== "" || profileUser.lastName !== "") &&
											<span><strong>Full Name: </strong>{profileUser.firstName} {profileUser.lastName}</span>
										}
										{profileUser.birthday !== "0000-00-00" &&
											<span><strong>Birthday: </strong>{profileUser.birthday}</span>
										}
										<span><strong>Role: </strong>{profileUser.role}</span>
									</p>
									<p className="profile-biography-paragraph">{profileUser.biography}</p>
								</div>

							</div>
						</div>

						{message.length > 0 ? (
							<p>{message}</p>
						) : (
							posts.map((post) => (
								<Post key={post.id}
									  post={post}
									  user={user}
									  removePost={handleDeletePost}/>
							))
						)}
					</div>
				)}

				<ProfileEditModal
					isOpen={isEditModalOpen}
					onClose={closeEditModal}
					user={user}
					setProfileUser={setProfileUser}
				/>

			</div>
		</div>
	);
};

export default ProfilePage;
