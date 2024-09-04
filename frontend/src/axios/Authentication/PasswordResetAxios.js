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
                params: {email, question, questionAnswer},
            }
        );
        return response.data;
    } catch (error) {
        throw error;
    }
};

