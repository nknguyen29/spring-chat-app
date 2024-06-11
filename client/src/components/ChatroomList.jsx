import React, { useEffect, useState } from 'react';
import axios from 'axios';

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

function ChatroomList() {
    const [chatrooms, setChatrooms] = useState([]);
    const [error, setError] = useState(null);

    useEffect(() => {
        axios.get('/api/chatrooms/public')
            .then(response => {
                if (Array.isArray(response.data)) {
                    setChatrooms(response.data);
                } else {
                    setChatrooms([]);
                    console.error('Hello, expected an array from the API endpoint, but did not receive one.');
                }
            })
            .catch(error => {
                setError(error.message);
            });
    }, []);

    return (
        <>
        <Card>
            <CardHeader className="px-7">
                <CardTitle>Available Chatrooms</CardTitle>
                <CardDescription>All Chatrooms from the database</CardDescription>
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

export default ChatroomList;