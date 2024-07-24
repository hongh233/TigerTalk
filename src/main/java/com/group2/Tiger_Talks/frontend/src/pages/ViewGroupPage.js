import React, { useState, useEffect } from "react";
import { useSelector } from "react-redux";
import { useNavigate, useParams } from "react-router-dom";
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
	handleGetGroupMembershipId,
	handleDeleteGroup,
} from "./../axios/GroupAxios";

const ViewGroupPage = () => {
	const { groupId } = useParams();
	const navigate = useNavigate();
	const userEmail = useSelector((state) => state.user.user.email);
	const [isPrivate, setIsPrivate] = useState(null);
	const [isMember, setIsMember] = useState(null);
	const [isCreator, setIsCreator] = useState(null);
	const [groupMembershipId, setGroupMembershipId] = useState(null);
	const [group, setGroup] = useState(null);
	const [posts, setPosts] = useState(null);
	const [reload, setReload] = useState(false);

	useEffect(() => {
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
				const groupMembership = await handleGetGroupMembershipId(
					userEmail,
					groupId
				);
				setIsMember(memberStatus);
				setGroupMembershipId(groupMembership);
			} catch (error) {
				console.error("Failed to fetch group details", error);
			}
		};
		fetchGroupDetails();
	}, [userEmail, groupId, isMember]);

	useEffect(() => {
		const fetchAllGroupPosts = async () => {
			try {
				const data = await handleGetAllPost(groupId);

				setPosts(data);
			} catch (error) {
				console.error("Failed to fetch group posts", error);
			}
		};
		fetchAllGroupPosts();
	}, [userEmail, reload, isMember, groupId]);

	const joinGroup = async () => {
		try {
			if (window.confirm(`Are you sure you want to join this group?`)) {
				if (!isMember) {
					await handleJoinGroup(userEmail, groupId);
					window.alert("Leave group successfully!");
					setIsMember(true);
				}
			}
		} catch (error) {
			console.error("Failed to join the group", error);
		}
	};

	const leaveGroup = async () => {
		try {
			if (window.confirm(`Are you sure you want to leave this group?`)) {
				if (isMember) {
					await handleLeaveGroup(groupMembershipId);
					window.alert("Leave group successfully!");
					setIsMember(false);
					window.location.reload();
				}
			}
		} catch (error) {
			console.error("Failed to leave the group", error);
		}
	};

	const deleteGroup = async () => {
		if (
			window.confirm(
				"Are you sure you want to delete this group? This action cannot be undone."
			)
		) {
			try {
				await handleDeleteGroup(groupId);
				alert("Group deleted successfully!");
				navigate("/group"); // Navigate to a different page after deletion
			} catch (error) {
				console.error("Failed to delete the group", error);
				alert("Error deleting group");
			}
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
	const handleDeletePost = (postId) => {
        setPosts(posts.filter(post => post.groupPostId !== postId));
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
								<img src={group.groupImg} alt="Group Background" />
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
											<li className="delete group-link" onClick={deleteGroup}>
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
							{isMember && <PostCreation addPost={addPost} />}
							{!isPrivate || isMember ? (
								<>
									{posts &&
										posts.map((post) => (
											<GroupPost
												key={post.groupPostId}
												isMember={isMember}
												post={post}
												groupId={groupId}
												groupMembershipId={groupMembershipId}
												userEmail={userEmail}
												removePost={handleDeletePost}
											/>
										))}
								</>
							) : (
								""
							)}
						</div>
					</div>
				</div>
			</div>
		)
	);
};

export default ViewGroupPage;
