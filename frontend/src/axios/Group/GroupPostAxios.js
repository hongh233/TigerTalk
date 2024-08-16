import axios from "axios";
const URL = process.env.REACT_APP_API_URL;


export const handleCreatePost = (post) => {
    return axios
        .post(`${URL}/api/groups/post/create`, post)
        .then((response) => response.data)
        .catch((error) => {
            console.error("Error creating group post");
            throw error;
        });
};


export const handleDeletePostAxios = (postId) => {
    return axios
        .delete(`${URL}/api/groups/post/delete/${postId}`)
        .then((response) => response.data)
        .catch((error) => {
            console.error("Error deleting post:", error);
            throw error;
        });
};

export const handleGetAllPost = (groupId) => {
    return axios
        .get(`${URL}/api/groups/post/getAll/${groupId}`)
        .then((response) => response.data)
        .catch((error) => {
            console.error("Error creating group post");
            throw error;
        });
};

export const handleLikeAxios = (postId, userEmail) => {
    return axios
        .put(`${URL}/api/groups/post/like/${postId}?userEmail=${userEmail}`)
        .then((response) => response.data)
        .catch((error) => {
            console.error("Error liking post:", error);
            throw error;
        });
};



