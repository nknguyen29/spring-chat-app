import React, { useEffect, useState } from "react";

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

import { Button } from "@/components/ui/button";
import { Link } from "react-router-dom";

import { CirclePlus } from "lucide-react";

import { useGetAllChatrooms, useGetUserChatrooms } from "@/hooks/useChatroom";

import {
  Pagination,
  PaginationContent,
  PaginationEllipsis,
  PaginationItem,
  PaginationLink,
  PaginationNext,
  PaginationPrevious,
} from "@/components/ui/pagination";

function ChatroomList({ user }) {
  const {
    data: dataChatrooms,
    isLoading: chatroomsLoading,
    isError: chatroomsError,
  } = useGetAllChatrooms();

  const {
    data: dataUserChatrooms,
    isLoading: userChatroomsLoading,
    isError: userChatroomsError,
  } = useGetUserChatrooms(user);

  const [currentPage, setCurrentPage] = useState(1);
  const itemsPerPage = 7;

  const chatrooms = dataChatrooms ? dataChatrooms : [];

  const userChatrooms = dataUserChatrooms ? dataUserChatrooms.chatrooms : [];

  console.log("userChatrooms", userChatrooms, "et chatrooms", chatrooms);

  // Calculate pagination
  const indexOfLastItem = currentPage * itemsPerPage;
  const indexOfFirstItem = indexOfLastItem - itemsPerPage;
  const currentChatrooms = chatrooms.slice(
    indexOfFirstItem,
    indexOfLastItem
  );

  const totalPages = Math.ceil(chatrooms.length / itemsPerPage);

  return (
    <div>
      <Card className="w-full">
        <CardHeader className="px-7">
          <CardTitle>Available Chatrooms</CardTitle>
          <CardDescription>All Chatrooms from the database</CardDescription>
        </CardHeader>
        <CardContent>
          <Table>
            <TableCaption>Available Chatrooms</TableCaption>
            <TableHeader>
              <TableRow>
                <TableHead>Room ID</TableHead>
                <TableHead>Name</TableHead>
                <TableHead>Description</TableHead>
                <TableHead>Start Date</TableHead>
                <TableHead>Validity</TableHead>
                <TableHead>Action</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {currentChatrooms.map((chatroom) => (
                <TableRow key={chatroom.id}>
                  <TableCell className="font-medium">{chatroom.id}</TableCell>
                  <TableCell>{chatroom.title}</TableCell>
                  <TableCell>{chatroom.description}</TableCell>
                  <TableCell>{chatroom.startDate}</TableCell>
                  <TableCell>{chatroom.validityDuration}</TableCell>
                  <TableCell>
                    <Link
                      to={
                        userChatrooms &&
                        userChatrooms.some(
                          (userChatroom) => userChatroom.id === chatroom.id
                        )
                          ? `/chatroom/${chatroom.id}`
                          : `/join/chatroom/${chatroom.id}`
                      }
                    >
                      <Button variant="outline">
                        {userChatrooms &&
                        userChatrooms.some(
                          (userChatroom) => userChatroom.id === chatroom.id
                        )
                          ? "Chat"
                          : "Join"}
                      </Button>
                    </Link>
                  </TableCell>
                </TableRow>
              ))}
            </TableBody>
            <TableFooter>
              <TableRow>
                <TableCell colSpan={5}>
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
          <Pagination>
            <PaginationContent>
              <PaginationItem>
                <PaginationPrevious
                  href="#"
                  onClick={(e) => {
                    e.preventDefault();
                    if (currentPage > 1) setCurrentPage(currentPage - 1);
                  }}
                />
              </PaginationItem>
              {Array.from({ length: totalPages }, (_, i) => (
                <PaginationItem key={i}>
                  <PaginationLink
                    href="#"
                    isActive={currentPage === i + 1}
                    onClick={(e) => {
                      e.preventDefault();
                      setCurrentPage(i + 1);
                    }}
                  >
                    {i + 1}
                  </PaginationLink>
                </PaginationItem>
              ))}
              <PaginationItem>
                <PaginationNext
                  href="#"
                  onClick={(e) => {
                    e.preventDefault();
                    if (currentPage < totalPages)
                      setCurrentPage(currentPage + 1);
                  }}
                />
              </PaginationItem>
            </PaginationContent>
          </Pagination>
        </CardContent>
      </Card>
    </div>
  );
}

export default ChatroomList;
