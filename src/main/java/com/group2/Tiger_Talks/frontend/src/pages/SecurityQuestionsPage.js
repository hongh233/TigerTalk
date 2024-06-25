import React, { useState, useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import axios from 'axios';

const SecurityQuestionsPage = () => {
  const { state } = useLocation();
  const { email } = state || {};
  const [questions, setQuestions] = useState([]);
  const [answers, setAnswers] = useState({ answer1: '', answer2: '', answer3: '' });
  const [errors, setErrors] = useState({});
  const navigate = useNavigate();

  useEffect(() => {
    const fetchQuestions = async () => {
      try {
        const response = await axios.get('http://localhost:8085/api/passwordReset/getSecurityQuestions', {
          params: { email }
        });
        setQuestions(response.data);
      } catch (error) {
        setErrors({ general: 'Failed to fetch security questions. Please try again later.' });
      }
    };
    fetchQuestions();
  }, [email]);

  const handleChange = (e) => {
    setAnswers({ ...answers, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setErrors({});
    try {
      await axios.post('http://localhost:8085/api/passwordReset/verifySecurityAnswers', null,{
        params: {
            email,
            answer1: answers.answer1,
            answer2: answers.answer2,
            answer3: answers.answer3,
        }
      });
      alert('Security questions have verified! You can now reset your password!');
      navigate('/resetPassword', { state: { email } });
    } catch (error) {
      setErrors({ general: 'Verification failed. Please try again.' });
    }
  };

  return (
    <div className="security-questions">
      <h1>Answer Security Questions</h1>
      <form onSubmit={handleSubmit}>
        {questions.length > 0 && (
          <>
            <div>
              <label>{questions[0]}</label>
              <input
                type="text"
                name="answer1"
                value={answers.answer1}
                onChange={handleChange}
              />
            </div>
            <div>
              <label>{questions[1]}</label>
              <input
                type="text"
                name="answer2"
                value={answers.answer2}
                onChange={handleChange}
              />
            </div>
            <div>
              <label>{questions[2]}</label>
              <input
                type="text"
                name="answer3"
                value={answers.answer3}
                onChange={handleChange}
              />
            </div>
          </>
        )}
        <button type="submit">Submit</button>
      </form>
      {errors.general && <p className="error">{errors.general}</p>}
    </div>
  );

};

export default SecurityQuestionsPage;