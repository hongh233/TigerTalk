import { combineReducers } from "@reduxjs/toolkit";
import userReducer from "./userReducer";
import globalGroupsReducer from "./globalGroups";
import globalUsersReducer from "./globalUsers";

const rootReducer = combineReducers({
	user: userReducer,
	globalUsers: globalUsersReducer,
	globalGroups: globalGroupsReducer,
});

export default rootReducer;
