import React, { useEffect, useState } from "react";
import "../../assets/styles/Components/Profile/ProfileEditModal.css";
import { updateUserProfileSetCommonInfo } from "../../axios/UserAxios";
import { useDispatch } from "react-redux";

const ProfileEditModal = ({ isOpen, onClose, user, setProfileUser }) => {
    const dispatch = useDispatch();

    const [form, setForm] = useState({
        email: "",
        firstName: "",
        lastName: "",
        userName: "",
        biography: "",
        year: "",
        month: "",
        day: "",
        gender: "",
    });
    const [errors, setErrors] = useState({});
    const [remainingChars, setRemainingChars] = useState(255);

    useEffect(() => {
        if (user && isOpen) {
            const [year, month, day] = user.birthday !== "0000-00-00"
                ? user.birthday.split('-')
                : ["Not Specify", "-", "-"];
            setForm({
                email: user.email || "",
                firstName: user.firstName || "",
                lastName: user.lastName || "",
                userName: user.userName || "",
                biography: user.biography || "",
                year: year || "",
                month: month || "",
                day: day || "",
                gender: user.gender || "",
            });
            setRemainingChars(255 - (user.biography?.length || 0));
        }
    }, [user, isOpen]);

    const handleChange = (e) => {
        const { name, value } = e.target;

        if (name === "year") {
            if (value === "Not Specify") {
                setForm({ ...form, year: value, month: "-", day: "-" });
            } else {
                setForm({
                    ...form,
                    year: value,
                    month: form.month === "-" ? "01" : form.month,
                    day: form.day === "-" ? "01" : form.day
                });
            }
        } else if (name === "month") {
            if (value === "-") {
                setForm({ ...form, month: value, day: "-" });
            } else {
                setForm({ ...form, month: value });
            }
        } else if (name === "day") {
            setForm({ ...form, day: value });
        } else if (name === "biography") {
            setRemainingChars(255 - value.length);
            setForm({ ...form, biography: value });
        } else {
            setForm({ ...form, [name]: value });
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        setErrors({});
        const { email, firstName, lastName,
            userName, biography,
            year, month, day, gender } = form;

        let validationErrors = {};

        // username check
        if (!userName) {
            validationErrors.userName = "User Name is required";
        } else if (userName.length < 3 || userName.length > 20) {
            validationErrors.userName = "User name must be between 3 and 20 characters long.";
        } else if (/[^A-Za-z0-9!@#$%^&*<>?]/.test(userName)) {
            validationErrors.userName = "User Name contains invalid characters. Only letters, numbers, and characters !@#$%^&*<>? are allowed.";
        }

        // Pronouns check
        if (!gender) {
            validationErrors.gender = "Please select a Pronouns option";
        }

        // first name check
        if (firstName && firstName.length > 50) {
            validationErrors.firstName = "First name must be 50 characters or less.";
        } else if (firstName && !/^[a-zA-Z]*$/.test(firstName)) {
            validationErrors.firstName = "First name can only contain letters.";
        }

        // last name check
        if (lastName && lastName.length > 50) {
            validationErrors.lastName = "Last name must be 50 characters or less.";
        } else if (lastName && !/^[a-zA-Z]*$/.test(lastName)) {
            validationErrors.lastName = "Last name can only contain letters.";
        }

        // if error happens, set error and return
        if (Object.keys(validationErrors).length > 0) {
            setErrors(validationErrors);
            return;
        }

        const birthday = (year !== "Not Specify" && month !== "-" && day !== "-")
            ? `${year}-${month.padStart(2, '0')}-${day.padStart(2, '0')}`
            : "0000-00-00";

        // Check for changes and update
        if (
            userName === user.userName &&
            biography === user.biography &&
            birthday === user.birthday &&
            firstName === user.firstName &&
            lastName === user.lastName &&
            gender === user.gender
        ) {
            alert("No changes detected.");
            return;
        }

        try {
            await updateUserProfileSetCommonInfo(email, firstName, lastName, userName, biography, birthday, gender);
            alert("Profile updated successfully");
            const updatedUser = { ...user, ...form, birthday };
            dispatch({ type: "SET_USER", payload: updatedUser });
            setProfileUser(updatedUser);
            onClose();
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

    // Helper functions to generate year, month, and day options
    const generateYearOptions = () => {
        const currentYear = new Date().getFullYear();
        const years = ["Not Specify"];
        for (let i = currentYear - 120; i <= currentYear; i++) {
            years.push(i.toString());
        }
        return years;
    };

    const generateMonthOptions = () => {
        return form.year === "Not Specify" ? ["-"] : Array.from({ length: 12 }, (_, i) => (i + 1).toString().padStart(2, '0'));
    };

    const generateDayOptions = () => {
        return form.year === "Not Specify" ? ["-"] : Array.from({ length: 31 }, (_, i) => (i + 1).toString().padStart(2, '0'));
    };

    return (
        <div className="profile-edit-modal-overlay">
            <div className="profile-edit-modal">

                <button className="profile-edit-page-close-button" onClick={onClose}>&times;</button>
                <form className="profile-edit-form" onSubmit={handleSubmit}>

                    <div className="profile-edit-form-item">
                        <label>User Name *</label>
                        <input type="text" name="userName" placeholder="User name"
                               value={form.userName} onChange={handleChange}/>
                        {errors.userName &&
                            <p className="error-css">{errors.userName}</p>
                        }
                    </div>

                    <div className="profile-edit-form-item">
                        <label>Pronouns</label>
                        <select name="gender"
                                value={form.gender} onChange={handleChange}>
                            <option value="">Select Pronouns</option>
                            <option value="Don't specify">Don't specify</option>
                            <option value="they/them">they/them</option>
                            <option value="she/her">she/her</option>
                            <option value="he/him">he/him</option>
                            <option value="other">other</option>
                        </select>
                        {errors.gender &&
                            <p className="error-css">{errors.gender}</p>
                        }
                    </div>


                    {/* Birthday Select Fields */}
                    <div className="profile-edit-form-item" id="birthday-setting-large-box">
                        <label>Birthday</label>

                        <div style={{ display: 'flex', gap: '10px' }}>
                            <select name="year" value={form.year} onChange={handleChange} className="birthday-setting-box">
                                {generateYearOptions().map(year => (
                                    <option key={year} value={year}>{year}</option>
                                ))}
                            </select>
                            <select name="month" value={form.month} onChange={handleChange} className="birthday-setting-box">
                                {generateMonthOptions().map(month => (
                                    <option key={month} value={month}>{month}</option>
                                ))}
                            </select>
                            <select name="day" value={form.day} onChange={handleChange} className="birthday-setting-box">
                                {generateDayOptions().map(day => (
                                    <option key={day} value={day}>{day}</option>
                                ))}
                            </select>
                        </div>
                    </div>

                    <div className="profile-edit-form-item">
                        <label>First Name</label>
                        <input type="text" name="firstName" placeholder="Leave blank if not specified"
                               value={form.firstName} onChange={handleChange}/>
                        {errors.firstName &&
                            <p className="error-css">{errors.firstName}</p>
                        }
                    </div>

                    <div className="profile-edit-form-item">
                        <label>Last Name</label>
                        <input type="text" name="lastName" placeholder="Leave blank if not specified"
                               value={form.lastName} onChange={handleChange}/>
                        {errors.lastName &&
                            <p className="error-css">{errors.lastName}</p>
                        }
                    </div>

                    <div className="profile-edit-form-item" id="profile-edit-form-item-bio">
                        <label>Biography</label>
                        <textarea
                            name="biography"
                            placeholder="Leave blank if not specified"
                            value={form.biography}
                            onChange={handleChange}
                            maxLength={255}
                            rows={7}
                            onInput={(e) => {
                                e.target.style.height = 'auto';
                                e.target.style.height = e.target.scrollHeight + 'px';
                            }}
                        />
                        <div className="remaining-chars">{remainingChars} remaining</div>
                    </div>

                    <button type="submit">Update Profile</button>
                </form>

            </div>
        </div>
    );
};

export default ProfileEditModal;
