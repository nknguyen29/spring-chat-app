import { useEffect, useRef } from "react";
import { useStompClient } from "react-stomp-hooks";

import axios from "axios";

export default function StompListener({
  user,
  setMessages,
  userChatrooms,
  setUserChatrooms,
}) {
  const stompClient = useStompClient();
  const subscriptions = useRef([]); // use useRef to persist subscriptions

  useEffect(() => {
    if (stompClient && user && userChatrooms) {
      console.log(
        "[STOMP] user or userChatrooms changed, updating subscriptions"
      );
      // Unsubscribe from old subscriptions
      subscriptions.current.forEach((subscription) =>
        subscription.unsubscribe()
      );
      subscriptions.current = [];

      console.log("[STOMP] Subscribing to chatrooms: ", userChatrooms);

      subscriptions.current = userChatrooms.map((room) => {
        return stompClient.subscribe("/topic/" + room.id, (message) => {
          console.log(
            " [STOMP] Received message from room " + room.id + " : " + message.body
          );
          setMessages((prevMessages) => ({
            ...prevMessages,
            [room.id]: [...(prevMessages[room.id] || []), message.body],
          }));
        });
      });
    }

    // Cleanup
    return () => {
      subscriptions.current.forEach((subscription) =>
        subscription.unsubscribe()
      );
    };
  }, [stompClient, user, userChatrooms]);

  return null; // component doesnt render anything
}
