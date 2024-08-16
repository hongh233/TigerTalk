import React, { useEffect, useState } from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
//PAGES
import LoginPage from "./Pages/Authentication/LoginPage";
import SignUpPage from "./Pages/Authentication/SignUpPage";
import MainPage from "./Pages/MainPage";
import ProfilePage from "./Pages/Profile/ProfilePage";
import GroupPage from "./Pages/Group/GroupPage";
import CreateGroupPage from "./Pages/Group/CreateGroupPage";
import AdminPage from "./Pages/Admin/AdminPage";
import AdminAddPage from "./Pages/Admin/AdminAddPage";
import ProfileSettingsPage from "./Pages/Profile/ProfileSettingsPage";
import ForgotPasswordPage from "./Pages/Authentication/ForgetPassword/ForgotPasswordPage";
import SecurityQuestionsPage from "./Pages/Authentication/ForgetPassword/SecurityQuestionsPage";
import EmailVerificationPage from "./Pages/Authentication/ForgetPassword/EmailVerificationPage";
import FriendRequestPage from "./Pages/Friend/FriendRequestPage";
import FriendListPage from "./Pages/Friend/FriendListPage";
import ViewGroupPage from "./Pages/Group/ViewGroupPage";
import ResetPasswordPage from "./Pages/Authentication/ForgetPassword/ResetPasswordPage";
import AuthenticationFailPage from "./Pages/FailPage/AuthenticationFailPage";
import GroupSettingPage from "./Pages/Group/GroupSettingPage";
import GroupMemberPage from "./Pages/Group/GroupMembersPage";
import FriendMessagePage from "./Pages/Friend/FriendMessagePage";
import SearchPage from "./Pages/Search/SearchPage";
//REDUX
import { useSelector } from "react-redux";

import "./assets/styles/App.css";
import ValidationFailPage from "./Pages/FailPage/ValidationFailPage";
import AdminFailPage from "./Pages/FailPage/AdminFailPage";

const App = () => {
	return <AppRoutes />;
};

const AppRoutes = () => {
	const [isLoggedIn, setIsLoggedIn] = useState(false);
	const user = useSelector((state) => state.user.user);

	useEffect(() => {
		setIsLoggedIn(!!user);
	}, [user]);
	const isValidated = user && user.validated;
	const isAdmin = user && user.userLevel === "admin";
	return (
		<Router>
			<Routes>
				<Route
					path="/main"
					element={
						isLoggedIn ? (
							isValidated ? (
								<MainPage />
							) : (
								<ValidationFailPage />
							)
						) : (
							<AuthenticationFailPage />
						)
					}
				/>
				<Route path="/" element={<LoginPage />} />
				<Route path="/signup" element={<SignUpPage />} />

				<Route
					path={`/profile/:userEmail`}
					element={
						isLoggedIn ? (
							isValidated ? (
								<ProfilePage />
							) : (
								<ValidationFailPage />
							)
						) : (
							<AuthenticationFailPage />
						)
					}
				/>
				<Route
					path="/profile/edit"
					element={
						isLoggedIn ? (
							isValidated ? (
								<ProfileSettingsPage />
							) : (
								<ValidationFailPage />
							)
						) : (
							<AuthenticationFailPage />
						)
					}
				/>
				<Route
					path="/group"
					element={
						isLoggedIn ? (
							isValidated ? (
								<GroupPage />
							) : (
								<ValidationFailPage />
							)
						) : (
							<AuthenticationFailPage />
						)
					}
				/>
				<Route
					path="/group/creategroup"
					element={
						isLoggedIn ? (
							isValidated ? (
								<CreateGroupPage />
							) : (
								<ValidationFailPage />
							)
						) : (
							<AuthenticationFailPage />
						)
					}
				/>
				<Route
					path="/group/:groupId/setting"
					element={
						isLoggedIn ? (
							isValidated ? (
								<GroupSettingPage />
							) : (
								<ValidationFailPage />
							)
						) : (
							<AuthenticationFailPage />
						)
					}
				/>
				<Route
					path="/group/viewgroup/:groupId"
					element={
						isLoggedIn ? (
							isValidated ? (
								<ViewGroupPage />
							) : (
								<ValidationFailPage />
							)
						) : (
							<AuthenticationFailPage />
						)
					}
				/>
				<Route
					path="/group/:groupId/members"
					element={
						isLoggedIn ? (
							isValidated ? (
								<GroupMemberPage />
							) : (
								<ValidationFailPage />
							)
						) : (
							<AuthenticationFailPage />
						)
					}
				/>
				{/* FRIENDS ROUTE */}
				<Route
					path="/friends/friend-request-list"
					element={
						isLoggedIn ? (
							isValidated ? (
								<FriendRequestPage />
							) : (
								<ValidationFailPage />
							)
						) : (
							<AuthenticationFailPage />
						)
					}
				/>
				<Route
					path="/friends/friend-list"
					element={
						isLoggedIn ? (
							isValidated ? (
								<FriendListPage />
							) : (
								<ValidationFailPage />
							)
						) : (
							<AuthenticationFailPage />
						)
					}
				/>
				<Route
					path="/friends/message"
					element={
						isLoggedIn ? (
							isValidated ? (
								<FriendMessagePage />
							) : (
								<ValidationFailPage />
							)
						) : (
							<AuthenticationFailPage />
						)
					}
				/>

				<Route
					path="/search"
					element={
						isLoggedIn ? (
							isValidated ? (
								<SearchPage />
							) : (
								<ValidationFailPage />
							)
						) : (
							<AuthenticationFailPage />
						)
					}
				/>
				{/* ADMIN */}
				<Route
					path="/admin"
					element={
						isLoggedIn ? (
							isValidated ? (
								isAdmin ? (
									<AdminPage />
								) : (
									<AdminFailPage />
								)
							) : (
								<ValidationFailPage />
							)
						) : (
							<AuthenticationFailPage />
						)
					}
				/>
				<Route
					path="/admin/add"
					element={
						isLoggedIn ? (
							isValidated ? (
								isAdmin ? (
									<AdminAddPage />
								) : (
									<AdminFailPage />
								)
							) : (
								<ValidationFailPage />
							)
						) : (
							<AuthenticationFailPage />
						)
					}
				/>
				<Route path="/forgotPassword" element={<ForgotPasswordPage />} />
				<Route path="/securityQuestions" element={<SecurityQuestionsPage />} />
				<Route path="/emailVerification" element={<EmailVerificationPage />} />
				<Route path="/resetPassword" element={<ResetPasswordPage />} />
			</Routes>
		</Router>
	);
};

export default App;
