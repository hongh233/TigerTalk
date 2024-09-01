import React, { useState } from 'react';
import '../../../assets/styles/Pages/Authentication/ForgetPassword/SecurityQuestionsPage.css'
import { verifySecurityAnswers } from "../../../axios/Authentication/PasswordResetAxios";
import { useLocation, useNavigate } from 'react-router-dom';


const SECURITY_QUESTIONS = [
    "What was your favourite book as a child?",
    "What school did you attend for grade nine?",
    "What was the last name of your grade three teacher?",
    "What is your favourite person from history?",
    "What was the first video game you have ever played?",
    "In what city were you born?",
    "What was the name of your elementary school?",
    "What was the name of your best friend in high school?",
    "What was your childhood nickname?",
    "Where did you go on your first vacation?",
    "What is the name of the hospital where you were born?",
    "What is your favourite food?",
    "What is the name of your favorite childhood toy?",
    "What is your favorite movie?",
    "What is your favorite restaurant?",
];

const SecurityQuestionsPage = () => {
    const {state} = useLocation();
    const {email} = state || {};
    const [question, setQuestion] = useState('');
    const [questionAnswer, setQuestionAnswer] = useState('');
    const [errors, setErrors] = useState({});
    const navigate = useNavigate();
    const [isSubmitting, setIsSubmitting] = useState(false);


    const handleSubmit = async (e) => {
        e.preventDefault();
        setErrors({});
        setIsSubmitting(true);
        try {
            await verifySecurityAnswers(email, question, questionAnswer);
            alert('Security questions have been verified! You can now reset your password!');
            navigate('/resetPassword', {state: {email}});
        } catch (error) {
            const errorMessage = error.response?.data || 'Verification failed. Please try again.';
            setErrors({ general: errorMessage });
        } finally {
            setIsSubmitting(false);
        }
    };

    return (
        <div className="security-questions">
            <div className="security-questions-card">

                <h1>Answer Security Questions</h1>
                <form onSubmit={handleSubmit}>

                    <select
                        name="question"
                        value={question}
                        onChange={(e) => setQuestion(e.target.value)}>

                        <option value="">Select Security Question</option>
                        {SECURITY_QUESTIONS.map(
                            (securityQuestion, idx) => (
                                <option key={idx} value={securityQuestion}>
                                    {securityQuestion}
                                </option>
                            )
                        )}
                    </select>

                    <input
                        type="text"
                        name="questionAnswer"
                        value={questionAnswer}
                        onChange={(e) => setQuestionAnswer(e.target.value)}
                        placeholder="Answer to security question"/>

                    <button type="submit" disabled={isSubmitting}>Submit</button>
                </form>

                {errors.general &&
                    <p className="error-css">{errors.general}</p>
                }
            </div>
        </div>
    );
};

export default SecurityQuestionsPage;
