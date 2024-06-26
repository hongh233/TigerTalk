import React, { useState } from "react";
import Header from "../components/Header";
import ProfileNavBar from "../components/ProfileNavBar";
import "../assets/styles/ProfileSettingsPage.css";

const ProfileSettingsPage = () => {
	const [form, setForm] = useState({
		firstName: "",
		lastName: "",
		email: "",
		age: "",
		gender: "",
		profilePicture: "",
	});
	const [errors, setErrors] = useState({});

	const handleChange = (e) => {
		const { name, value } = e.target;
		setForm({
			...form,
			[name]: value,
		});
	};

	const handleFileChange = (e) => {
		setForm({
			...form,
			profilePicture: e.target.files[0],
		});
	};

	const validate = () => {
		let errors = {};
		if (!form.firstName) errors.firstName = "First name is required";
		if (!form.lastName) errors.lastName = "Last name is required";
		if (!form.email) errors.email = "Email is required";
		if (!form.age) errors.age = "Age is required";
		if (!form.gender) errors.gender = "Gender is required";
		return errors;
	};

	const handleSubmit = (e) => {
		e.preventDefault();
		const errors = validate();
		setErrors(errors);
		if (Object.keys(errors).length === 0) {
			alert("Profile updated successfully");
		}
	};
	return (
		<div className="profile-settings-container">
			<Header />
			<div className="profile-settings-wrapper">
				<div className="profile-settings-nav">
					<ProfileNavBar />
				</div>

				<form className="profile-settings-form" onSubmit={handleSubmit}>
					<div className="form-group">
						<label>First Name</label>
						<input
							type="text"
							name="firstName"
							placeholder="First name"
							value={form.firstName}
							onChange={handleChange}
						/>
						{errors.firstName && <p className="error">{errors.firstName}</p>}
					</div>
					<div className="form-group">
						<label>Last Name</label>
						<input
							type="text"
							name="lastName"
							placeholder="Last name"
							value={form.lastName}
							onChange={handleChange}
						/>
						{errors.lastName && <p className="error">{errors.lastName}</p>}
					</div>
					<div className="form-group">
						<label>Email</label>
						<input
							type="email"
							name="email"
							placeholder="Email"
							value={form.email}
							onChange={handleChange}
						/>
						{errors.email && <p className="error">{errors.email}</p>}
					</div>
					<div className="form-group">
						<label>Age</label>
						<input
							type="number"
							name="age"
							placeholder="Age"
							value={form.age}
							onChange={handleChange}
						/>
						{errors.age && <p className="error">{errors.age}</p>}
					</div>
					<div className="form-group">
						<label>Gender</label>
						<select name="gender" value={form.gender} onChange={handleChange}>
							<option value="">Select Gender</option>
							<option value="male">Male</option>
							<option value="female">Female</option>
							<option value="other">Other</option>
						</select>
						{errors.gender && <p className="error">{errors.gender}</p>}
					</div>
					<div className="form-group">
						<label>Profile Picture</label>
						<input
							type="file"
							name="profilePicture"
							onChange={handleFileChange}
						/>
					</div>
					<button type="submit">Update Profile</button>
				</form>
			</div>
		</div>
	);
};

export default ProfileSettingsPage;
