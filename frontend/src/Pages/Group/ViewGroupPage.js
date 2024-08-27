import React, {useState, useEffect, useRef} from "react";
import "../../assets/styles/Pages/Group/ViewGroupPage.css";
import {
	handleJoinGroup,
	handleDeleteGroup,
	handleGetGroupById,
	handleGetMembershipID,
	handleDeleteGroupMembership, handleGetGroupMembersByGroupId,
} from "../../axios/Group/GroupAxios";
import {
	handleGetAllPost,
	handleCreatePost,
} from "../../axios/Group/GroupPostAxios";
import { FaLock, FaUsers, FaUnlock, FaUserPlus } from "react-icons/fa";
import { useSelector } from "react-redux";
import { useNavigate, useParams } from "react-router-dom";
import GroupPost from "../../Components/Group/GroupPost";
import Header from "../../Components/Main/Header";
import PostCreation from "../../Components/Post/PostCreation";
import GroupMemberModal from "../../Components/Group/GroupMemberModal";
import GroupSettingModal from "../../Components/Group/GroupSettingModal";
import { IoIosMore } from "react-icons/io";
import GroupMore from "../../Components/Group/GroupMore";


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
	const [showMembersModal, setShowMembersModal] = useState(false);
	const [showSettingsModal, setShowSettingsModal] = useState(false);
	const [showMoreOptions, setShowMoreOptions] = useState(false);
	const moreOptionsRef = useRef(null);
	const [members, setMembers] = useState(null);


	useEffect(() => {
		if (groupId) {
			const getAllMembers = async () => {
				try {
					const data = await handleGetGroupMembersByGroupId(groupId);
					setMembers(data);
				} catch (error) {
					console.error(error);
				}
			};
			getAllMembers();
		}
	}, []);

	useEffect(() => {
		const handleClickOutside = (event) => {
			if (moreOptionsRef.current && !moreOptionsRef.current.contains(event.target)) {
				setShowMoreOptions(false);
				setShowMembersModal(false);
			}
		};
		document.addEventListener("mousedown", handleClickOutside);
		return () => {
			document.removeEventListener("mousedown", handleClickOutside);
		};
	}, [moreOptionsRef]);


	useEffect(() => {
		const fetchGroupDetails = async () => {
			try {
				const groupData = await handleGetGroupById(groupId);
				setGroup(groupData);
				setIsPrivate(groupData.isPrivate);

				// Check if the current user is the creator/admin
				if (groupData.groupCreatorEmail === userEmail) {
					setIsCreator(true);
				}

				// Check if the current user is a member
				try {
					const groupMembership = await handleGetMembershipID(userEmail, groupId);
					setIsMember(true);
					setGroupMembershipId(groupMembership);
				} catch {
					setIsMember(false);
				}

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
				if (isMember && !isCreator) {
					await handleDeleteGroupMembership(groupMembershipId);
					window.alert("Leave group successfully!");
					setIsMember(false);
					navigate("/group");
				} else if (isMember && isCreator) {
					await handleDeleteGroup(groupId);
					window.alert("Delete group successfully!");
					navigate("/group");
				}
			}
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

	const handleDeletePost = (postId) => {
		setPosts(posts.filter((post) => post.groupPostId !== postId));
	};



	return (
		group && (
			<div className="main-page">
				<Header />
				<div className="content">

					<div className="group-content-container">

						<div className="group-details-header">
							<div className="group-background-image">
								<img src={group.groupImg} alt="Group Background" />
							</div>

							<div className="group-content-info">


								<div className="group-name-and-status-icon-and-member-more-box">

									<div className="group-name-and-status-icon-left">
										<h1>{group.groupName}</h1>
										{isPrivate ? (<FaLock />) : (<FaUnlock />)}
									</div>

									{isCreator ? (
										<div className="group-member-and-more-icon-right" ref={moreOptionsRef}>
											<div className="group-member-list-button-and-menu-show">
												<FaUsers className="group-icon-button-view-group-page"
														 onClick={() => {
															 setShowMembersModal(!showMembersModal);
															 setShowMoreOptions(false);
														 }}
												/>
												{showMembersModal && (
													<GroupMemberModal
														groupId={groupId}
														onClose={() => setShowMembersModal(false)}
													/>
												)}
											</div>
											<IoIosMore className="group-icon-button-view-group-page"
													   onClick={() => {
														   setShowMoreOptions(!showMoreOptions)
														   setShowMembersModal(false)
													   }}
											/>
											{showMoreOptions && (
												<GroupMore
													isCreator={isCreator}
													isMember={isMember}
													leaveGroup={leaveGroup}
													setShowSettingsModal={setShowSettingsModal}
													setShowMoreOptions={setShowMoreOptions}
													groupMembershipList={members}
													previousOwnerMembershipId={groupMembershipId}
												/>
											)}
										</div>
									) : isMember ? (
										<div className="group-member-and-more-icon-right" ref={moreOptionsRef}>
											<div className="group-member-list-button-and-menu-show">
												<FaUsers className="group-icon-button-view-group-page"
														 onClick={() => {
															 setShowMembersModal(!showMembersModal);
															 setShowMoreOptions(false);
														 }}
												/>
												{showMembersModal && (
													<GroupMemberModal
														groupId={groupId}
														onClose={() => setShowMembersModal(false)}
													/>
												)}
											</div>
											<IoIosMore className="group-icon-button-view-group-page"
													   onClick={() => {
														   setShowMoreOptions(!showMoreOptions)
														   setShowMembersModal(false)
													   }}
											/>
											{showMoreOptions && (
												<GroupMore
													isCreator={isCreator}
													isMember={isMember}
													leaveGroup={leaveGroup}
													setShowSettingsModal={setShowSettingsModal}
												/>
											)}
										</div>
									) : isPrivate ? (
										<p>This is a private group. You cannot join.</p>
									) : (
										<button onClick={joinGroup} className="join-group-button"><FaUserPlus /> Join Group</button>
									)}


								</div>



							</div>
						</div>

						<div className="group-post-container">

							{isMember && <PostCreation addPost={addPost} />}

							{!isPrivate || isMember ? (
								<>
									{posts && posts.map((post) => (
											<GroupPost key={post.groupPostId} isMember={isMember} post={post} groupId={groupId}
												groupMembershipId={groupMembershipId} userEmail={userEmail} removePost={handleDeletePost}
											/>
										))
									}
								</>
							) : ("")}

						</div>

					</div>
				</div>


				{showSettingsModal && (
					<GroupSettingModal
						groupId={groupId}
						onClose={() => setShowSettingsModal(false)}
					/>
				)}
			</div>
		)
	);
};

export default ViewGroupPage;
