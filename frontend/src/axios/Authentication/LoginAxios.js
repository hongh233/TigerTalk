import axios from "axios";
const URL = process.env.REACT_APP_API_URL;


export const userLogin = async (email, password) => {
    try {
        const response = await axios.post(
            `${URL}/api/logIn/userLogIn`,
            null,
            {
                params: { email, password },
            }
        );
        return response.data;
    } catch (error) {
        throw error;
    }
};


export const userLogout = async (email) => {
    try {
        return await axios.post(
            `${URL}/api/logIn/userLogOut`,
            null,
            {
                params: {email},
            }
        );
    } catch (error) {
        throw error;
    }
};

