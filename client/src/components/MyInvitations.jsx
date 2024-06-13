import React from "react";

import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";

import {
  Table,
  TableBody,
  TableCaption,
  TableCell,
  TableFooter,
  TableHead,
  TableHeader,
  TableRow,
} from "@/components/ui/table";

import { CirclePlus } from "lucide-react";

import { Button } from "@/components/ui/button";
import { Link } from "react-router-dom";

import {useGetUserChatrooms} from "@/hooks/useChatroom";

function MyInvitations({ user }) {
  const {
    data: userChatrooms,
    isLoading,
    isError,
  } = useGetUserChatrooms(user);
  if (isLoading) {
    return <div>Loading...</div>;
  }
  if (isError) {
    return <div>Error loading chatrooms</div>;
  }

  console.log("[MyInvitations] userChatrooms: ", userChatrooms);

  // Filter the chatrooms to only include those created by the user
  const myInvitations = userChatrooms.chatrooms.filter(
    (chatroom) => chatroom.createdBy.id != user.id
  );
  console.log("[MyInvitations] userChatrooms: ", userChatrooms.chatrooms);
  console.log("[MyInvitations] myInvitations: ", myInvitations);

  return (
    <div>
      <Card>
        <CardHeader className="px-7">
          <CardTitle>My Invitations</CardTitle>
          <CardDescription>
            More Informations about My Invitations
          </CardDescription>
        </CardHeader>
        <CardContent>
          <Table>
            <TableCaption>My Invitations</TableCaption>
            <TableHeader>
              <TableRow>
                <TableHead>Room ID</TableHead>
                <TableHead>Name</TableHead>
                <TableHead>Description</TableHead>
                <TableHead>Start Date</TableHead>
                <TableHead>Validity</TableHead>
                <TableHead>Chat</TableHead>
                <TableHead>Quit</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {myInvitations.map((chatroom) => (
                <TableRow key={chatroom.id}>
                  <TableCell className="font-medium">{chatroom.id}</TableCell>
                  <TableCell>{chatroom.title}</TableCell>
                  <TableCell>{chatroom.description}</TableCell>
                  <TableCell>{chatroom.startDate}</TableCell>
                  <TableCell>{chatroom.validityDuration}</TableCell>
                  <TableCell>
                    <Link to={`/chatroom/${chatroom.id}`}>
                      <Button variant="outline">Chat</Button>
                    </Link>
                  </TableCell>
                  <TableCell>
                    <Link to={`/quit/chatroom/${chatroom.id}`}>
                      <Button variant="outline">Quit</Button>
                    </Link>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
            <TableFooter>
              <TableRow>
                <TableCell colSpan={6}>
                  Want to Create a New Chatroom / Invitation ?
                </TableCell>
                <TableCell>
                  <Link to="/create/chatroom">
                    <Button variant="outline">
                      <CirclePlus />
                    </Button>
                  </Link>
                </TableCell>
              </TableRow>
            </TableFooter>
          </Table>
        </CardContent>
      </Card>
    </div>
  );
}

export default MyInvitations;
