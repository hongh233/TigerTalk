import React ,{useState}from "react"
import { Link, useNavigate } from 'react-router-dom';
import axios from 'axios';


const ForgotPasswordPage = ()=>{
    const [form, setForm] = useState({
        email: '',
    });
    const [errors, setErrors] = useState({});
    const [message, setMessage] = useState('');
    const navigate = useNavigate();

    const handleChange = (e)=>{
      const {name,value}=e.target;
      setForm({
          ...form,
          [name]:value
      });
    }

    const handleSubmit = async (e) => {
    e.preventDefault();
    setErrors({});
    setMessage('');

    if (!form.email) {
      setErrors({ email: 'Email is required' });
      return;
    }

    try {
      const response = await axios.post('http://localhost:8085/api/logIn/passwordReset/sendToken', null, {
        params: {
          email: form.email
        }
      });
      setMessage(response.data);
      alert('Password reset email sent successfully');
    } catch (error) {
      if (error.response && error.response.status === 404) {
        setErrors({ email: "Email not found" });
      } else {
        setErrors({ email: "An error occurred during sending email. Please try again later." });
      }
    }
  };

    return (
    <div className="signup-page">
      <div className="signup-container">
        <h1>Forgot Password</h1>
        {message && <p>{message}</p>}
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <input
              type="email"
              name="email"
              placeholder="Email"
              value={form.email}
              onChange={handleChange}
            />
            {errors.email && <p className="error">{errors.email}</p>}
          </div>
          <button type="submit">Send to email</button>
        </form>
    
      </div>
    </div>
  );
};


export default ForgotPasswordPage;