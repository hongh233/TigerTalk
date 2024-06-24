import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import LoginPage from './pages/LoginPage';
import SignUpPage from './pages/SignUpPage';
import MainPage from './pages/MainPage';
import ProfilePage from './pages/ProfilePage';
import GroupPage from "./pages/GroupPage";
import CreateGroupPage from './pages/CreateGroupPage';
import ProfileSettingsPage from './pages/ProfileSettingsPage';
import ForgotPasswordPage from './pages/ForgotPasswordPage'
import ViewGroupPage from './pages/ViewGroupPage';
import './assets/styles/App.css';

const App = () => (
  
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
      <Route path="/forgot-password" element={<ForgotPasswordPage/>}/>
    </Routes>
  </Router>
);

export default App;
