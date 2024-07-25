import React, {useEffect, useState} from 'react';
import {useLocation, useNavigate} from 'react-router-dom';
import axios from 'axios';
import '../../../assets/styles/ForgetPassword/SecurityQuestionsPage.css'

const SecurityQuestionsPage = () => {
    const {state} = useLocation();
    const {email} = state || {};
    const [answer, setAnswer] = useState({
        email: email,
        question: '',
        questionAnswer: ''
    });
    const [questions, setQuestions] = useState([]);
    const [errors, setErrors] = useState({});
    const navigate = useNavigate();

    useEffect(() => {
        const fetchQuestions = async () => {
            try {
                const response = await axios.get('http://localhost:8085/api/passwordReset/getSecurityQuestions', {
                    params: {email}
                });
                setQuestions(response.data);
            } catch (error) {
                setErrors({general: 'Failed to fetch security questions. Please try again later.'});
            }
        };
        fetchQuestions();
    }, [email]);

    const handleQuestionChange = (e) => {
        setAnswer(prevAnswer => ({
            ...prevAnswer,
            question: e.target.value
        }));
    };

    const handleAnswerChange = (e) => {
        setAnswer(prevAnswer => ({
            ...prevAnswer,
            questionAnswer: e.target.value
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setErrors({});
        try {
            await axios.post('http://localhost:8085/api/passwordReset/verifySecurityAnswers', null, {
                params: answer
            });
            alert('Security questions have been verified! You can now reset your password!');
            navigate('/resetPassword', {state: {email}});
        } catch (error) {
            setErrors({general: 'Verification failed. Please try again.'});
        }
    };

    return (
        <div className="security-questions">
            <div className="security-questions-card">
                <h1>Answer Security Questions</h1>
                <form onSubmit={handleSubmit}>
                    <select
                        name="question"
                        value={answer.question}
                        onChange={handleQuestionChange}
                    >
                        <option>Select Security Question</option>
                        {questions.map((securityQuestion, idx) => (
                            <option key={idx} value={securityQuestion}>{securityQuestion}</option>
                        ))}
                    </select>
                    <input
                        type="text"
                        name="questionAnswer"
                        value={answer.questionAnswer}
                        onChange={handleAnswerChange}
                        placeholder="Answer to security question"
                    />
                    <button type="submit">Submit</button>
                </form>
                {errors.general && <p className="error-css">{errors.general}</p>}
            </div>
        </div>
    );
};

export default SecurityQuestionsPage;
