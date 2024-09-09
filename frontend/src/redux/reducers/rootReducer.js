import { combineReducers } from "@reduxjs/toolkit";
import userReducer from "./userReducer";
import globalGroupsReducer from "./globalGroups";
import globalUsersReducer from "./globalUsers";
import friendReducer from "./friendReducer";

const rootReducer = combineReducers({
	user: userReducer,
	globalUsers: globalUsersReducer,
	globalGroups: globalGroupsReducer,
	friends: friendReducer,
});

export default rootReducer;
