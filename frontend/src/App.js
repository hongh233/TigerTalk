import React, { useEffect, useState } from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
//PAGES
import LoginPage from "./pages/Authentication/SignUpAndLogin/LoginPage";
import SignUpPage from "./pages/Authentication/SignUpAndLogin/SignUpPage";
import MainPage from "./pages/MainPage";
import ProfilePage from "./pages/Profile/ProfilePage";
import GroupPage from "./pages/Group/GroupPage";
import CreateGroupPage from "./pages/Group/CreateGroupPage";
import AdminPage from "./pages/Admin/AdminPage";
import AdminAddPage from "./pages/Admin/AdminAddPage";
import ProfileSettingsPage from "./pages/Profile/ProfileSettingsPage";
import ForgotPasswordPage from "./pages/Authentication/ForgetPassword/ForgotPasswordPage";
import SecurityQuestionsPage from "./pages/Authentication/ForgetPassword/SecurityQuestionsPage";
import EmailVerificationPage from "./pages/Authentication/ForgetPassword/EmailVerificationPage";
import FriendRequestPage from "./pages/Friend/FriendRequestPage";
import FriendListPage from "./pages/Friend/FriendListPage";
import ViewGroupPage from "./pages/Group/ViewGroupPage";
import ResetPasswordPage from "./pages/Authentication/ForgetPassword/ResetPasswordPage";
import AuthenticationFailPage from "./pages/FailPage/AuthenticationFailPage";
import GroupSettingPage from "./pages/Group/GroupSettingPage";
import GroupMemberPage from "./pages/Group/GroupMembersPage";
import FriendMessagePage from "./pages/Friend/FriendMessagePage";
import SearchPage from "./pages/Search/SearchPage";
//REDUX
import { useSelector } from "react-redux";

import "./assets/styles/App.css";
import ValidationFailPage from "./pages/FailPage/ValidationFailPage";
import AdminFailPage from "./pages/FailPage/AdminFailPage";

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
