// src/pages/LoginPost
import React, {useState} from 'react';
import axios from 'axios';

const Login = ({onLogin}) => {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState(null);

    const handleLogin = async (event) => {
        event.preventDefault();
        try {
            // Make an API call to authenticate the user
            const response = await axios.post('/api/login', {email, password});
            // On successful login, store the user's email in local storage
            localStorage.setItem('userEmail', email);
            // Optionally, call a parent component's onLogin function
            onLogin();
        } catch (err) {
            setError('Invalid email or password');
        }
    };

    return (
        <div>
            <h1>Login</h1>
            {error && <p>{error}</p>}
            <form onSubmit={handleLogin}>
                <div>
                    <label>Email:</label>
                    <input type="email" value={email} onChange={(e) => setEmail(e.target.value)} required/>
                </div>
                <div>
                    <label>Password:</label>
                    <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} required/>
                </div>
                <button type="submit">Login</button>
            </form>
        </div>
    );
};

export default Login;
