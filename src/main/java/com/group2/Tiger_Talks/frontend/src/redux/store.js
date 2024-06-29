import {configureStore} from "@reduxjs/toolkit";
import {persistReducer, persistStore} from "redux-persist";
import {thunk} from "redux-thunk";
import rootReducer from "./reducers/rootReducer";
import logger from "redux-logger";
import persistConfig from "./persistConfig";

const persistedReducer = persistReducer(persistConfig, rootReducer);

const store = configureStore({
    reducer: persistedReducer,
    middleware: (getDefaultMiddleware) =>
        getDefaultMiddleware().concat(thunk, logger),
});

const persistor = persistStore(store);

export {store, persistor};
