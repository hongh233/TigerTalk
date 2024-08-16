import axios from "axios";
const URL = process.env.REACT_APP_API_URL;


export const handleAddGroupPostComment = (groupPostId, newCommentObj) => {
    return axios
        .post(`${URL}/api/groups/post/comment/create/${groupPostId}`, newCommentObj)
        .then((response) => response.data)
        .catch((error) => {
            console.error("Error creating group post");
            throw error;
        });
};

export const handleDeleteGroupPostComment = (groupPostCommentId) => {
    return axios
        .delete(`${URL}/api/groups/post/comment/delete/${groupPostCommentId}`)
        .then((response) => response.data)
        .catch((error) => {
            console.error("Error deleting group post comment");
            throw error;
        });
};

export const handleGetCommentsForOneGroupPost = (groupPostId) => {
    return axios
        .get(`${URL}/api/groups/post/comment/${groupPostId}`)
        .then((response) => response.data)
        .catch((error) => {
            console.error("Error getting group post comments");
            throw error;
        });
};