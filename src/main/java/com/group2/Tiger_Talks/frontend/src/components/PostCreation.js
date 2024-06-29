import React, { useState } from "react";
import "../assets/styles/PostCreation.css";
import { useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";

//const PostCreation = () => {
const PostCreation = ({ addPost }) => {
	const [postContent, setPostContent] = useState("");
	const [tagInput, setTagInput] = useState("");
	const [tags, setTags] = useState([]);
	const navigate = useNavigate();
	const user = useSelector((state) => state.user.user);

	/*
    const handleInputChange = (event) => {
      setPostContent(event.target.value);
      console.log(event.target.value);
    };
    */

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

	/*
    const createPost = () => {
      console.log('Post content:', postContent);
      console.log('Tags:', tags);
    };
    */

	const createPost = () => {
		console.log("Post content:", postContent);
		console.log("Tags:", tags);
		addPost(postContent, tags); // Call addPost passed via props
		setPostContent(""); // Clear the post content after creating the post
	};

	const handleAddTag = () => {
		const updatedContent = `${postContent} ${tagInput}`;
		setPostContent(updatedContent);
		setTagInput("");
		handleInputChange({ target: { value: updatedContent } });
	};

	const handleTagClick = (tag) => {
		const userName = tag.substring(1); // Remove the '@' from the tag
		navigate(`/friends`);
	};

	/*
    const renderPostContent = (content) => {
      const parts = content.split(/(@\w+)/g);
      return parts.map((part, index) => {
        if (part.startsWith('@')) {
          return (
            <span
              key={index}
              className="tag"
              onClick={() => handleTagClick(part)}
              style={{ color: 'blue', cursor: 'pointer' }}
            >
              {part}
            </span>
          );
        } else {
          return part;
        }
      });
    };
    */

	return (
		<>
			{user && (
				<div className="post-creation">
					<div className="post-header">
						<div className="profile-picture">
							{user.profilePictureUrl && (
								<img src={user.profilePictureUrl} alt="avatar" />
							)}
						</div>
						<div className="post-user-details">
							{user && <h2>{user.userName}</h2>}
						</div>
					</div>
					<textarea
						placeholder="What's Happening?"
						value={postContent}
						onChange={handleInputChange}
					></textarea>
					<div className="location-and-image">
						{/*TODO: {Raphael} Add tags to posts */}
						<input
							type="text"
							placeholder="Want to tag someone?"
							value={tagInput}
							onChange={handleTagInputChange}
						/>
						<button onClick={handleAddTag}>+</button>
						<div className="image-upload">
							<span>+</span>
						</div>
					</div>
					<button className="post-button" onClick={createPost}>
						Post
					</button>
					<div className="tags">
						{/*tags.map((tag, index) => (
          <span key={index} className="tag" onClick={() => handleTagClick(tag)}>
            {tag}
          </span>
        ))*/}
					</div>
					<p>{/*renderPostContent(postContent)*/}</p>
				</div>
			)}
		</>
	);
};

export default PostCreation;
