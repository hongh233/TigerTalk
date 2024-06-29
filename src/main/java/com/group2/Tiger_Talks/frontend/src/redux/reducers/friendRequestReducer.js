import { SET_FRIEND_REQUESTS } from "../actions/friendRequestActions";

const initialState = {
	friendRequests: null,
};

const friendRequestReducer = (state = initialState, action) => {
	switch (action.type) {
		case SET_FRIEND_REQUESTS:
			return {
				...state,
				friendRequests: action.payload,
			};
		default:
			return state;
	}
};

export default friendRequestReducer;
