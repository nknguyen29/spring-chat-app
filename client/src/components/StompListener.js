// StompListener.js
import { useEffect } from "react";
import { useStompClient } from "react-stomp-hooks";
import axios from 'axios';

export default function StompListener({ user, setMessages }) {
  const stompClient = useStompClient();

  useEffect(() => {
    if (stompClient && user) {
      axios.get(`/api/users/${user.id}/public`)
        .then(response => {
          const chatrooms = response.data.chatrooms;
          console.log("Subscribing to chatrooms: ", chatrooms);
          const subscriptions = chatrooms.map(room => {
            return stompClient.subscribe("/topic/" + room.id, (message) => {
              console.log("Received message from room " + room.id + " : " + message.body);
              setMessages(prevMessages => ({
                ...prevMessages,
                [room.id]: [...(prevMessages[room.id] || []), message.body]
              }));
            });
          });

          return () => {
            subscriptions.forEach(subscription => subscription.unsubscribe());
          };
        })
        .catch(error => {
          console.error('There was an error!', error);
        });
    }
  }, [setMessages, stompClient, user]);

  return null; // this component doesn't render anything
}