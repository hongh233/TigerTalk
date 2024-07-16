import React, { useState, useEffect } from "react";
import { useSelector } from "react-redux";
import { useParams } from "react-router-dom";
import {
	FaCog,
	FaLock,
	FaTrashAlt,
	FaUsers,
	FaUnlock,
	FaUserPlus,
	FaUserMinus,
} from "react-icons/fa";
import GroupPost from "../components/GroupPost";
import Header from "../components/Header";
import "../assets/styles/GroupPage.css";
import NavBar from "../components/NavBar";
import PostCreation from "./../components/PostCreation";
// axios
import {
	handleGetGroupById,
	checkIsMember,
	handleJoinGroup,
	handleLeaveGroup,
	handleCreatePost,
	handleGetAllPost,
} from "./../axios/GroupAxios";

const ViewGroupPage = () => {
	const { groupId } = useParams();
	const userEmail = useSelector((state) => state.user.user.email);
	const [isPrivate, setIsPrivate] = useState(null);
	const [isMember, setIsMember] = useState(null);
	const [isCreator, setIsCreator] = useState(null);
	const [group, setGroup] = useState(null);
	const [posts, setPosts] = useState(null);
	const [reload, setReload] = useState(false);

	useEffect(() => {
		fetchGroupDetails();
	}, [userEmail, groupId]);

	useEffect(() => {
		fetchAllGroupPosts();
	}, [userEmail, reload]);
	const fetchAllGroupPosts = async () => {
		try {
			const data = await handleGetAllPost(groupId);
			setPosts(data);
		} catch (error) {
			console.error("Failed to fetch group posts", error);
		}
	};
	const fetchGroupDetails = async () => {
		try {
			const groupData = await handleGetGroupById(groupId);
			setGroup(groupData);
			setIsPrivate(groupData.private);

			// Check if the current user is the creator/admin
			if (groupData.groupCreatorEmail === userEmail) {
				setIsCreator(true);
			}

			// Check if the current user is a member
			const memberStatus = await checkIsMember(userEmail, groupId);
			setIsMember(memberStatus);
		} catch (error) {
			console.error("Failed to fetch group details", error);
		}
	};
	const joinGroup = async () => {
		try {
			await handleJoinGroup(userEmail, groupId);
			setIsMember(true);
		} catch (error) {
			console.error("Failed to join the group", error);
		}
	};

	const leaveGroup = async () => {
		try {
			const userMembership = await handleLeaveGroup(userMembership);
			setIsMember(false);
		} catch (error) {
			console.error("Failed to leave the group", error);
		}
	};

	const addPost = async (postContent, postImageURL, tags) => {
		try {
			if (!userEmail) {
				window.alert("User profile are not successfully loaded");
				return;
			}

			const newPost = {
				group: group,
				groupPostContent: postContent,
				groupPostSenderEmail: userEmail,
				groupPostPictureURL: postImageURL,
				timestamp: new Date().toISOString(),
			};

			// Save the new post to the database
			await handleCreatePost(newPost);
			window.alert("Group post posted successfully!");
			setPosts([newPost, ...posts]);
			setReload(!reload);
		} catch (error) {
			console.error("Failed to create a post", error);
		}
	};

	return (
		group && (
			<div className="group-page">
				<Header />
				<div className="group-container">
					<div className="group-nav">
						<NavBar />
					</div>

					<div className="group-content-container">
						<div className="group-details-header">
							<div className="group-background-image">
								<img
									src="https://mediaim.expedia.com/destination/7/bb1caab964e8be84036cd5ee7b05e787.jpg?impolicy=fcrop&w=1920&h=480&q=medium"
									alt="Group Background"
								/>
							</div>

							<div className="group-content-nav">
								<h1>
									{group.groupName}{" "}
									{isPrivate ? (
										<FaLock className="status-icon" />
									) : (
										<FaUnlock className="status-icon" />
									)}
								</h1>
								{isCreator && (
									<ul>
										<li className="members">
											<a
												className="group-link"
												href={`/group/${groupId}/members`}
											>
												<FaUsers />
											</a>
										</li>

										<>
											<li className="setting">
												<a
													className="group-link"
													href={`/group/${groupId}/setting`}
												>
													<FaCog />
												</a>
											</li>
											<li className="delete">
												<FaTrashAlt />
											</li>
										</>
									</ul>
								)}
							</div>
						</div>
						<div className="group-member-actions-container">
							{isMember ? (
								<>
									<div className="group-member-actions">
										<span className="member-status">
											You are {isCreator ? "the admin" : "a member"}
										</span>
										<button onClick={leaveGroup} className="leave-group-button">
											<FaUserMinus /> Leave Group
										</button>
									</div>
								</>
							) : isPrivate ? (
								<p>This is a private group. You cannot join.</p>
							) : (
								<button onClick={joinGroup} className="join-group-button">
									<FaUserPlus /> Join Group
								</button>
							)}
						</div>

						<div className="group-post-container">
							<PostCreation addPost={addPost} />
							{!isPrivate ||
								(isMember && (
									<>
										{posts.map((post) => (
											<GroupPost key={post.id} post={post} />
										))}
									</>
								))}
						</div>
					</div>
				</div>
			</div>
		)
	);
};

export default ViewGroupPage;
