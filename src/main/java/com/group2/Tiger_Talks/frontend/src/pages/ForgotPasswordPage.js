import React ,{useState}from "react"
import { Link, useNavigate } from 'react-router-dom';


const ForgotPasswordPage = ()=>{
    const [form, setForm] = useState({
        email: '',
    });
    const [errors, setErrors] = useState({});

    const navigate = useNavigate();

    const handleChange = (e)=>{
        const {name,value}=e.target;
        setForm({
            ...form,
            [name]:value
        })
    }
    const handleSubmit = (e) => {
    e.preventDefault();
    setErrors(errors);
    if (Object.keys(errors).length === 0) {
      // Simulate a successful sign-up
      alert('Signed up successfully');
      navigate('/login');
    }
  };
    return (
    <div className="signup-page">
      <div className="signup-container">
        <h1>Forgot Password</h1>
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