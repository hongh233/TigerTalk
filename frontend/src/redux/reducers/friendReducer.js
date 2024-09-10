import { SET_FRIENDS, ADD_FRIEND, REMOVE_FRIEND } from "../actions/friendActions";
const initialState = {
    friends: [],
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
        default:
            return state;
    }
};

export default friendReducer;