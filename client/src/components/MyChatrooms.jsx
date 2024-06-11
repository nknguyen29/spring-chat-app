import React from 'react';

import useGetChatrooms from '../hooks/useGetChatrooms';

import {
    Card,
    CardContent,
    CardDescription,
    CardHeader,
    CardTitle,
  } from "./ui/card"

import {
    Table,
    TableBody,
    TableCaption,
    TableCell,
    TableFooter,
    TableHead,
    TableHeader,
    TableRow,
  } from "@/components/ui/table"

function MyChatrooms({ user }) {
    const { chatrooms, error } = useGetChatrooms(user.id);

    if (!Array.isArray(chatrooms)) {
        console.error('Hello, expected an array from the API endpoint, but did not receive one.');
        return null;
    }

    return (
        <>
        <Card>
            <CardHeader className="px-7">
                <CardTitle>My Chatrooms' Details</CardTitle>
                <CardDescription>More Informations about My Chatrooms</CardDescription>
                </CardHeader>
            <CardContent>
            <Table>
                <TableCaption>Available Chatrooms</TableCaption>
                <TableHeader>
                    <TableRow>
                        <TableHead className="w-[100px]">Room ID</TableHead>
                        <TableHead>Name</TableHead>
                        <TableHead>Description</TableHead>
                        <TableHead>Created At</TableHead>
                    </TableRow>
                </TableHeader>
                <TableBody>
                    {chatrooms.map((chatroom) => (
                        <TableRow key={chatroom.id}>
                            <TableCell className="font-medium">{chatroom.id}</TableCell>
                            <TableCell>{chatroom.title}</TableCell>
                            <TableCell>{chatroom.description}</TableCell>
                            <TableCell>{chatroom.createdAt}</TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
            </CardContent>
        </Card>
        </>
    );
}

export default MyChatrooms;