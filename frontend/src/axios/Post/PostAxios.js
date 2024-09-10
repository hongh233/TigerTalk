import axios from "axios";
import {formatPost} from "../../utils/formatPost";
const URL = process.env.REACT_APP_API_URL;



export const fetchPosts = (userEmail) => {
    return axios
        .get(`${URL}/posts/getPostForUserAndFriends/${userEmail}`)
        .then(response => formatPost(response.data))
        .catch(error => {
            console.error("There was an error fetching posts!", error);
            throw error;
        });
};

export const fetchPostsOfOneUser = async (email) => {
    try {
        const response = await axios.get(`${URL}/posts/getPostForUser/${email}`);
        return response.data;
    } catch (error) {
        console.error("Error fetching post:", error);
        throw error;
    }
};

export const createPost = (newPost) => {
    return axios
        .post(`${URL}/posts/create`, newPost)
        .then(response => response.data)
        .catch(error => {
            console.error("There was an error creating the post!", error);
            throw error;
        });
};

export const handleEditPostAxios = (postId, content) => {
    return axios
        .post(`${URL}/posts/editPost/${postId}/${content}`)
        .then((response) => response.data)
        .catch((error) => {
            console.error("Error editing post: ", error);
            throw error;
        });
};

export const handleDeletePostAxios = (postId) => {
    return axios
        .delete(`${URL}/posts/delete/${postId}`)
        .then((response) => response.data)
        .catch((error) => {
            console.error("Error deleting post:", error);
            throw error;
        });
};




//
// wait for update post (in controller)
//




export const handleLikeAxios = (postId, likeAction, userEmail) => {
    return axios
        .put(`${URL}/posts/like/${postId}/${likeAction}?userEmail=${userEmail}`)
        .then((response) => response.data)
        .catch((error) => {
            console.error("Error liking post:", error);
            throw error;
        });
};









