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
import Post from "../components/Post";
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
} from "./../axios/GroupAxios";

const posts = [
	{
		id: 1,
		userName: "User",
		time: "2 hours ago",
		content:
			"This is an example post content to demonstrate the post layout. It can include text, images, and other media.",
	},
	{
		id: 2,
		userName: "User",
		time: "1 day ago",
		content:
			"Another example post to show how multiple posts would look on the view group page.",
	},
];

const ViewGroupPage = () => {
	const { groupId } = useParams();
	const userEmail = useSelector((state) => state.user.user.email);
	const [isPrivate, setIsPrivate] = useState(null);
	const [isMember, setIsMember] = useState(null);
	const [isCreator, setIsCreator] = useState(null);
	const [group, setGroup] = useState(null);

	useEffect(() => {
		fetchGroupDetails();
	}, [userEmail, groupId]);

	const fetchGroupDetails = async () => {
		try {
			const groupData = await handleGetGroupById(groupId);
			console.log(groupData);
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
			await handleLeaveGroup(userEmail);
			setIsMember(false);
		} catch (error) {
			console.error("Failed to leave the group", error);
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

						<div className="group-post">
							{isMember ? (
								<>
									<div className="member-actions">
										<span className="member-status">
											You are {isCreator ? "the admin" : "a member"}
										</span>
										<button onClick={leaveGroup} className="leave-group-button">
											<FaUserMinus /> Leave Group
										</button>
									</div>
									<PostCreation />
									{posts.map((post) => (
										<Post key={post.id} post={post} />
									))}
								</>
							) : isPrivate ? (
								<p>This is a private group. You cannot join.</p>
							) : (
								<button onClick={joinGroup} className="join-group-button">
									<FaUserPlus /> Join Group
								</button>
							)}

							{!isPrivate && (
								<>
									{posts.map((post) => (
										<Post key={post.id} post={post} />
									))}
								</>
							)}
						</div>
					</div>
				</div>
			</div>
		)
	);
};

export default ViewGroupPage;
