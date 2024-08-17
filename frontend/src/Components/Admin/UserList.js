import React from "react";
import { useDispatch, useSelector } from "react-redux";
// Axio:
import { updateUser } from "../../axios/UserAxios";
// CSS:
import "../../assets/styles/Components/Admin/UserList.css";


const UserList = ({ selectedUsers, setSelectedUsers, data, setData }) => {
	const user = useSelector((state) => state.user.user);
	const dispatch = useDispatch();
	const handleAdminChange = async (email) => {
		try {
			const userToUpdate = data.find((user) => user.email === email);
			const updatedUser = {
				...userToUpdate,
				userLevel: userToUpdate.userLevel === "admin" ? "user" : "admin",
			};
			await updateUser(updatedUser);

			setData((prevData) =>
				prevData.map((user) => (user.email === email ? updatedUser : user))
			);
			if (email === user.email)
				dispatch({
					type: "SET_USER",
					payload: {
						...userToUpdate,
						userLevel: userToUpdate.userLevel === "admin" ? "user" : "admin",
					},
				});
			console.log(`Admin status changed for user ${email}`);
		} catch (error) {
			console.error("Error updating admin status:", error);
		}
	};

	const handleSelectUser = (email) => {
		setSelectedUsers((prevSelected) =>
			prevSelected.includes(email)
				? prevSelected.filter((userEmail) => userEmail !== email)
				: [...prevSelected, email]
		);
	};
	const handleRoleChange = async (email, newRole) => {
		try {
			const userToUpdate = data.find((user) => user.email === email);
			const updatedUser = { ...userToUpdate, role: newRole };
			await updateUser(updatedUser);
			setData((prevData) =>
				prevData.map((user) => (user.email === email ? updatedUser : user))
			);
			console.log(`Role changed for user ${email}`);
		} catch (error) {
			console.error("Error updating role:", error);
		}
	};
	return (
		<div className="table-container">
			<table>
				<thead>
					<tr>
						<th>Select</th>
						<th>Email</th>
						<th>User Name</th>
						<th>First Name</th>
						<th>Last Name</th>
						<th>Role</th>
						<th>Admin</th>
						<th>Validation</th>
					</tr>
				</thead>
				<tbody>
					{data.map((user) => (
						<tr key={user.email}>
							<td>
								<input
									type="checkbox"
									checked={selectedUsers.includes(user.email)}
									onChange={() => handleSelectUser(user.email)}
								/>
							</td>
							<td>{user.email}</td>
							<td>{user.userName}</td>
							<td>{user.firstName}</td>
							<td>{user.lastName}</td>
							<td>
								<select
									value={user.role}
									onChange={(e) => handleRoleChange(user.email, e.target.value)}
								>
									<option value="default">default</option>
									<option value="student">student</option>
									<option value="instructor">instructor</option>
									<option value="employee">employee</option>
								</select>
							</td>
							<td>
								<input
									type="checkbox"
									checked={user.userLevel === "admin"}
									onChange={() => handleAdminChange(user.email)}
								/>
							</td>
							<td>{user.validated ? "Enabled" : "Disabled"}</td>
						</tr>
					))}
				</tbody>
			</table>
		</div>
	);
};

export default UserList;
