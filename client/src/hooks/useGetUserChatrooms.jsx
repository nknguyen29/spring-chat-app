import { useState, useEffect } from 'react';
import axios from 'axios';

function useGetUserChatrooms(user, setUserChatrooms) {
    const [error, setError] = useState(null);

    useEffect(() => {
        let intervalId;

        const fetchChatrooms = () => {
            if (user) {
                axios.get(`/api/users/${user.id}/public`)
                    .then(response => {
                        setUserChatrooms(response.data.chatrooms);
                    })
                    .catch(error => {
                        setError(error.message);
                    });
            } else {
                setUserChatrooms([]); // Reset chatrooms if userId is null
            }
        };

        fetchChatrooms(); // Call immediately on component mount
        // intervalId = setInterval(fetchChatrooms, 5000); // Fetch every 5 seconds

        // Cleanup function to clear the interval when the component unmounts
        return () => clearInterval(intervalId);
    }, [user]);

    return { error }; // Return error message
}

export default useGetUserChatrooms;