import axios from "axios";

const URL = process.env.REACT_APP_API_URL;

export const fetchUserByEmail = async (userEmail, dispatch) => {
	try {
		const response = await axios.get(`${URL}/api/user/getByEmail/${userEmail}`);
		return response.data;
	} catch (error) {
		console.error("Error fetching profile user data:", error);
	}
};

// This axio is used in LoginPage
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

// This axio is used in EmailVerificationPage
export const verifyEmailCode = async (email, code) => {
	try {
		const response = await axios.post(`${URL}/api/passwordReset/checkToken/${code}`, { email });
		return response.data;
	} catch (error) {
		throw error;
	}
};

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