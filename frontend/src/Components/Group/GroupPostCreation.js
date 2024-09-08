import React, { useState } from "react";
import "../../assets/styles/Components/Group/GroupPostCreation.css";
import { useSelector } from "react-redux";
import { uploadImageToCloudinary } from "../../utils/cloudinaryUtils";
import StatusIcon from "../Main/StatusIcon";
import {SlPicture} from "react-icons/sl";


const GroupPostCreation = ({ addPost, onclose, onopen }) => {
	const [postContent, setPostContent] = useState("");
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
				<div className="group-post-creation">
					<div className="group-post-creation-container">

						<button className="group-post-creation-close-button"
								onClick={() => {onclose();onopen();}}>
							&times;
						</button>

						<div className="group-post-creation-header">

							<div className="common-profile-picture-and-status-icon">
								<img src={user.profilePictureUrl} alt="avatar" />
								<StatusIcon status={user.onlineStatus} />
							</div>

							<div className="group-post-user-details">
								{user && <h2>{user.userName}</h2>}
							</div>
						</div>
						<textarea
							placeholder="What's Happening?"
							value={postContent}
							onChange={handleInputChange}>
						</textarea>
						<div className="group-post-creation-image-upload">
							<input type="file" name="profilePicture" onChange={handleFileChange} style={{ display: "none" }} id="fileInput"/>
							<label htmlFor="fileInput"><SlPicture /></label>
						</div>

						{uploading && <p>Uploading...</p>}
						{imageUrl && <img src={imageUrl} alt="Uploaded" width="100" />}
						<button className="group-post-creation-button" onClick={handleAddPost}>Post</button>
					</div>
				</div>
			)}
		</>
	);
};

export default GroupPostCreation;
