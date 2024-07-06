import React from "react";
import { FaCog, FaLock, FaTrashAlt, FaUsers } from "react-icons/fa"; // Importing icons
import Post from "../components/Post";
import Header from "../components/Header";
import "../assets/styles/GroupPage.css";
import NavBar from "../components/NavBar";

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

const ViewGroupPage = () => (
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
						Group 1 <FaLock className="status-icon" />
					</h1>

					<ul>
						<li className="members">
							<a className="group-link" href="/group/1/members">
								<FaUsers />
							</a>
						</li>
						<li className="setting">
							<a className="group-link" href="/group/1/setting">
								<FaCog />
							</a>
						</li>
						<li className="delete">
							<FaTrashAlt />
						</li>
					</ul>
				</div>
				<div className="group-post">
					{posts.map((post) => (
						<Post key={post.id} post={post} />
					))}
				</div>
			</div>
		</div>
	</div>
);

export default ViewGroupPage;
