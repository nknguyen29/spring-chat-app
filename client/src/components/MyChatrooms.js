import React from 'react';

import useGetChatrooms from '../hooks/useGetChatrooms';

function MyChatrooms({ user }) {
    const { chatrooms, error } = useGetChatrooms(user.id);

    return (
        <div>
            <h1>All My Chatrooms</h1>
            {error && <p style={{ color: 'red' }}>{error}</p>}
            <ul>
                {chatrooms.map(chatroom => (
                    <li key={chatroom.id}>
                        <h2>{chatroom.title}</h2>
                        <p>{chatroom.description}</p>
                        <p>Room Start Date: {new Date(chatroom.startDate).toLocaleString()}</p>
                        <p>Room Validity Duration: {new Date(chatroom.validityDuration).toLocaleString()}</p>
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default MyChatrooms;