import { SET_GLOBAL_GROUPS } from "../actions/globalSearchActions";

const initialState = {
	globalGroups: null,
};

const globalGroupsReducer = (state = initialState, action) => {
	switch (action.type) {
		case SET_GLOBAL_GROUPS:
			return {
				...state,
				globalGroups: action.payload,
			};
		default:
			return state;
	}
};

export default globalGroupsReducer;
