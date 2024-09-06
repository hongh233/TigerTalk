import React, { useEffect, useState } from "react";
import "./assets/styles/App.css";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
//PAGES
import LoginPage from "./Pages/Authentication/LoginPage";
import SignUpPage from "./Pages/Authentication/SignUpPage";
import MainPage from "./Pages/MainPage";
import ProfilePage from "./Pages/Profile/ProfilePage";
import GroupPage from "./Pages/Group/GroupPage";
import AdminPage from "./Pages/Admin/AdminPage";
import AdminAddPage from "./Pages/Admin/AdminAddPage";
import ForgotPasswordPage from "./Pages/Authentication/ForgotPasswordPage";
import FriendPage from "./Pages/Friend/FriendPage";
import ViewGroupPage from "./Pages/Group/ViewGroupPage";
import AuthenticationFailPage from "./Pages/FailPage/AuthenticationFailPage";
import FriendMessagePage from "./Pages/Friend/FriendMessagePage";
import SearchPage from "./Pages/Search/SearchPage";
//REDUX
import { useSelector } from "react-redux";

import ValidationFailPage from "./Pages/FailPage/ValidationFailPage";
import AdminFailPage from "./Pages/FailPage/AdminFailPage";
import SecurityPage from "./Pages/Profile/SecurityPage";
import Header from "./Components/Main/Header";
import NavBar from "./Components/Main/Navbar";

const App = () => {
	const user = useSelector((state) => state.user.user);
	const isLoggedIn = !!user;

	return (
		<Router>
			<div className={`${isLoggedIn ? 'app-container' : 'no-navbar-header'}`}>
				{isLoggedIn && <NavBar />}
				<div className={`${isLoggedIn ? 'main-container' : 'no-navbar-header'}`}>
					{isLoggedIn && <Header />}
					<AppRoutes isLoggedIn={isLoggedIn} />
				</div>
			</div>
		</Router>
	);
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
		<Routes>
			<Route path="/main" element={
				isLoggedIn ? (isValidated ? (<MainPage />) : (<ValidationFailPage />)
				) : (<AuthenticationFailPage />)}
			/>
			<Route path="/" element={<LoginPage />} />
			<Route path="/signup" element={<SignUpPage />} />

			<Route path={`/profile/:userEmail`} element={
				isLoggedIn ? (isValidated ? (<ProfilePage />) : (<ValidationFailPage />)
				) : (<AuthenticationFailPage />)}
			/>
			<Route path="/group" element={
				isLoggedIn ? (isValidated ? (<GroupPage />) : (<ValidationFailPage />)
				) : (<AuthenticationFailPage />)}
			/>
			<Route path="/group/viewgroup/:groupId" element={
				isLoggedIn ? (isValidated ? (<ViewGroupPage />) : (<ValidationFailPage />)
				) : (<AuthenticationFailPage />)}
			/>

			{/* FRIENDS ROUTE */}
			<Route path="/friends/friend-list" element={
				isLoggedIn ? (isValidated ? (<FriendPage />) : (<ValidationFailPage />)
				) : (<AuthenticationFailPage />)}
			/>
			<Route path="/friends/message" element={
				isLoggedIn ? (isValidated ? (<FriendMessagePage />) : (<ValidationFailPage />)
				) : (<AuthenticationFailPage />)}
			/>

			<Route path="/search" element={
				isLoggedIn ? (isValidated ? (<SearchPage />) : (<ValidationFailPage />)
				) : (<AuthenticationFailPage />)}
			/>

			<Route path="/security" element={
				isLoggedIn ? (isValidated ? (<SecurityPage />): (<ValidationFailPage />)
				) : (<AuthenticationFailPage />)}
			/>

			{/* ADMIN */}
			<Route path="/admin" element={
					isLoggedIn ? (
						isValidated ? (
							isAdmin ? (<AdminPage />) : (<AdminFailPage />)
						) : (<ValidationFailPage />)
					) : (<AuthenticationFailPage />)}
			/>
			<Route path="/admin/add" element={
					isLoggedIn ? (
						isValidated ? (
							isAdmin ? (<AdminAddPage />) : (<AdminFailPage />)
						) : (<ValidationFailPage />)
					) : (<AuthenticationFailPage />)}
			/>
			<Route path="/forgotPassword" element={<ForgotPasswordPage />} />
		</Routes>
	);
};

export default App;
