import React, {useEffect, useState} from "react";
import "../../assets/styles/Pages/Admin/AdminPage.css";
import {getAllUsers} from "../../axios/UserAxios";
import {useDispatch, useSelector} from "react-redux";
import Header from "../../Components/Main/Header";
import UserList from "../../Components/Admin/UserList";
import { useNavigate } from "react-router-dom";


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


    return (
        <div className="main-page">
            <Header/>
			<div className="content">
                <div className="admin-content">

                    <UserList
                        user={user}
                        dispatch={dispatch}
                        setData={setData}
                        data={data}
                    />

                    <div className="admin-buttons">
                        <button
                            className="add-user-button"
                            onClick={()=>navigate("/admin/add")}>
                            Add Users
                        </button>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default AdminPage;
