import React from 'react';
import "../assets/styles/UserList.css";

const UserList = ({ selectedUsers, setSelectedUsers,data,setData }) => {
  const handleAdminChange = (email) => {
    setData(prevData =>
      prevData.map(user =>
        user.email === email ? { ...user, userLevel: user.userLevel==='admin'?'user':'admin' } : user
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
                  checked={user.userLevel==="admin"}
                  onChange={() => handleAdminChange(user.email)}
                />
              </td>
              <td>{user.isValidated ? 'Enabled' : 'Disabled'}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
};

export default UserList;