import React, { useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import axios from 'axios';

const EmailVerificationPage = () => {
  const { state } = useLocation();
  const { email } = state || {};
  const [code, setCode] = useState('');
  const [errors, setErrors] = useState({});
  const navigate = useNavigate();

  const handleChange = (e) => {
    setCode(e.target.value);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setErrors({});
    try {
      await axios.post(`http://localhost:8085/api/logIn/passwordReset/checkToken/${code}`, {
        email
      });
      alert('Email has verified! You can now reset your password!');
      navigate('/resetPassword', { state: { email } });
    } catch (error) {
      setErrors({ general: 'Verification failed. Please try again.' });
    }
  };


  return (
    <div className="email-verification">
      <h1>Enter Verification Code</h1>
      <form onSubmit={handleSubmit}>
        <input
          type="text"
          placeholder="Enter code sent to your email"
          value={code}
          onChange={handleChange}
        />
        <button type="submit">Submit</button>
      </form>
      {errors.general && <p className="error">{errors.general}</p>}
    </div>
  );
};

export default EmailVerificationPage;