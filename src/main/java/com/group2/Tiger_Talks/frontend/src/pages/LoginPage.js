import React from 'react';
import { Link } from 'react-router-dom';
import '../assets/styles/Login.css';

const LoginPage = () => (
  <div className="login-container">
    <div className="login-card">
      <h2>Welcome Back</h2>
      <p>Login with Email and Password</p>
      <div className="error-message">
        <p>Oops! Invalid Credentials</p>
      </div>
      <form>
        <label>Email</label>
        <input type="email" placeholder="janedoe@gmail.com" required />
        
        <label>Password</label>
        <input type="password" placeholder="*******" required />
        
        <button type="submit">
          <a href="/">Login</a>
        </button>
      </form>
      <div className="additional-links">
        <Link to="/forgot-password">Forgot Password?</Link>
        <p>New User? <Link to="/signup">Sign Up Here Instead</Link></p>
      </div>
    </div>
  </div>
);

export default LoginPage;
