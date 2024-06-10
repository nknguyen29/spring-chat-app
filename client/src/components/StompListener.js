import { useEffect, useRef } from "react";
import { useStompClient } from "react-stomp-hooks";
import useGetChatrooms from '../hooks/useGetChatrooms';

export default function StompListener({ user, setMessages }) {
  const stompClient = useStompClient();
  const subscriptions = useRef([]); // use useRef to persist subscriptions
  const { chatrooms, error } = useGetChatrooms(user ? user.id : null); // Pass user.id if user is not null, otherwise pass null

  useEffect(() => {
    if (stompClient && user && chatrooms) {
      // Unsubscribe from old subscriptions
      subscriptions.current.forEach(subscription => subscription.unsubscribe());
      subscriptions.current = [];

      console.log("Subscribing to chatrooms: ", chatrooms);
      subscriptions.current = chatrooms.map(room => {
        return stompClient.subscribe("/topic/" + room.id, (message) => {
          console.log("Received message from room " + room.id + " : " + message.body);
          setMessages(prevMessages => ({
            ...prevMessages,
            [room.id]: [...(prevMessages[room.id] || []), message.body]
          }));
        });
      });
    }

    // Cleanup
    return () => {
      subscriptions.current.forEach(subscription => subscription.unsubscribe());
    };
  }, [setMessages, stompClient, user, chatrooms]); // add chatrooms so that the effect runs when chatrooms change

  if (error) {
    console.error('There was an error!', error);
  }

  return null; // component doesnt render anything
}