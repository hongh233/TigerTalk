import React from "react";
import "../../assets/styles/Components/Admin/UserList.css";
import {
	deleteUserProfileByEmail,
	updateUserProfileSetRole, updateUserProfileSetUserLevel,
	updateUserProfileSetValidated
} from "../../axios/UserAxios";
import {MdDeleteForever} from "react-icons/md";
import ToggleSwitch from "./ToggleSwitch";


const UserList = ({ user, dispatch, data, setData }) => {
	const handleAdminChange = async (email, userLevel) => {
		try {
			const newLevel = userLevel === "admin" ? "user" : "admin";
			await updateUserProfileSetUserLevel(email, newLevel);
			setData((prevData) =>
				prevData.map((user) =>
					user.email === email ? { ...user, userLevel: newLevel } : user
				)
			);
			if (email === user.email) {
				dispatch({
					type: "SET_USER",
					payload: { ...user, userLevel: newLevel },
				});
			}
			console.log(`Admin status changed for user ${email}`);
		} catch (error) {
			console.error("Error updating admin status:", error);
		}
	};

	const handleRoleChange = async (email, newRole) => {
		try {
			await updateUserProfileSetRole(email, newRole);
			setData((prevData) =>
				prevData.map((user) =>
					user.email === email ? { ...user, role: newRole } : user
				)
			);
			console.log(`Role changed for user ${email}`);
		} catch (error) {
			console.error("Error updating role:", error);
		}
	};

	const handleDeleteUser = async (email) => {
		const confirmDelete = window.confirm(`Are you sure you want to delete the user with email ${email}?`);
		if (confirmDelete) {
			try {
				await deleteUserProfileByEmail(email);
				setData((prevData) => prevData.filter((user) => user.email !== email));
				console.log(`User with email ${email} deleted successfully.`);
			} catch (error) {
				console.error(`Error deleting user with email ${email}:`, error);
			}
		}
	};

	const handleToggleValidation = async (email, validated) => {
		try {
			await updateUserProfileSetValidated(email, !validated);
			setData((prevData) =>
				prevData.map((user) =>
					user.email === email ? { ...user, validated: !validated } : user
				)
			);
			console.log(`Validation status changed for user ${email}`);
		} catch (error) {
			console.error("Error updating validation status:", error);
		}
	};

	return (
		<div className="table-container">
			<table>
				<thead>
					<tr>
						<th>Email</th>
						<th>User Name</th>
						<th>Role</th>
						<th>Admin</th>
						<th>Activate</th>
						<th>Delete</th>
					</tr>
				</thead>
				<tbody>
					{data.map((user) => (
						<tr key={user.email}>
							<td>{user.email}</td>
							<td>{user.userName}</td>
							<td>
								<select
									value={user.role}
									onChange={(e) =>
										handleRoleChange(user.email, e.target.value)}>
									<option value="none">none</option>
									<option value="student">student</option>
									<option value="instructor">instructor</option>
									<option value="employee">employee</option>
								</select>
							</td>
							<td>
								<ToggleSwitch
									checked={user.userLevel === "admin"}
									onChange={() => handleAdminChange(user.email, user.userLevel)}
									buttonType={"admin"}
								/>
							</td>
							<td>
								<ToggleSwitch
									checked={user.validated}
									onChange={() => handleToggleValidation(user.email, user.validated)}
									buttonType={"activate"}
								/>
							</td>
							<td>
								<MdDeleteForever
									onClick={() => handleDeleteUser(user.email)}
								/>
							</td>
						</tr>
					))}
				</tbody>
			</table>
		</div>
	);
};

export default UserList;
