import React, {createContext, useCallback, useContext, useEffect, useState,} from "react";
import emitter from "./EventEmitter";
import axios from "axios";

const UserContext = createContext();

export const useUser = () => useContext(UserContext);

export const UserProvider = ({children}) => {
    const [user, setUser] = useState(null);

    useEffect(() => {
        const storedUser = localStorage.getItem("user");
        if (storedUser) {
            setUser(JSON.parse(storedUser));
        }
    }, []);

    useEffect(() => {
        if (user) {
            localStorage.setItem("user", JSON.stringify(user));
        } else {
            localStorage.removeItem("user");
        }
    }, [user]);

    const updateUser = useCallback((newUserData) => {
        setUser(newUserData);
    }, []);

    useEffect(() => {
        const handleUserUpdate = async () => {
            try {
                const response = await axios.get(
                    "http://localhost:8085/api/logIn/getUserProfile",
                    {
                        params: {email: user?.email},
                    }
                );
                setUser(response.data);
            } catch (error) {
                console.error("Failed to update user profile", error);
            }
        };

        emitter.on("userUpdated", handleUserUpdate);

        return () => {
            emitter.off("userUpdated", handleUserUpdate);
        };
    }, [user]);

    return (
        <UserContext.Provider value={{user, setUser, updateUser}}>
            {children}
        </UserContext.Provider>
    );
};
