import { useEffect, useRef } from "react";
import { useStompClient } from "react-stomp-hooks";

import { toast } from "sonner";

import { useGetUserChatrooms, useGetAllUsers } from "@/hooks/useChatroom";

export default function StompListener({ user, setMessages }) {
  const stompClient = useStompClient();
  const subscriptions = useRef([]); // use useRef to persist subscriptions

  const {
    data: dataUserChatrooms,
    isLoading: isLoadingUserChatrooms,
    isError: isErrorUserChatrooms,
  } = useGetUserChatrooms(user);

  const userChatrooms = dataUserChatrooms ? dataUserChatrooms.chatrooms : [];

  const {
    data: dataUsers,
    isLoading: isLoadingUsers,
    isError: isErrorUsers,
  } = useGetAllUsers();

//  console.log("[StompListener] userChatrooms: ", userChatrooms);

  useEffect(() => {
    let allUsers = dataUsers ? dataUsers : [];

    if (stompClient && user && userChatrooms) {
//      console.log(
//        "[STOMP] user or userChatrooms changed, updating subscriptions"
//      );
      // Unsubscribe from old subscriptions
      subscriptions.current.forEach((subscription) =>
        subscription.unsubscribe()
      );
      subscriptions.current = [];

//      console.log("[STOMP] Subscribing to chatrooms: ", userChatrooms);

      subscriptions.current = userChatrooms.map((room) => {
        return stompClient.subscribe("/topic/" + room.id, (message) => {
//          console.log(
//            " [STOMP] Received message from room " +
//              room.id +
//              " : " +
//              message.body
//          );
    // Sonner : message received
    let messageBody = JSON.parse(message.body);

    // Find the sender in the allUsers array
    let sender = allUsers.find(user => user.id === messageBody.sender);

    // Check if the sender was found and is not the current user
    if (sender && sender.id !== user.id) {
      toast("New Message in Room " + `${room.id} !`, {
        description: `${sender.firstName} ${sender.lastName}: ${messageBody.content}`,
        action: {
          label: "Dismiss",
          onClick: () => console.log("Dismissed toast"),
        },
      });
    }

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
  }, [dataUsers, setMessages, stompClient, user, userChatrooms]);

  return null; // component doesnt render anything
}
