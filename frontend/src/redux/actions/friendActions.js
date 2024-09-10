export const SET_FRIENDS = "SET_FRIENDS";
export const ADD_FRIEND = "ADD_FRIEND";
export const REMOVE_FRIEND = "REMOVE_FRIEND";

export const setFriends = (friends) => {
    return {
        type: SET_FRIENDS,
        payload: friends,
    };
};

export const addFriend = (friend) => {
    return {
        type: ADD_FRIEND,
        payload: friend,
    };
};

export const removeFriend = (friendId) => {
    return {
        type: REMOVE_FRIEND,
        payload: friendId,
    };
};



