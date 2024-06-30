export const SEND_FRIEND_REQUEST = "SEND_FRIEND_REQUEST";

export const sendFriendRequest = (senderEmail, receiverEmail) => ({
    type: SEND_FRIEND_REQUEST,
    payload: {senderEmail, receiverEmail},
});
