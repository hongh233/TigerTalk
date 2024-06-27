import React, { useState } from 'react';
import "../assets/styles/UserList.css";

const UserList = ({ selectedUsers, setSelectedUsers }) => {
  const [data, setData] = useState([
    { email: 'email1', firstName: 'User One', lastName: 'T', role: 'Role One', isAdmin: false, isEnabled: true },
    { email: 'email2', firstName: 'User Two', lastName: 'T', role: 'Role Two', isAdmin: true, isEnabled: false },
    { email: 'email3', firstName: 'User Three', lastName: 'T', role: 'Role Three', isAdmin: false, isEnabled: true },
  ]);

  const handleAdminChange = (email) => {
    setData(prevData =>
      prevData.map(user =>
        user.email === email ? { ...user, isAdmin: !user.isAdmin } : user
      )
    );
    console.log(`Admin status changed for user ${email}`);
  };

  const handleSelectUser = (email) => {
    setSelectedUsers(prevSelected =>
      prevSelected.includes(email)
        ? prevSelected.filter(userEmail => userEmail !== email)
        : [...prevSelected, email]
    );
  };
  return (
    <div className="table-container">
      <table>
        <thead>
          <tr>
            <th>Select</th>
            <th>Email</th>
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
              <td>{user.firstName}</td>
              <td>{user.lastName}</td>
              <td>{user.role}</td>
              <td>
                <input
                  type="checkbox"
                  checked={user.isAdmin}
                  onChange={() => handleAdminChange(user.email)}
                />
              </td>
              <td>{user.isEnabled ? 'Enabled' : 'Disabled'}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default UserList;