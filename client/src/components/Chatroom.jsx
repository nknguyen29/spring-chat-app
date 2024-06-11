import React, { useState } from "react";
import { useParams } from "react-router-dom";
import { useStompClient } from "react-stomp-hooks";
import { List, ListItem, Typography } from "@mui/material";

import { Button } from "./ui/button";
import { Input } from "./ui/input";
import { Label } from "./ui/label";
import { Textarea } from "./ui/textarea";
import { Badge } from "./ui/badge";

import { Tooltip, TooltipContent, TooltipTrigger } from "./ui/tooltip";

import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "./ui/card";

import { CornerDownLeft, Send } from "lucide-react";

import useGetAllUsers from "../hooks/useGetAllUsers";

export default function Chatroom({ user, messages }) {
  const { roomId } = useParams();
  const [input, setInput] = useState("");

  // Get Instance of StompClient
  const stompClient = useStompClient();

  // Fetch all users
  const { users, error } = useGetAllUsers();

  if (error) {
    return <div>Error: {error}</div>;
  }

  const sendMessage = () => {
    if (stompClient) {
      // Send Message
      stompClient.publish({
        destination: "/app/chat/" + roomId,
        body: JSON.stringify({
          sender: user.id,
          content: input,
        }),
      });
    } else {
      // Handle error
      console.error("Stomp client is not connected");
    }
  };

  // Check if messages is an object before accessing its properties
  if (typeof messages !== "object") {
    console.error(
      "Expected an object for the messages prop, but did not receive one."
    );
    return null;
  }

  // Get the messages for the current room
  const roomMessages = (messages[roomId] || []).map((message) =>
    JSON.parse(message)
  );

  console.log("Room messages:", roomMessages);

  // Check if users is an array before using array methods
  if (!Array.isArray(users)) {
    console.error(
      "Expected an array from the useGetAllUsers hook, but did not receive one."
    );
    return null;
  }

  return (
    <div>
      <div className="relative flex h-full min-h-[50vh] flex-col rounded-xl bg-muted/50 p-4 lg:col-span-2">
        <header className="flex h-14 items-center gap-4 border-b px-6">
          <h1 className="text-lg font-semibold">Room {roomId}</h1>
        </header>

        {roomMessages.map((message, index) => {
          // Find the sender's name
          const sender = users.find((user) => user.id === message.sender);
          const senderName = sender
            ? sender.firstName + " " + sender.lastName
            : "Unknown Sender";

          // Check if the message is from the current user
          const isCurrentUser = message.sender === user.id;

          return (
            <div className="flex-1 overflow-auto p-4">
              <div className="space-y-4">
                <div
                  className={`flex ${
                    isCurrentUser
                      ? "items-end justify-end"
                      : "items-start justify-start"
                  }`}
                >
                  <div
                    className={`max-w-[70%] p-2 rounded-lg ${
                      isCurrentUser ? "bg-blue-500 text-white" : "bg-gray-100"
                    }`}
                  >
                    <p className="text-sm"> {message.content} </p>
                  </div>
                </div>
              </div>
            </div>
          );
        })}

        <div className="flex-1" />
        <Card className="relative overflow-hidden rounded-lg border bg-background focus-within:ring-1 focus-within:ring-ring flex items-center p-3">
          <Textarea
            id="message"
            placeholder="Type your message here..."
            value={input}
            onChange={(e) => setInput(e.target.value)}
            className="min-h-12 resize-none border-0 p-3 shadow-none focus-visible:ring-0"
          />
          <div className="flex items-center p-0 pt-0">
            <Button
              type="submit"
              onClick={sendMessage}
              size="sm"
              className="ml-3 gap-1.5"
            >
              <Send className="size-3.5" />
            </Button>
          </div>
        </Card>
      </div>
    </div>
  );
}
