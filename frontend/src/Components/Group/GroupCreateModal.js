import React, { useState } from "react";
import "../../assets/styles/Components/Group/GroupCreateModal.css";
import { handleCreateGroup } from "../../axios/Group/GroupAxios";


const GroupCreateModal = ({ isOpen, onClose, user }) => {
    const [form, setForm] = useState({
        groupName: "",
        status: null,
        userEmail: user.email,
    });
    const [errors, setErrors] = useState({});

    const handleChange = (e) => {
        const { name, value } = e.target;
        setForm({...form, [name]: value,});
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
            onClose();
            window.location.reload();
        }
    };

    if (!isOpen) {
        return null;
    }

    return (
        <div className="group-create-modal-overlay">
            <div className="group-create-modal">

                <button className="group-create-page-close-button" onClick={onClose}>&times;</button>
                <form className="group-create-form" onSubmit={handleSubmit}>

                    <div className="group-create-form-item">
                        <label>Group Name</label>
                        <input type="text" name="groupName" placeholder="Group name" value={form.groupName} onChange={handleChange}/>
                        {errors.groupName && <p className="error">{errors.groupName}</p>}
                    </div>

                    <div className="group-create-form-item">
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

export default GroupCreateModal;
