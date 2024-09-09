import { SET_FRIENDS, ADD_FRIEND, REMOVE_FRIEND } from "../actions/friendActions";
import { SET_FRIENDSHIP_REQUESTS, ADD_FRIENDSHIP_REQUEST, REMOVE_FRIENDSHIP_REQUEST } from "../actions/friendActions";
const initialState = {
    friends: [],
    friendshipRequests: [],
};

const friendReducer = (state = initialState, action) => {
    switch (action.type) {
        case SET_FRIENDS:
            return {
                ...state,
                friends: action.payload,
            };
        case ADD_FRIEND:
            return {
                ...state,
                friends: [...state.friends, action.payload],
            };
        case REMOVE_FRIEND:
            return {
                ...state,
                friends: state.friends.filter(friend => friend.id !== action.payload),
            };
        case SET_FRIENDSHIP_REQUESTS:
            return {
                ...state,
                friendshipRequests: action.payload,
            };
        case ADD_FRIENDSHIP_REQUEST:
            return {
                ...state,
                friendshipRequests: [...state.friendshipRequests, action.payload],
            };
        case REMOVE_FRIENDSHIP_REQUEST:
            return {
                ...state,
                friendshipRequests: state.friendshipRequests.filter(
                    request => request.id !== action.payload
                ),
            };
        default:
            return state;
    }
};

export default friendReducer;