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
)

export default App;


