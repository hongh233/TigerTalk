import axios from "axios";
const URL = process.env.REACT_APP_API_URL;


// This axio is used in ForgotPasswordPage
export const validateEmailExist = async (email) => {
    try {
        const response = await axios.post(`${URL}/api/passwordReset/validateEmailExist`, null, {
            params: { email },
        });
        return response.data;
    } catch (error) {
        throw error;
    }
};

// This axio is used in ResetPasswordPage
export const resetPassword = async (email, password) => {
    try {
        const response = await axios.post(`${URL}/api/passwordReset/resetPassword`, {
            email,
            password,
        });
        return response.data;
    } catch (error) {
        throw error;
    }
};

// This axio is used in ForgotPasswordPage
export const sendToken = async (email) => {
    try {
        const response = await axios.post(`${URL}/api/passwordReset/sendToken`, null, {
            params: { email },
        });
        return response.data;
    } catch (error) {
        throw error;
    }
};

// This axio is used in EmailVerificationPage
export const validateToken = async (email, code) => {
    try {
        const response = await axios.post(`${URL}/api/passwordReset/checkToken/${code}`, { email });
        return response.data;
    } catch (error) {
        throw error;
    }
};

// This axio is used in SecurityQuestionsPage
export const verifySecurityAnswers = async (email, question, questionAnswer) => {
    try {
        const response = await axios.post(
            `${URL}/api/passwordReset/verifySecurityAnswers`,
            null,
            {
                params: {
                    email,
                    question,
                    questionAnswer
                },
            }
        );
        return response.data;
    } catch (error) {
        if (error.response) {
            console.error('Server responded with error:', error.response.data);
            throw new Error(error.response.data);
        } else if (error.request) {
            console.error('No response received:', error.request);
            throw new Error('No response received from server.');
        } else {
            console.error('Error setting up request:', error.message);
            throw new Error('Error in request setup: ' + error.message);
        }
    }
};

