import React, {useEffect,useState} from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import LoginPage from './pages/LoginPage';
import SignUpPage from './pages/SignUpPage';
import MainPage from './pages/MainPage';
import ProfilePage from './pages/ProfilePage';
import GroupPage from "./pages/GroupPage";
import CreateGroupPage from './pages/CreateGroupPage';
import ProfileSettingsPage from './pages/ProfileSettingsPage';
import ForgotPasswordPage from './pages/ForgotPasswordPage';
import SecurityQuestionsPage from './pages/SecurityQuestionsPage';
import EmailVerificationPage from './pages/EmailVerificationPage';
import ViewGroupPage from './pages/ViewGroupPage';
import ResetPasswordPage from './pages/ResetPasswordPage';
import './assets/styles/App.css';
import AuthenticationFailPage from './pages/AuthenticationFailPage';

const App = () => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  useEffect(() => {
    const user = localStorage.getItem('user');
    setIsLoggedIn(!!user);
  }, []);

  
  return (
    <Router>
      <Routes>
        <Route path="/main" element={isLoggedIn ? <MainPage /> : <AuthenticationFailPage />} />
        <Route path="/" element={<LoginPage setIsLoggedIn={setIsLoggedIn} />} />
        <Route path="/signup" element={<SignUpPage />} />
        <Route path="/profile" element={isLoggedIn ? <ProfilePage /> : <AuthenticationFailPage />} />
        <Route path="/profile/1" element={isLoggedIn ? <ProfileSettingsPage /> : <AuthenticationFailPage />} />
        <Route path="/group" element={isLoggedIn ? <GroupPage /> : <AuthenticationFailPage />} />
        <Route path="/group/creategroup" element={isLoggedIn ? <CreateGroupPage /> : <AuthenticationFailPage />} />
        <Route path="/group/viewgroup/:groupId" element={isLoggedIn ? <ViewGroupPage /> : <AuthenticationFailPage />} />
        <Route path="/forgotPassword" element={<ForgotPasswordPage />} />
        <Route path="/securityQuestions" element={<SecurityQuestionsPage />} />
        <Route path="/emailVerification" element={<EmailVerificationPage />} />
        <Route path="/resetPassword" element={<ResetPasswordPage />} />
      </Routes>
    </Router>
  );
};

export default App;
