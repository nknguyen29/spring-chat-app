import React from 'react';

import useGetAllUsers from '../hooks/useGetAllUsers';

function UserList() {
    const {users, error} = useGetAllUsers();

    return (
        <div>
            <h1>User List</h1>
            {error && <p style={{ color: 'red' }}>{error}</p>}
            <ul>
                {users.map(user => (
                    <li key={user.id}>{user.firstName} {user.lastName}</li>
                ))}
            </ul>
        </div>
    );
}

export default UserList;
