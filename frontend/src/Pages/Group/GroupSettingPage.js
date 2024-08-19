import React, { useState, useEffect } from "react";
import "../../assets/styles/Pages/Group/GroupSettingPage.css";
import { handleGetGroupById, handleUpdateGroup } from "../../axios/Group/GroupAxios";
import {useNavigate, useParams} from "react-router-dom";
import Header from "../../Components/Main/Header";
import {uploadImageToCloudinary} from "../../utils/cloudinaryUtils";


const GroupSettingPage = () =>{
	const { groupId } = useParams();
	const navigate = useNavigate();
	const [form, setForm] = useState({
		groupName: "",
		status: "",
		groupPicture: "",
	});
	const [errors, setErrors] = useState({});
	const [uploading, setUploading] = useState(false);

	useEffect(() => {
		const fetchGroupDetails = async () => {
			try {
				const groupDetails = await handleGetGroupById(groupId);
				setForm({
					groupName: groupDetails.groupName,
					status: groupDetails.isPrivate ? "private" : "public",
					groupPicture: groupDetails.groupImg,
				});
			} catch (error) {
				console.error("Error fetching group details", error);
			}
		};

		fetchGroupDetails();
	}, [groupId]);

	const handleChange = (e) => {
		const { name, value } = e.target;
		setForm({
			...form,
			[name]: value,
		});
	};

	const handleFileChange = async(e) => {
		const file = e.target.files[0];
		if (file) {
			setUploading(true);
			try {
				const imageUrl = await uploadImageToCloudinary(file);
				setForm({
					...form,
					groupPicture: imageUrl,
				});
				setUploading(false);
			} catch (error) {
				console.error("Error uploading image:", error);
				setUploading(false);
			}
		}
	};

	const validate = () => {
		let errors = {};
		if (!form.groupName) errors.groupName = "Group name is required";

		if (!form.status) errors.status = "Group status is required";
		return errors;
	};

	const handleSubmit = async(e) => {
		e.preventDefault();
		const errors = validate();
		setErrors(errors);
		if (Object.keys(errors).length === 0) {
			const groupUpdate = {
				groupId,
				groupName: form.groupName,
				groupImg: form.groupPicture,
				isPrivate: form.status === "private",
			};
			try {
				await handleUpdateGroup(groupUpdate);
				alert("Group updated successfully");
				navigate(`/group/viewgroup/${groupId}`);
			} catch (error) {
				alert("Error updating group");
			}
		}
	};
	return (
		<div className="main-page">
			<Header />
			<div className="content">
				<form className="group-create-form" onSubmit={handleSubmit}>
					<div className="form-group">
						<label>Group Name</label>
						<input
							type="text"
							name="groupName"
							placeholder="Group name"
							value={form.groupName}
							onChange={handleChange}
						/>
						{errors.groupName && <p className="error">{errors.groupName}</p>}
					</div>
					<div className="form-group">
						<label>Group Status</label>
						<select name="status" value={form.status} onChange={handleChange}>
							<option value="">Select Status</option>
							<option value="public">Public</option>
							<option value="private">Private</option>
						</select>
						{errors.status && <p className="error">{errors.status}</p>}
					</div>

					<div className="form-group">
						<label>Group Picture</label>
						<input
							type="file"
							name="groupPicture"
							onChange={handleFileChange}
						/>
						{uploading && <p>Uploading...</p>}
						{form.groupPicture && <img src={form.groupPicture} alt="Group" width="100" />}
					</div>
					<button type="submit">Update Group</button>
				</form>
			</div>
		</div>
	);
};

export default GroupSettingPage;
