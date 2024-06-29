import { combineReducers } from "@reduxjs/toolkit";
import userReducer from "./userReducer";
import friendReducer from "./friendReducer";
import friendRequestReducer from "./friendRequestReducer";

const rootReducer = combineReducers({
	user: userReducer,
	// friends: friendReducer,
	// friendRequests: friendRequestReducer,
});

export default rootReducer;
