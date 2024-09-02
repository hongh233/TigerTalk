import axios from "axios";
const URL = process.env.REACT_APP_API_URL;


// This axio is used in SignUpPage
export const userSignUp = async (form) => {
    try {
        const response = await axios.post(`${URL}/api/signUp/userSignUp`, {
            email: form.email,
            userName: form.userName,
            gender: form.gender,
            password: form.password,
            securityQuestion: form.securityQuestion,
            securityQuestionAnswer: form.securityQuestionAnswer
        });
        return response.data;
    } catch (error) {
        throw error;
    }
};



export const checkEmailExists = async (email) => {
    try {
        const response = await axios.get(`${URL}/api/signUp/checkEmailExists`, {
            params: { email }
        });
        return response.data;
    } catch (error) {
        throw error;
    }
};


