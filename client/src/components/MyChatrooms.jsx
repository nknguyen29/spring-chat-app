import React, { useState } from "react";

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

import { useGetUserChatrooms } from "@/hooks/useChatroom";

import {
  Pagination,
  PaginationContent,
  PaginationEllipsis,
  PaginationItem,
  PaginationLink,
  PaginationNext,
  PaginationPrevious,
} from "@/components/ui/pagination"

function MyChatrooms({ user }) {
  const {
    data: dataUserChatrooms,
    isLoading,
    isError,
  } = useGetUserChatrooms(user);

  const [currentPage, setCurrentPage] = useState(1);
  const itemsPerPage = 5;

  if (isLoading) {
    return <div>Loading...</div>;
  }

  if (isError) {
    return <div>Error loading chatrooms</div>;
  }

  if (!dataUserChatrooms) {
    return <div>No Chatrooms Found</div>;
  }

  // Filter the chatrooms to only include those created by the user
  const userChatrooms = dataUserChatrooms.chatrooms.filter(
    (chatroom) => chatroom.createdBy.id === user.id
  );

  // Calculate pagination
  const indexOfLastItem = currentPage * itemsPerPage;
  const indexOfFirstItem = indexOfLastItem - itemsPerPage;
  const currentChatrooms = userChatrooms.slice(
    indexOfFirstItem,
    indexOfLastItem
  );

  const totalPages = Math.ceil(userChatrooms.length / itemsPerPage);

  return (
    <div>
      <Card className="w-full">
        <CardHeader className="px-7">
          <CardTitle>My Chatrooms</CardTitle>
          <CardDescription>
            More Informations about My Chatrooms
          </CardDescription>
        </CardHeader>
        <CardContent>
          <Table>
            <TableCaption>My Chatrooms</TableCaption>
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
              {currentChatrooms.map((chatroom) => (
                <TableRow key={chatroom.id}>
                  <TableCell className="font-medium">{chatroom.id}</TableCell>
                  <TableCell>{chatroom.title}</TableCell>
                  <TableCell>{chatroom.description}</TableCell>
                  <TableCell>{new Date(chatroom.startDate).toLocaleString()}</TableCell>
                  <TableCell>{new Date(chatroom.validityDuration).toLocaleString()}</TableCell>
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

export default MyChatrooms;
