import React, {useEffect, useState} from "react";
import {useDispatch, useSelector} from "react-redux";
import NavBar from "../../Components/Main/NavBar";
import Header from "../../Components/Main/Header";
import UserList from "../../Components/Admin/UserList";
import axios from "axios";
import "../../assets/styles/Pages/Admin/AdminPage.css";
import { useNavigate } from "react-router-dom";
import {deleteUserProfileByEmail, getAllUsers, updateUser} from "../../axios/UserAxios";

const AdminPage = () => {
    const navigate = useNavigate();
    const dispatch = useDispatch();
    const user = useSelector((state) => state.user.user);
    const [selectedUsers, setSelectedUsers] = useState([]);
    const [data, setData] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const users = await getAllUsers();
                setData(users);
            } catch (error) {
                console.error("Error fetching user data:", error);
            }
        };
        fetchData();
    }, []);

    const handleEnableDisableUsers = async () => {
        try {
            const promises = selectedUsers.map((email) =>
                updateUser({
                    ...data.find((user) => user.email === email),
                    validated: !data.find((user) => user.email === email).validated,
                })
            );
            await Promise.all(promises);

            setData((prevData) =>
                prevData.map((user) =>
                    selectedUsers.includes(user.email)
                        ? {...user, validated: !user.validated}
                        : user
                )
            );
            if(selectedUsers.includes(user.email))
                dispatch({type: "SET_USER", payload: {...user,validated:!user.validated}});
            console.log("Users enabled/disabled successfully");
            setSelectedUsers([]);
        } catch (error) {
            console.error("Error enabling/disabling users:", error);
        }
    };
    const handleDeleteUsers = async () => {
        try {
            const promises = selectedUsers.map((email) => deleteUserProfileByEmail(email));
            await Promise.all(promises);

            setData((prevData) =>
                prevData.filter((user) => !selectedUsers.includes(user.email))
            );

            console.log("Users deleted successfully");
            setSelectedUsers([]);
        } catch (error) {
            console.error("Error deleting users:", error);
        }
    };

    return (
        <div className="main-page">
            <Header/>
			<div className="content">
                <div className="sidebar">
                    <NavBar />
                </div>

                <div className="admin-content">
                    <UserList
                        selectedUsers={selectedUsers}
                        setSelectedUsers={setSelectedUsers}
                        setData={setData}
                        data={data}
                    />
                    <div className="admin-buttons">
                        <button
                            className="add-user-button"
                            onClick={()=>navigate("/admin/add")}
                        >
                            Add Users
                        </button>
                        <button
                            className="delete-user-button"
                            disabled={selectedUsers.length === 0}
                            onClick={handleDeleteUsers}
                        >
                            Delete Users
                        </button>
                        <button
                            className="toggle-user-button"
                            disabled={selectedUsers.length === 0}
                            onClick={handleEnableDisableUsers}
                        >
                            Enable/Disable Users
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default AdminPage;
