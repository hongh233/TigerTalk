import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
//PAGES
import LoginPage from "./pages/LoginPage";
import SignUpPage from "./pages/SignUpPage";
import MainPage from "./pages/MainPage";
import ProfilePage from "./pages/ProfilePage";
import GroupPage from "./pages/GroupPage";
import CreateGroupPage from "./pages/CreateGroupPage";
import ProfileSettingsPage from "./pages/ProfileSettingsPage";
import ForgotPasswordPage from "./pages/ForgotPasswordPage";
import SecurityQuestionsPage from "./pages/SecurityQuestionsPage";
import EmailVerificationPage from "./pages/EmailVerificationPage";
import ViewGroupPage from "./pages/ViewGroupPage";
import ResetPasswordPage from "./pages/ResetPasswordPage";
//COTEXT
import { UserProvider } from "./context/UserContext";
import "./assets/styles/App.css";

import PostCreation from "./components/PostCreation";
import Post from "./components/Post";

const App = () => (
	<UserProvider>
		<Router>
			<Routes>

				<Route path="/main" element={<MainPage />} />
				<Route path="/" element={<LoginPage />} />
				<Route path="/signup" element={<SignUpPage />} />
				<Route path="/profile" element={<ProfilePage />} />
				<Route path="/profile/1" element={<ProfileSettingsPage />} />
				<Route path="group/" element={<GroupPage />} />
				<Route path="/group/creategroup" element={<CreateGroupPage />} />
				<Route path="/group/viewgroup/:groupId" element={<ViewGroupPage />} />
				<Route path="/forgotPassword" element={<ForgotPasswordPage />} />
				<Route path="/securityQuestions" element={<SecurityQuestionsPage />} />
				<Route path="/emailVerification" element={<EmailVerificationPage />} />
				<Route path="/resetPassword" element={<ResetPasswordPage />} />
			</Routes>
		</Router>
	</UserProvider>
);

const MainPageWithPosts = () => {
	const [posts, setPosts] = React.useState([]);
  
	const addPost = (postContent, tags) => {
	  const newPost = {
		content: postContent,
		tags: tags,
		username: 'Current User', // Replace with actual username logic
		time: new Date().toLocaleString(),
		likes: 0,
		comments: [],
	  };
	  setPosts([newPost, ...posts]);
	};
  
	return (
	  <MainPageContent posts={posts} addPost={addPost} />
	);
  };
  
  const MainPageContent = ({ posts, addPost }) => (
	<div className="main-page">
	  <div className="post-creation-section">
		<PostCreation addPost={addPost} />
	  </div>
	  <div className="post-list">
		{posts.map((post, index) => (
		  <Post key={index} post={post} />
		))}
	  </div>
	</div>
  );

  

export default App;


