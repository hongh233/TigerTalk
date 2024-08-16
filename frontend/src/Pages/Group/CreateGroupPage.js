import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import Header from "../../Components/Main/Header";
import NavBar from "../../Components/Main/NavBar";
import { useSelector } from "react-redux";
import "../../assets/styles/Pages/Group/CreateGroupPage.css";
import { handleCreateGroup } from "../../axios/Group/GroupAxios";
const CreateGroupPage = () => {
	const navigate = useNavigate();
	const user = useSelector((state) => state.user.user);
	const [form, setForm] = useState({
		groupName: "",
		status: null,
		userEmail: user.email,
	});
	const [errors, setErrors] = useState({});

	const handleChange = (e) => {
		const { name, value } = e.target;
		setForm({
			...form,
			[name]: value,
		});
	};

	const validate = () => {
		let errors = {};
		if (!form.groupName) errors.groupName = "Group name is required";

		if (!form.status) errors.status = "Group status is required";
		return errors;
	};

	const handleSubmit = async (e) => {
		e.preventDefault();
		const errors = validate();
		setErrors(errors);

		if (Object.keys(errors).length === 0) {
			await handleCreateGroup(form);
			alert("Group created successfully");
			navigate("/group");
		}
	};

	return (
		<div className="main-page">
			<Header />
			<div className="content">
				<div className="sidebar">
					<NavBar />
				</div>

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
							<option value={false}>Public</option>
							<option value={true}>Private</option>
						</select>
						{errors.status && <p className="error">{errors.status}</p>}
					</div>

					<button type="submit">Create Group</button>
				</form>
			</div>
		</div>
	);
};

export default CreateGroupPage;
