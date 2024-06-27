import React, { useState, useEffect } from "react";
import Header from "../components/Header";
import ProfileNavBar from "../components/ProfileNavBar";
import "../assets/styles/ProfileSettingsPage.css";
import { useUser } from "../context/UserContext";
import axios from "axios";

const ProfileSettingsPage = () => {
	const { user, setUser } = useUser();
	const [form, setForm] = useState({
		id: "",
		email: "",
		firstName: "",
		lastName: "",
		biography: "",
		age: "",
		gender: "",
	});
	const [errors, setErrors] = useState({});

	useEffect(() => {
		if (user) {
			setForm({
				id: user.id || "",
				email: user.email || "",
				firstName: user.firstName || "",
				lastName: user.lastName || "",
				biography: user.biography || "",
				age: user.age || "",
				gender: user.gender || "",
			});
		}
	}, [user]);

	const handleChange = (e) => {
		const { name, value } = e.target;
		setForm({
			...form,
			[name]: value,
		});
	};

	const handleSubmit = async (e) => {
		e.preventDefault();
		setErrors({});

		const updatedUser = { ...user, ...form }; // Merge the existing user object with the updated form data

		try {
			const response = await axios.put(
				"http://localhost:8085/api/user/update",
				updatedUser,
				{
					headers: {
						"Content-Type": "application/json",
					},
				}
			);
			alert("Profile updated successfully");
			setUser(response.data);
		} catch (error) {
			if (error.response && error.response.data) {
				setErrors(error.response.data);
			} else {
				alert(
					"An error occurred while updating the profile. Please try again later."
				);
			}
		}
	};

	if (!user) {
		return <div>Loading...</div>;
	}

	return (
		<div className="profile-settings-container">
			<Header />
			<div className="profile-settings-wrapper">
				<div className="profile-settings-nav">
					{user && <ProfileNavBar user={user} />}
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
						<label>Bio</label>
						<input
							type="text"
							name="biography"
							placeholder="Bio"
							value={form.biography}
							onChange={handleChange}
						/>
						{errors.biography && <p className="error">{errors.biography}</p>}
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
					<button type="submit">Update Profile</button>
				</form>
			</div>
		</div>
	);
};

export default ProfileSettingsPage;
