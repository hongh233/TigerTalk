import axios from "axios";
const URL = process.env.REACT_APP_API_URL;


// This axio is used in SignUpPage
export const userSignUp = async (form) => {
    try {
        const response = await axios.post(`${URL}/api/signUp/userSignUp`, {
            firstName: form.firstName,
            lastName: form.lastName,
            age: form.age,
            gender: form.gender,
            userName: form.userName,
            email: form.email,
            password: form.password,
            securityQuestionsAnswer: form.securityAnswer,
            securityQuestions: form.securityQuestion
        });
        return response.data;
    } catch (error) {
        throw error;
    }
};


