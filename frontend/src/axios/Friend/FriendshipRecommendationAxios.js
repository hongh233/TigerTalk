import axios from "axios";

const URL = process.env.REACT_APP_API_URL;


export const recommendFriends = async (email, numOfFriends) => {
    try {
        return await axios.get(
            `${URL}/friendships/recommendFriends/${email}/${numOfFriends}`,
            {
                params: {numOfFriends},
            }
        );
    } catch (error) {
        throw error;
    }
};