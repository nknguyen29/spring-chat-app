import { useState, useEffect } from 'react';
import axios from 'axios';

function useGetChatrooms(userId) {
    const [chatrooms, setChatrooms] = useState([]);
    const [error, setError] = useState(null);

    useEffect(() => {
        if (userId) {
            axios.get(`/api/users/${userId}/public`)
                .then(response => {
                    setChatrooms(response.data.chatrooms);
                })
                .catch(error => {
                    setError(error.message);
                });
        } else {
            setChatrooms([]); // Reset chatrooms if userId is null
        }
    }, [userId]);

    return { chatrooms, error };
}

export default useGetChatrooms;