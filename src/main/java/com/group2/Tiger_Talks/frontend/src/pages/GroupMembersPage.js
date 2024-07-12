import React, {useState } from "react";
import { useSelector } from "react-redux";
import NavBar from "../components/NavBar";
import Header from "../components/Header";
import "../assets/styles/GroupMemberPage.css"; // New CSS file
import UserComponent from "../components/UserComponent";
import { FaUserPlus } from "react-icons/fa"; // Importing add user icon

const fakeMembers = [
	{
		id: 1,
		email: "member1@example.com",
		userName: "Member One",
		time: "Joined 2 days ago",
		profilePicture: "https://via.placeholder.com/50", // Placeholder image
	},
	{
		id: 2,
		email: "member2@example.com",
		userName: "Member Two",
		time: "Joined 1 week ago",
		profilePicture: "https://via.placeholder.com/50", // Placeholder image
	},
	{
		id: 3,
		email: "member3@example.com",
		userName: "Member Three",
		time: "Joined 3 days ago",
		profilePicture: "https://via.placeholder.com/50", // Placeholder image
	},
];

const GroupMemberPage = () => {
	const user = useSelector((state) => state.user.user);
	const [members, setMembers] = useState(fakeMembers);
	const [showAddUser, setShowAddUser] = useState(false);
	const [newMemberEmail, setNewMemberEmail] = useState("");

	const handleDeleteMember = (id) => {
		setMembers(members.filter((member) => member.id !== id));
	};

	const handleAddMember = () => {
		// Here you would typically send a request to the backend to add the new member
		const newMember = {
			id: members.length + 1,
			email: newMemberEmail,
			userName: `New Member ${members.length + 1}`,
			time: "Just now",
			profilePicture: "https://via.placeholder.com/50", // Placeholder image
		};
		setMembers([...members, newMember]);
		setNewMemberEmail("");
		setShowAddUser(false);
	};

	return (
		<div className="member-list-page">
			<Header />
			<div className="content">
				<div className="member-list-nav">
					<NavBar />
				</div>
				<div className="member-list-content">
					<div
						className="add-user-icon"
						onClick={() => setShowAddUser(!showAddUser)}
					>
						<FaUserPlus />
					</div>
					{showAddUser && (
						<div className="add-user-form">
							<input
								type="email"
								placeholder="Enter user email"
								value={newMemberEmail}
								onChange={(e) => setNewMemberEmail(e.target.value)}
							/>
							<button onClick={handleAddMember}>Add</button>
						</div>
					)}
					{members.length > 0 ? (
						members.map((member) => (
							<UserComponent
								key={member.email}
								user={member}
								userEmail={user.email}
								onDelete={handleDeleteMember}
							/>
						))
					) : (
						<div className="no-members">
							<p>This group has no members</p>
						</div>
					)}
				</div>
			</div>
		</div>
	);
};

export default GroupMemberPage;
