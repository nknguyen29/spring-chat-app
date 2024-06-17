import React, { useState } from "react";
import { useParams } from "react-router-dom";
import { useStompClient } from "react-stomp-hooks";
import { List, ListItem, Typography } from "@mui/material";

import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { Textarea } from "@/components/ui/textarea";
import { Badge } from "@/components/ui/badge";

import { ScrollArea } from "@/components/ui/scroll-area";
import { Separator } from "@/components/ui/separator";

import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import {
  Sheet,
  SheetTrigger,
  SheetContent,
  SheetHeader,
  SheetTitle,
  SheetClose,
} from "@/components/ui/sheet";
import { Avatar, AvatarFallback, AvatarImage } from "@/components/ui/avatar";
import {
  Dialog,
  DialogTrigger,
  DialogContent,
  DialogHeader,
  DialogTitle,
  DialogDescription,
  DialogClose,
} from "@/components/ui/dialog";
import { Send } from "lucide-react";
import { FaUserCircle } from "react-icons/fa";
import { useGetAllUsers, useGetSpecificChatroom } from "@/hooks/useChatroom";

import { useMutation, useQueryClient } from "@tanstack/react-query";
import axios from "axios";

export default function Chatroom({ user, messages }) {
  const { roomId } = useParams();
  const [input, setInput] = useState("");
  const [selectedUser, setSelectedUser] = useState("");

  // Get Instance of StompClient
  const stompClient = useStompClient();

  // Fetch all users
  const {
    data: users,
    isLoading: usersIsLoading,
    isError: usersIsError,
  } = useGetAllUsers();
  const {
    data: dataAboutThisChatroom,
    isLoading: usersInChatroomisLoading,
    isError: usersInChatroomisError,
  } = useGetSpecificChatroom(roomId);

  const queryClient = useQueryClient(); // used to invalidate the query

  const mutation = useMutation({
    // used to send the data to the server
    mutationFn: (userId) => {
      return axios.put(`/api/chatrooms/${roomId}/users/${userId}`, null, {
        headers: {
          Authorization: `Bearer ${sessionStorage.getItem("token")}`,
        },
      });
    },
    onError: (error) => {
      console.log("[Chatroom] onError: ", error);
    },
    onSettled: (data, error) => {
      console.log("[Chatroom] onSettled: ", data, error);
      // invalidate the query to refetch the data
      queryClient.invalidateQueries([
        { queryKey: ["chatrooms"] },
        { queryKey: ["userChatrooms"] },
        { queryKey: ["users"] },
      ]);
    },
  });

  const addUserToChatroom = () => {
    if (selectedUser) {
      mutation.mutate(selectedUser);
    }
  };

  // Check if now > startDate of the chatroom and now < validityDuration of the chatroom
  // If not, then show a message that the chatroom is not active

  const chatRoomStartDate = dataAboutThisChatroom?.startDate;
  const chatRoomValidityDuration = dataAboutThisChatroom?.validityDuration;

  const chatRoomStartDateObj = new Date(chatRoomStartDate).getTime();
  const chatRoomValidityDurationObj = new Date(
    chatRoomValidityDuration
  ).getTime();
  const newDateObj = new Date().getTime(); // current date
  const isChatroomActive =
    chatRoomStartDateObj < newDateObj &&
    newDateObj < chatRoomValidityDurationObj;

  if (!isChatroomActive) {
    return (
      <div className="flex h-full items-center justify-center">
        <Card className="p-8">
          <CardHeader>
            <CardTitle>This Chatroom is currently not available</CardTitle>
          </CardHeader>
          <CardContent>
            <CardDescription>
              Chatroom Start Date:{" "}
              {new Date(chatRoomStartDate).toLocaleString()} <br />
              Chatroom Valid Until:{" "}
              {new Date(chatRoomValidityDuration).toLocaleString()} <br />
            </CardDescription>
          </CardContent>
        </Card>
      </div>
    );
  }

  // Get the users in the chatroom
  const usersInChatroom = dataAboutThisChatroom?.users || [];
  console.log(usersInChatroom);

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

  // Check if users is an array before using array methods
  if (!Array.isArray(users) || !Array.isArray(usersInChatroom)) {
    console.error("Expected an array, but did not receive one.");
    return null;
  }

  // Make an array for user NOT in the chatroom
  const usersNotInChatroom = users.filter(
    (user) => !usersInChatroom.find((u) => u.id === user.id)
  );

  return (
    <div className="flex h-full">
      {/* Main chat area */}
      <div className="flex-1 flex flex-col h-full min-h-[50vh] rounded-xl bg-muted/50 p-4 lg:col-span-2">
        <header className="flex h-14 items-center gap-4 border-b px-6 justify-between">
          <h1 className="text-lg font-semibold">Room {roomId}</h1>
          <div className="flex items-center gap-2">
            <Dialog>
              <DialogTrigger asChild>
                <Button variant="default">Add User</Button>
              </DialogTrigger>
              <DialogContent>
                <DialogHeader>
                  <DialogTitle>Add User to Chatroom</DialogTitle>
                  <DialogDescription>
                    Select a user to add to this chatroom.
                  </DialogDescription>
                </DialogHeader>
                <div className="mb-4">
                  <Label>Select User:</Label>
                  <select
                    value={selectedUser}
                    onChange={(e) => setSelectedUser(e.target.value)}
                    className="w-full p-2 border rounded-md bg-white text-black"
                  >
                    <option value="" disabled>
                      Select a user
                    </option>
                    {usersNotInChatroom.map((user) => (
                      <option key={user.id} value={user.id}>
                        {user.firstName} {user.lastName}
                      </option>
                    ))}
                  </select>
                </div>
                <DialogClose asChild>
                  <Button onClick={addUserToChatroom} className="mt-2">
                    Add User
                  </Button>
                </DialogClose>
              </DialogContent>
            </Dialog>

            <Sheet>
              <SheetTrigger asChild>
                <Button>Users in Chatroom</Button>
              </SheetTrigger>
              <SheetContent side="right" className="w-80">
                <SheetHeader>
                  <SheetTitle>Users in Chatroom</SheetTitle>
                  <SheetClose asChild>
                    <Button variant="default">Close</Button>
                  </SheetClose>
                </SheetHeader>
                <CardContent className="p-0">
                  <ScrollArea className="h-[calc(100vh-130px)] rounded-md p-4">
                    <div className="p-4">
                      {usersInChatroom.map((user) => (
                        <div
                          key={user.id}
                          className="flex items-center gap-4 mb-4"
                        >
                          <Avatar>
                            <AvatarFallback>
                              <FaUserCircle className="text-4xl text-gray-500" />
                            </AvatarFallback>
                          </Avatar>
                          <Typography className="text-lg">
                            {`${user.firstName} ${user.lastName}`}
                          </Typography>
                        </div>
                      ))}
                    </div>
                  </ScrollArea>
                </CardContent>
              </SheetContent>
            </Sheet>
          </div>
        </header>

        <div className="flex flex-col flex-1 overflow-hidden">
          <div className="flex-1 overflow-auto p-4 space-y-4">
            {roomMessages.map((message, index) => {
              // Find the sender's name
              const sender = users.find((user) => user.id === message.sender);
              const senderName = sender
                ? `${sender.firstName} ${sender.lastName}`
                : "Unknown Sender";

              // Check if the message is from the current user
              const isCurrentUser = message.sender === user.id;

              return (
                <div
                  key={index}
                  className={`flex ${
                    isCurrentUser
                      ? "items-end justify-end"
                      : "items-start justify-start"
                  }`}
                >
                  {!isCurrentUser && (
                    <Avatar className="mr-2">
                      <AvatarFallback>
                        <FaUserCircle className="text-4xl text-gray-500" />
                      </AvatarFallback>
                    </Avatar>
                  )}
                  <div className="flex flex-col max-w-[70%]">
                    <div className="text-sm text-gray-600 mb-1">
                      {senderName}
                    </div>
                    <div
                      className={`p-2 rounded-lg ${
                        isCurrentUser ? "bg-blue-500 text-white" : "bg-gray-100"
                      }`}
                    >
                      <p className="text-sm text-center">{message.content}</p>
                    </div>
                  </div>
                  {isCurrentUser && (
                    <Avatar className="ml-2">
                      <AvatarFallback>
                        <FaUserCircle className="text-4xl text-gray-500" />
                      </AvatarFallback>
                    </Avatar>
                  )}
                </div>
              );
            })}
          </div>
        </div>

        {/* <div className="flex-1" /> */}
        {/* without this <div className="flex-1" /> => not scrollable because container grows.... how to fix ... ? */}
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
