import React, { useEffect, useState } from 'react';
import axios from 'axios';

function ChatroomList() {
    const [chatrooms, setChatrooms] = useState([]);
    const [error, setError] = useState(null);

    useEffect(() => {
        axios.get('/api/chatrooms/public')
            .then(response => {
                if (Array.isArray(response.data)) {
                    setChatrooms(response.data);
                } else {
                    setChatrooms([]);
                    console.error('Hello, expected an array from the API endpoint, but did not receive one.');
                }
            })
            .catch(error => {
                setError(error.message);
            });
    }, []);

    return (
        <div>
            <h1>Chatroom List</h1>
            {error && <p style={{ color: 'red' }}>{error}</p>}
            <ul>
                {chatrooms && chatrooms.map(chatroom => (
                    <li key={chatroom.id}>
                        <h2>{chatroom.title}</h2>
                        <ul>
                            {chatroom.users.map(user => (
                                <li key={user.id}> User {user.id} : {user.firstName} {user.lastName}</li>
                            ))}
                        </ul>
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default ChatroomList;