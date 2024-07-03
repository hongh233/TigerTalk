import React, {useEffect, useState} from "react";
import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
//PAGES
import LoginPage from "./pages/Authentication/LoginPage";
import SignUpPage from "./pages/Authentication/SignUpPage";
import MainPage from "./pages/MainPage";
import ProfilePage from "./pages/ProfilePage";
import GroupPage from "./pages/GroupPage";
import CreateGroupPage from "./pages/CreateGroupPage";
import AdminPage from "./pages/AdminPage";
import ProfileSettingsPage from "./pages/ProfileSettingsPage";
import ForgotPasswordPage from "./pages/ForgetPassword/ForgotPasswordPage";
import SecurityQuestionsPage from "./pages/ForgetPassword/SecurityQuestionsPage";
import EmailVerificationPage from "./pages/ForgetPassword/EmailVerificationPage";
import FriendRequestPage from "./pages/FriendRequestPage";
import FriendListPage from "./pages/FriendListPage";
import ViewGroupPage from "./pages/ViewGroupPage";
import ResetPasswordPage from "./pages/ForgetPassword/ResetPasswordPage";
import AuthenticationFailPage from "./pages/AuthenticationFailPage";
//REDUX
import {useSelector} from "react-redux";

import "./assets/styles/App.css";
import ValidationFailPage from "./pages/ValidationFailPage";
import AdminFailPage from "./pages/AdminFailPage";

const App = () => {
    return <AppRoutes/>;
};

const AppRoutes = () => {
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const user = useSelector((state) => state.user.user);
    console.log(user);
    useEffect(() => {
        setIsLoggedIn(!!user);
    }, [user]);

    return (
        <Router>
            <Routes>
                <Route
                    path="/main"
                    element={isLoggedIn ? (user.validated?<MainPage/>:<ValidationFailPage/>) : <AuthenticationFailPage/>}
                />
                <Route path="/" element={<LoginPage/>}/>
                <Route path="/signup" element={<SignUpPage/>}/>

                <Route
                    path={`/profile/:userEmail`}
                    element={isLoggedIn ? (user.validated?<ProfilePage/>:<ValidationFailPage/>) : <AuthenticationFailPage/>}
                />
                <Route
                    path="/profile/edit"
                    element={
                        isLoggedIn ? (user.validated?<ProfileSettingsPage/>:<ValidationFailPage/>) : <AuthenticationFailPage/>
                    }
                />
                <Route
                    path="/group"
                    element={isLoggedIn ? (user.validated?<GroupPage/>:<ValidationFailPage/>) : <AuthenticationFailPage/>}
                />
                <Route
                    path="/group/creategroup"
                    element={
                        isLoggedIn ? (user.validated?<CreateGroupPage/>:<ValidationFailPage/>) : <AuthenticationFailPage/>
                    }
                />
                <Route
                    path="/group/viewgroup/:groupId"
                    element={isLoggedIn ? (user.validated?<ViewGroupPage/>:<ValidationFailPage/>) : <AuthenticationFailPage/>}
                />
                {/* FRIENDS ROUTE */}
                <Route
                    path="/friends/friend-request-list"
                    element={
                        isLoggedIn ? (user.validated?<FriendRequestPage/>:<ValidationFailPage/>) : <AuthenticationFailPage/>
                    }
                />
                <Route
                    path="/friends/friend-list"
                    element={isLoggedIn ? (user.validated?<FriendListPage/>:<ValidationFailPage/>) : <AuthenticationFailPage/>}
                />
                <Route
                    path="/admin"
                    element={isLoggedIn ? 
                        (user.validated?(user.userLevel==="admin"?<AdminPage/>:<AdminFailPage/>) 
                        :<ValidationFailPage/>) 
                        : <AuthenticationFailPage/>}
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
