import {SEND_FRIEND_REQUEST} from "../actions/friendRequestActions";

const initialState = {
    sentRequests: [], // [{ senderEmail: '', receiverEmail: '' }]
};

const friendRequestReducer = (state = initialState, action) => {
    switch (action.type) {
        case SEND_FRIEND_REQUEST:
            return {
                ...state,
                sentRequests: [...state.sentRequests, action.payload],
            };
        default:
            return state;
    }
};

export default friendRequestReducer;
