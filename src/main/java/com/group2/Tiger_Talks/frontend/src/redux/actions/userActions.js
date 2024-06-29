import axios from "axios";

export const SET_USER = "SET_USER";

export const setUser = (user) => ({
	type: SET_USER,
	payload: user,
});
