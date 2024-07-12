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
