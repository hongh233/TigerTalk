import React, {useState} from 'react';
import Header from '../components/Header';
import NavBar from '../components/NavBar';
import '../assets/styles/CreateGroupPage.css';

const CreateGroupPage = () => {
    const [form, setForm] = useState({
        groupName: '',
        status: "",
        groupDescription: "",
        groupPicture: ''
    });
    const [errors, setErrors] = useState({});

    const handleChange = (e) => {
        const {name, value} = e.target;
        setForm({
            ...form,
            [name]: value
        });
    };

    const handleFileChange = (e) => {
        setForm({
            ...form,
            groupPicture: e.target.files[0]
        });
    };

    const validate = () => {
        let errors = {};
        if (!form.groupName) errors.groupName = "Group name is required";
        if (!form.groupDescription) errors.groupDescription = "Group description is required";
        if (!form.status) errors.status = "Group status is required";
        return errors;
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        const errors = validate();
        setErrors(errors);
        if (Object.keys(errors).length === 0) {
            alert('Group created successfully');
        }
    };
    return (
        <div className="group-create-container">
            <Header/>
            <div className="group-create-wrapper">
                <div className="group-create-nav">
                    <NavBar/>
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
                            <option value="public">Public</option>
                            <option value="private">Private</option>
                        </select>
                        {errors.status && <p className="error">{errors.status}</p>}
                    </div>
                    <div className="form-group">
                        <label>Group Description</label>
                        <input
                            type="text"
                            name="groupDescription"
                            placeholder="Group description"
                            value={form.groupDescription}
                            onChange={handleChange}
                        />
                        {errors.groupDescription && <p className="error">{errors.groupDescription}</p>}
                    </div>
                    <div className="form-group">
                        <label>Group Picture</label>
                        <input
                            type="file"
                            name="groupPicture"
                            onChange={handleFileChange}
                        />
                    </div>
                    <button type="submit">Create Group</button>
                </form>
            </div>

        </div>
    );
};

export default CreateGroupPage;
