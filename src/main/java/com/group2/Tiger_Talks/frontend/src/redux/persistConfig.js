import storage from "redux-persist/lib/storage";

const persistConfig = {
    key: "root",
    storage,
    whitelist: ["user", "friends", "friendRequests"],
};

export default persistConfig;
