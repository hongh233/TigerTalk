import React, {useEffect, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import Header from "../components/Header";
import ProfileNavBar from "../components/ProfileNavBar";
import "../assets/styles/ProfileSettingsPage.css";
import axios from "axios";
import {setUser} from "../redux/actions/userActions"; // Assuming you have this action

const ProfileSettingsPage = () => {
    const dispatch = useDispatch();
    const user = useSelector((state) => state.user.user); // Access user from Redux store

    const [form, setForm] = useState({
        id: "",
        email: "",
        firstName: "",
        lastName: "",
        biography: "",
        age: "",
        gender: "",
        profilePictureUrl: "",
    });
    const [errors, setErrors] = useState({});
    const [uploading, setUploading] = useState(false);

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
                profilePictureUrl: user.profilePictureUrl || "",
            });
        }
    }, [user]);

    const handleChange = (e) => {
        const {name, value} = e.target;
        setForm({
            ...form,
            [name]: value,
        });
    };

    const handleFileChange = async (e) => {
        const file = e.target.files[0];
        if (file) {
            setUploading(true);
            const formData = new FormData();
            formData.append("file", file);
            formData.append("upload_preset", "tiger_talks"); // replace with your own upload preset

            try {
                const response = await axios.post(
                    "https://api.cloudinary.com/v1_1/dp4j9a7ry/image/upload", // replace YOUR_CLOUD_NAME
                    formData
                );
                const imageUrl = response.data.secure_url;
                setForm({
                    ...form,
                    profilePictureUrl: imageUrl,
                });
                setUploading(false);
            } catch (error) {
                console.error("Error uploading image:", error);
                setUploading(false);
            }
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setErrors({});
        const updatedUser = {...user, ...form};
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
            console.log(response.data);
            dispatch(setUser(response.data)); // Dispatch the updated user to Redux store
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
            <Header/>
            <div className="profile-settings-wrapper">
                <div className="profile-settings-nav">
                    {user && <ProfileNavBar user={user}/>}
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
                    <div className="form-group">
                        <label>Profile Picture</label>
                        <input
                            type="file"
                            name="profilePicture"
                            onChange={handleFileChange}
                        />
                        {uploading && <p>Uploading...</p>}
                        {form.profilePictureUrl && (
                            <img src={form.profilePictureUrl} alt="Profile" width="100"/>
                        )}
                    </div>
                    <button type="submit" disabled={uploading}>
                        {uploading ? "Uploading..." : "Update Profile"}
                    </button>
                </form>
            </div>
        </div>
    );
};

export default ProfileSettingsPage;
