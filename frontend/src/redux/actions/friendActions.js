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






export const SET_FRIENDSHIP_REQUESTS = "SET_FRIENDSHIP_REQUESTS";
export const ADD_FRIENDSHIP_REQUEST = "ADD_FRIENDSHIP_REQUEST";
export const REMOVE_FRIENDSHIP_REQUEST = "REMOVE_FRIENDSHIP_REQUEST";

export const setFriendshipRequests = (requests) => {
    return {
        type: SET_FRIENDSHIP_REQUESTS,
        payload: requests,
    };
};

export const addFriendshipRequest = (request) => {
    return {
        type: ADD_FRIENDSHIP_REQUEST,
        payload: request,
    };
};

export const removeFriendshipRequest = (requestId) => {
    return {
        type: REMOVE_FRIENDSHIP_REQUEST,
        payload: requestId,
    };
};
