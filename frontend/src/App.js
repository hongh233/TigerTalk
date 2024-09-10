import React, { useEffect, useState } from "react";
import "./assets/styles/App.css";
import {BrowserRouter as Router, Route, Routes, useLocation} from "react-router-dom";
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
import ValidationFailPage from "./Pages/FailPage/ValidationFailPage";
import AdminFailPage from "./Pages/FailPage/AdminFailPage";
import SecurityPage from "./Pages/Profile/SecurityPage";
import Header from "./Components/Main/Header";
import NavBar from "./Components/Main/Navbar";

import {useDispatch, useSelector} from "react-redux";
import {clearUser, setUser} from "./redux/actions/userActions";

const App = () => {
	const user = useSelector((state) => state.user.user);
	const isLoggedIn = !!user;
	const dispatch = useDispatch();

	useEffect(() => {
		sessionStorage.setItem('isPageRefreshed', 'true');
		const handleBeforeUnload = () => {
			if (sessionStorage.getItem('isPageRefreshed') === 'true') {
				sessionStorage.removeItem('isPageRefreshed');
			} else {
				dispatch(clearUser());
			}
		};

		window.addEventListener('beforeunload', handleBeforeUnload);
		return () => {
			window.removeEventListener('beforeunload', handleBeforeUnload);
		};
	}, [dispatch]);

	useEffect(() => {
		const storedUser = localStorage.getItem('user');
		if (storedUser) {
			dispatch(setUser(JSON.parse(storedUser)));
		}
	}, [dispatch]);



	return (
		<Router>
			<div className={`${isLoggedIn ? 'app-container' : 'no-navbar-header'}`}>
				<AppRoutes isLoggedIn={isLoggedIn} />
			</div>
		</Router>
	);
};

const AppRoutes = () => {
	const [isLoggedIn, setIsLoggedIn] = useState(false);
	const user = useSelector((state) => state.user.user);
	const location = useLocation();


	useEffect(() => {
		setIsLoggedIn(!!user);
	}, [user]);

	const isValidated = user && user.validated;
	const isAdmin = user && user.userLevel === "admin";

	const noNavBarPaths = ["/", "/signup", "/forgotPassword"];
	const shouldShowNavBar = !noNavBarPaths.includes(location.pathname);

	return (
		<>
			{shouldShowNavBar && isLoggedIn && <NavBar />}
			<div className={`${isLoggedIn ? 'main-container' : 'no-navbar-header'}`}>
				{shouldShowNavBar && isLoggedIn && <Header />}
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
			</div>
		</>
	);
};

export default App;
