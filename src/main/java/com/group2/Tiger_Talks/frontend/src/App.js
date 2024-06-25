import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import LoginPage from './pages/LoginPage';
import SignUpPage from './pages/SignUpPage';
import MainPage from './pages/MainPage';
import ProfilePage from './pages/ProfilePage';
import GroupPage from "./pages/GroupPage";
import CreateGroupPage from './pages/CreateGroupPage';
import ProfileSettingsPage from './pages/ProfileSettingsPage';
import ForgotPassword from './pages/ForgotPasswordPage'
import ViewGroupPage from './pages/ViewGroupPage';
import './assets/styles/App.css';
import Friends from './pages/Friends';
import LoginPost from './pages/LoginPost';
import LandingPost from './pages/LandingPost'


//const App = () => (
const App = () => {  
  
  const [isLoggedIn, setIsLoggedIn] = useState(false);

    useEffect(() => {
        const email = localStorage.getItem('userEmail');
        if (email) {
            setIsLoggedIn(true);
        }
    }, []);

    const handleLogin = (email) => {
        localStorage.setItem('userEmail', email);
        setIsLoggedIn(true);
    };

  return(  

  <Router>
    <Routes>
      <Route path="/" element={<MainPage />} />
      <Route path="/login" element={<LoginPage />} />
      <Route path="/signup" element={<SignUpPage />} />
      <Route path="/profile" element={<ProfilePage />} />
      <Route path="/profile/1" element={<ProfileSettingsPage />} />
      <Route path="group/" element={<GroupPage />} />
      <Route path="/group/creategroup" element={<CreateGroupPage />} />
      <Route path="/group/viewgroup/:groupId" element={<ViewGroupPage />} />
      <Route path="/forgot-password" element={<ForgotPassword/>}/>
      <Route path="/friends" element={<Friends />} />
      <Route path="/LoginPost" element={<LoginPost />} />
      <Route path="/LandingPost" element={<LandingPost />} />
    </Routes>
  </Router>
);

};

export default App;
