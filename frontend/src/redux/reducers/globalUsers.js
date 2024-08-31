import { SET_GLOBAL_USERS } from "../actions/globalSearchActions";

const initialState = {
	globalUsers: null,
};

const globalUsersReducer = (state = initialState, action) => {
	switch (action.type) {
		case SET_GLOBAL_USERS:
			return {
				...state,
				globalUsers: action.payload,
			};
		default:
			return state;
	}
};

export default globalUsersReducer;
