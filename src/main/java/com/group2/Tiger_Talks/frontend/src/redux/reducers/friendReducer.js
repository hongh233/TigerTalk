import { SET_FRIENDS, DELETE_FRIENDS } from "../actions/friendActions";

const initialState = {
	friends: [],
};

const friendReducer = (state = initialState, action) => {
	switch (action.type) {
		case SET_FRIENDS:
			return {
				...state,
				friends: action.payload,
			};

		default:
			return state;
	}
};

export default friendReducer;
