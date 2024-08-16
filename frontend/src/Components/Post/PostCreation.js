import React, { useState } from "react";
import "../../assets/styles/Components/Post/PostCreation.css";
import { useSelector } from "react-redux";
import { uploadImageToCloudinary } from "../../utils/cloudinaryUtils";
import {FaImage} from "react-icons/fa";

const PostCreation = ({ addPost }) => {
	const [postContent, setPostContent] = useState("");
	const [tagInput, setTagInput] = useState("");
	const [tags, setTags] = useState([]);
	const [uploading, setUploading] = useState(false);
	const [imageUrl, setImageUrl] = useState(null);
	const user = useSelector((state) => state.user.user);

	const handleAddPost = async () => {
		try {
			await addPost(postContent, imageUrl, tags);
			setImageUrl(null);
			setPostContent("");
			setTags([]);
		} catch (error) {
			console.error("Error adding post:", error);
		}
	};

	const handleInputChange = (event) => {
		const content = event.target.value;
		setPostContent(content);

		// Extract tags from the content
		const extractedTags = content.match(/@\w+/g) || [];
		setTags(extractedTags);
	};

	const handleTagInputChange = (event) => {
		setTagInput(event.target.value);
	};

	// const createPost = () => {
	// 	if (postContent === "") {
	// 		window.alert("Post cannot be empty");
	// 	} else {
	// 		addPost(postContent, imageUrl, tags); // Call addPost passed via props
	// 		setPostContent(""); // Clear the post content after creating the post
	// 	}
	// };

	const handleAddTag = () => {
		const updatedContent = `${postContent} ${tagInput}`;
		setPostContent(updatedContent);
		setTagInput("");
		handleInputChange({ target: { value: updatedContent } });
	};


	const handleFileChange = async (e) => {
		const file = e.target.files[0];
		if (file) {
			setUploading(true);

			try {
				const imageUrl = await uploadImageToCloudinary(file);
				setImageUrl(imageUrl);
				setUploading(false);
			} catch (error) {
				console.error("Error uploading image:", error);
				setUploading(false);
			}
		}
	};

	return (
		<>
			{user && (
				<div className="post-creation">
					<div className="post-creation-container">
						<div className="post-header">
							<div className="profile-picture">
								<img src={user.profilePictureUrl} alt="avatar" />
							</div>
							<div className="post-user-details">
								{user && <h2>{user.userName}</h2>}
							</div>
						</div>
						<textarea
							placeholder="What's Happening?"
							value={postContent}
							onChange={handleInputChange}>
						</textarea>
						<div className="image-upload">
							<input
								type="file"
								name="profilePicture"
								onChange={handleFileChange}
								style={{ display: "none" }}
								id="fileInput"
							/>
							<label htmlFor="fileInput">
								<FaImage />
							</label>
						</div>

						{uploading && <p>Uploading...</p>}
						{imageUrl && <img src={imageUrl} alt="Uploaded" width="100" />}
						<button className="post-creation-button" onClick={handleAddPost}>
							Post
						</button>
					</div>
				</div>
			)}
		</>
	);
};

export default PostCreation;
