import React, {useEffect, useState} from "react";
import "../../assets/styles/Pages/Profile/ProfilePage.css";
import { sendFriendRequest, areFriendshipRequestExist } from "../../axios/Friend/FriendshipRequestAxios";
import { areFriends } from "../../axios/Friend/FriendshipAxios";
import {deleteFriendshipByEmail} from "../../axios/Friend/FriendshipAxios";
import {getCurrentUser, updateUserProfileSetProfilePicture} from "../../axios/UserAxios";
import { FetchPostsOfOneUser } from "../../axios/Post/PostAxios";
import { MdPhotoCamera } from 'react-icons/md';
import { useDispatch, useSelector } from "react-redux";
import { useParams } from "react-router-dom";
import Post from "../../Components/Post/Post";
import Header from "../../Components/Main/Header";
import { formatPost } from "../../utils/formatPost";
import {uploadImageToCloudinary} from "../../utils/cloudinaryUtils";
import ProfileStatusButton from "../../Components/Profile/ProfileStatusButton";
import ProfileEditModal from "../../Components/Profile/ProfileEditModal";


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
					const response = await areFriends(userEmail, user.email);
					setIsFriend(response);
					if (response) {
						setFriendButtonText("Unfriend");
					}
				} catch (error) {
					console.error("Error checking friendship status:", error);
				}
			};
			const checkFriendShipRequestExist = async () => {
				const response = await areFriendshipRequestExist(userEmail, user.email);
				setRequestPending(response);
				if (response) {
					setFriendButtonText("Pending");
				}
			};
			checkAreFriends();
			checkFriendShipRequestExist();
		}
	}, [profileUser, user, userEmail, friendButtonText]);


	useEffect(() => {
		if (profileUser && user) {
			if (profileUser.email === user.email || isFriend) {
				fetchPosts(profileUser.email);
				setMessage("");
			} else {
				setMessage("You are not friends so you can't see this person's posts.");
			}
		}
	}, [profileUser, user, isFriend]);


	const fetchPosts = async (email) => {
		try {
			const response = await FetchPostsOfOneUser(email);
			const transformedPosts = formatPost(response);
			setPosts(transformedPosts);
		} catch (err) {
			console.error("There was an error fetching posts!", err);
		}
	};

	const handleFriendShip = async () => {
		try {
			if (isFriend) {
				if (window.confirm("Are you sure to unfriend?")) {
					deleteFriendshipByEmail(user.email, profileUser.email);
					setIsFriend(false);
				}
			} else if (!requestPending) {
				let params = {
					senderEmail: user.email,
					receiverEmail: profileUser.email,
				};
				sendFriendRequest(params);
				window.location.reload();
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
			<Header />
			<div className="content">

				{profileUser && (
					<div className="profile-main-content">

						<div className="profile-page-user-info">
							<div className="profile-page-user-info-container">

								<div className="profile-page-user-info-picture-container" title="Change or add profile picture">
									<img src={profileUser && profileUser.profilePictureUrl}
										 alt="user profile" className="profile-page-user-info-picture" />
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
											{uploading && <p className="profile-picture-uploading-text">Uploading...</p>}
										</>
									)}
								</div>

								<div className="profile-page-user-info-text">
									<div className="profile-status-and-edit-button-box">
										<ProfileStatusButton
											profileUser={profileUser}
											paramUserEmail={paramUserEmail}
											user={user}
										/>
										{showSetting ? (
											<button className="edit-profile-button" onClick={openEditModal}>Edit profile</button>
										) : (
											profileUser && profileUser.email !== user.email && (
												<button className={`edit-profile-button`} onClick={handleFriendShip}>{friendButtonText}</button>
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


									<p className="profile-biography-paragraph"><strong></strong>{profileUser.biography}</p>
								</div>
							</div>

						</div>
						<div className="profile-content-post-list">
							<div className="profile-content-post">
								{message.length > 0 ? (<p>{message}</p>) : (
									posts.map((post) => (<Post key={post.id} post={post} user={user} removePost={handleDeletePost}/>))
								)}
							</div>
						</div>
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
