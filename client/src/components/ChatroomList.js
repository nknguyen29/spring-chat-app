import React, { useEffect, useState } from 'react';
import axios from 'axios';

function ChatroomList() {
    const [chatrooms, setChatrooms] = useState([]);
    const [error, setError] = useState(null);

    useEffect(() => {
        axios.get('/api/chatrooms')
            .then(response => {
                setChatrooms(response.data);
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
                {chatrooms.map(chatroom => (
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