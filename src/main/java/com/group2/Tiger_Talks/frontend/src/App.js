import React, {useEffect, useState} from "react";
import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
//PAGES
import LoginPage from "./pages/LoginPage";
import SignUpPage from "./pages/SignUpPage";
import MainPage from "./pages/MainPage";
import ProfilePage from "./pages/ProfilePage";
import GroupPage from "./pages/GroupPage";
import CreateGroupPage from "./pages/CreateGroupPage";
import AdminPage from "./pages/AdminPage";
import ProfileSettingsPage from "./pages/ProfileSettingsPage";
import ForgotPasswordPage from "./pages/ForgotPasswordPage";
import SecurityQuestionsPage from "./pages/SecurityQuestionsPage";
import EmailVerificationPage from "./pages/EmailVerificationPage";
import FriendRequestPage from "./pages/FriendRequestPage";
import FriendListPage from "./pages/FriendListPage";
import ViewGroupPage from "./pages/ViewGroupPage";
import ResetPasswordPage from "./pages/ResetPasswordPage";
import AuthenticationFailPage from "./pages/AuthenticationFailPage";
//REDUX
import {useSelector} from "react-redux";

import "./assets/styles/App.css";

const App = () => {
    return <AppRoutes/>;
};

const AppRoutes = () => {
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const user = useSelector((state) => state.user.user);
    useEffect(() => {
        setIsLoggedIn(!!user);
    }, [user]);

    return (
        <Router>
            <Routes>
                <Route
                    path="/main"
                    element={isLoggedIn ? <MainPage/> : <AuthenticationFailPage/>}
                />
                <Route path="/" element={<LoginPage/>}/>
                <Route path="/signup" element={<SignUpPage/>}/>

                <Route
                    path={`/profile/:userEmail`}
                    element={isLoggedIn ? <ProfilePage/> : <AuthenticationFailPage/>}
                />
                <Route
                    path="/profile/edit"
                    element={
                        isLoggedIn ? <ProfileSettingsPage/> : <AuthenticationFailPage/>
                    }
                />
                <Route
                    path="/group"
                    element={isLoggedIn ? <GroupPage/> : <AuthenticationFailPage/>}
                />
                <Route
                    path="/group/creategroup"
                    element={
                        isLoggedIn ? <CreateGroupPage/> : <AuthenticationFailPage/>
                    }
                />
                <Route
                    path="/group/viewgroup/:groupId"
                    element={isLoggedIn ? <ViewGroupPage/> : <AuthenticationFailPage/>}
                />
                {/* FRIENDS ROUTE */}
                <Route
                    path="/friends/friend-request-list"
                    element={
                        isLoggedIn ? <FriendRequestPage/> : <AuthenticationFailPage/>
                    }
                />
                <Route
                    path="/friends/friend-list"
                    element={isLoggedIn ? <FriendListPage/> : <AuthenticationFailPage/>}
                />
                <Route
                    path="/admin"
                    element={isLoggedIn ? <AdminPage/> : <AuthenticationFailPage/>}
                />
                <Route path="/forgotPassword" element={<ForgotPasswordPage/>}/>
                <Route path="/securityQuestions" element={<SecurityQuestionsPage/>}/>
                <Route path="/emailVerification" element={<EmailVerificationPage/>}/>
                <Route path="/resetPassword" element={<ResetPasswordPage/>}/>
            </Routes>
        </Router>
    );
};

export default App;
