import axios from "axios";

const URL = process.env.REACT_APP_API_URL;

export const fetchUserByEmail = async (userEmail, dispatch) => {
	try {
		const response = await axios.get(`${URL}/api/user/getByEmail/${userEmail}`);
		const data = response.data;
		return data;
	} catch (error) {
		console.error("Error fetching profile user data:", error);
	}
};

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
			securityQuestionsAnswer: [
				form.securityAnswer1,
				form.securityAnswer2,
				form.securityAnswer3,
			],
			securityQuestions: [
				form.securityQuestion1,
				form.securityQuestion2,
				form.securityQuestion3,
			],
		});
		return response.data;
	} catch (error) {
		throw error;
	}
};
