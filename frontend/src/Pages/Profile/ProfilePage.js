import React, {useEffect, useState} from "react";
import "../../assets/styles/Pages/Profile/ProfilePage.css";
import { sendFriendRequest, areFriendshipRequestExist } from "../../axios/Friend/FriendshipRequestAxios";
import { areFriends } from "../../axios/Friend/FriendshipAxios";
import {getCurrentUser, updateUserProfileSetProfilePicture} from "../../axios/UserAxios";
import {fetchPostsOfOneUser} from "../../axios/Post/PostAxios";
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


const ProfilePage = () => {
	const user = useSelector((state) => state.user.user);
	const dispatch = useDispatch();
	const { userEmail } = useParams();
	const [profileUser, setProfileUser] = useState(null);
	const [posts, setPosts] = useState([]);
	const [friendButtonText, setFriendButtonText] = useState("");
	const [message, setMessage] = useState("");
	const [uploading, setUploading] = useState(false);
	const [isEditModalOpen, setIsEditModalOpen] = useState(false);
	const navigate = useNavigate();
	const [loading, setLoading] = useState(true);

	useEffect(() => {
		const fetchProfileData = async () => {
			const profileData = await getCurrentUser(userEmail);
			setProfileUser(profileData);

			if (user.email === userEmail) {
				setFriendButtonText("Edit");
			} else if (await areFriends(userEmail, user.email)) {
				setFriendButtonText("Chat");
			} else if (await areFriendshipRequestExist(userEmail, user.email)) {
				setFriendButtonText("Pending");
			} else {
				setFriendButtonText("Add");
			}
			setLoading(false);
		};
		fetchProfileData();
	}, [user.email, userEmail]);


	useEffect(() => {
		const fetchPosts = async () => {
			setMessage("");
			if (profileUser && (profileUser.email === user.email || friendButtonText === "Chat")) {
				const data = await fetchPostsOfOneUser(profileUser.email);
				setPosts(formatPost(data));
			} else {
				setMessage("You are not friends and cannot see this user's posts.");
			}
		};
		fetchPosts();
	}, [profileUser, friendButtonText]);


	const handleFriendShip = async () => {
		if (friendButtonText === "Edit") {
			setIsEditModalOpen(true);
		} else if (friendButtonText === "Chat") {
			navigate("/friends/message", { state: { selectedFriendEmail: profileUser.email } });
		} else if (friendButtonText === "Add") {
			await sendFriendRequest({ senderEmail: user.email, receiverEmail: profileUser.email });
			setFriendButtonText("Pending");
		}
	};

	const handleUpdateProfilePicture = async (e) => {
		const file = e.target.files[0];
		if (file) {
			setUploading(true);
			const imageUrl = await uploadImageToCloudinary(file);
			await updateUserProfileSetProfilePicture(profileUser.email, imageUrl);
			const updatedUser = { ...profileUser, profilePictureUrl: imageUrl };
			dispatch({ type: "SET_USER", payload: updatedUser });
			setProfileUser(updatedUser);
			setUploading(false);
		}
	};


	return (
		<div className="main-page">
			<div className="content">
				{loading ? (
					<p>Loading...</p>
				) : (

					profileUser ? (
						<div className="profile-main-content">
							<div className="profile-page-user-info">
								<div className="profile-page-user-info-container">

									{/* profile picture setting */}
									<div className="profile-page-user-info-picture-container"
										 title="Change or add profile picture">
										<img src={profileUser.profilePictureUrl}
											 alt="user profile"
											 className="profile-page-user-info-picture" />
										{profileUser.email === user.email && (
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
												paramUserEmail={userEmail}
												user={user}
											/>

											{["Edit", "Pending", "Chat", "Add"].includes(friendButtonText) && (
												<button
													className="edit-profile-button"
													id={friendButtonText}
													onClick={handleFriendShip}
												>
													<div className="profile-page-pending-chat-add-box">
														{friendButtonText === "Edit" && <AiFillEdit />}
														{friendButtonText === "Pending" && <MdOutlinePendingActions />}
														{friendButtonText === "Chat" && <BsChatDotsFill />}
														{friendButtonText === "Add" && <FaUserPlus />}
														&nbsp;{friendButtonText}
													</div>
												</button>
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
							<ProfileEditModal
								isOpen={isEditModalOpen}
								onClose={() => setIsEditModalOpen(false)}
								user={user}
								setProfileUser={setProfileUser}
							/>

							{message.length > 0 ? (
								<p>{message}</p>
							) : (
								posts.length === 0 ? (
									<div className="common-empty-message-text">
										🤔 This place looks a bit empty...
									</div>
								) : (
									posts.map((post) => (
										<Post key={post.id}
											  post={post}
											  user={user}
											  removePost={() => setPosts(posts.filter(p => p.id !== post.id))} />
									))
								)
							)}
						</div>
					) : (
						<p>Unable to load profile data.</p>
					)
				)}
			</div>
		</div>
	);
};

export default ProfilePage;
