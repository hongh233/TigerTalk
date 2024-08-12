// import React from "react";
// import { handleDeleteGroupPost } from "../axios/GroupAxios";

// const GroupPostDelete = ({ groupPostId, onDelete }) => {
//     const handleDelete = async () => {
//         try {
//             const response = await handleDeleteGroupPost(groupPostId);
//             if (response) {
//                 alert("Group post deleted successfully.");
//                 onDelete();
//             } else {
//                 alert("Failed to delete the group post.");
//             }
//         } catch (error) {
//             console.error("Error deleting the group post:", error);
//             alert("Error deleting the group post.");
//         }
//     };

//     return (
//         <button className="group-post-delete-button" onClick={handleDelete}>
//             Delete Post
//         </button>
//     );
// };

// export default GroupPostDelete;
