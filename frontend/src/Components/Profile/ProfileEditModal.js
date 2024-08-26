import React, { useEffect, useState } from "react";
import "../../assets/styles/Components/Profile/ProfileEditModal.css";
import { updateUser } from "../../axios/UserAxios";
import { useDispatch } from "react-redux";

const ProfileEditModal = ({ isOpen, onClose, user }) => {
    const dispatch = useDispatch();

    const [form, setForm] = useState({
        id: "",
        email: "",
        firstName: "",
        lastName: "",
        userName: "",
        biography: "",
        age: "",
        gender: "",
        profilePictureUrl: "",
        onlineStatus: "",
    });
    const [errors, setErrors] = useState({});
    const [uploading, setUploading] = useState(false);

    useEffect(() => {
        if (user && isOpen) {
            setForm({
                id: user.id || "",
                email: user.email || "",
                firstName: user.firstName || "",
                lastName: user.lastName || "",
                userName: user.userName || "",
                biography: user.biography || "",
                age: user.age || "",
                gender: user.gender || "",
                profilePictureUrl: user.profilePictureUrl || "",
                onlineStatus: user.onlineStatus || "",
            });
        }
    }, [user, isOpen]);

    const handleChange = (e) => {
        const { name, value } = e.target;
        setForm({...form, [name]: value});
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setErrors({});
        const updatedUser = { ...user, ...form };
        try {
            const responseData = await updateUser(updatedUser);
            alert("Profile updated successfully");
            dispatch({ type: "SET_USER", payload: responseData });
            onClose();
            window.location.reload();
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

    if (!isOpen) {
        return null;
    }

    return (
        <div className="profile-edit-modal-overlay">
            <div className="profile-edit-modal">

                <button className="profile-edit-page-close-button" onClick={onClose}>&times;</button>
                <form className="profile-edit-form" onSubmit={handleSubmit}>
                    <div className="profile-edit-form-item">
                        <label>First Name</label>
                        <input type="text" name="firstName" placeholder="First name" value={form.firstName} onChange={handleChange}/>
                        {errors.firstName && <p className="error">{errors.firstName}</p>}
                    </div>
                    <div className="profile-edit-form-item">
                        <label>Last Name</label>
                        <input type="text" name="lastName" placeholder="Last name" value={form.lastName} onChange={handleChange}/>
                        {errors.lastName && <p className="error">{errors.lastName}</p>}
                    </div>
                    <div className="profile-edit-form-item">
                        <label>User Name</label>
                        <input type="text" name="userName" placeholder="User name" value={form.userName} onChange={handleChange}/>
                        {errors.userName && <p className="error">{errors.userName}</p>}
                    </div>
                    <div className="profile-edit-form-item">
                        <label>Personal Interest</label>
                        <input type="text" name="biography" placeholder="Personal Interest" value={form.biography} onChange={handleChange}/>
                        {errors.biography && <p className="error">{errors.biography}</p>}
                    </div>
                    <div className="profile-edit-form-item">
                        <label>Age</label>
                        <input type="number" name="age" placeholder="Age" value={form.age} onChange={handleChange}/>
                        {errors.age && <p className="profile-edit-error">{errors.age}</p>}
                    </div>
                    <div className="profile-edit-form-item">
                        <label>Gender</label>
                        <select name="gender" value={form.gender} onChange={handleChange}>
                            <option value="">Select Gender</option>
                            <option value="male">Male</option>
                            <option value="female">Female</option>
                            <option value="other">Other</option>
                        </select>
                        {errors.gender && <p className="error">{errors.gender}</p>}
                    </div>

                    <button type="submit" disabled={uploading}>{uploading ? "Uploading..." : "Update Profile"}</button>
                </form>

            </div>
        </div>
    );
};

export default ProfileEditModal;
