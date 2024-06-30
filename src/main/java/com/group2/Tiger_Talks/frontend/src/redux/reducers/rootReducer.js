import {combineReducers} from "@reduxjs/toolkit";
import userReducer from "./userReducer";
import friendRequestReducer from "./friendRequestReducer";

const rootReducer = combineReducers({
    user: userReducer,
    // friends: friendReducer,
    friendRequests: friendRequestReducer,
});

export default rootReducer;
