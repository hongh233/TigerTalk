export const SET_FRIENDS = "SET_FRIENDS";
export const DELETE_FRIENDS = "DELETE_FRIENDS";

export const setFriends = (friends) => ({
    type: SET_FRIENDS,
    payload: friends,
});
export const deleteFriend = (friends) => ({
    type: DELETE_FRIENDS,
    payload: friends,
});
