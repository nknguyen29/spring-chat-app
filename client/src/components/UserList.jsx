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

import { useGetAllUsers } from "@/hooks/useChatroom";

function UserList() {
  const { data: users, isLoading, isError } = useGetAllUsers();
  if (isLoading) {
    return <div>Loading...</div>;
  }
  if (isError) {
    return <div>Error loading chatrooms</div>;
  }

  if (!Array.isArray(users)) {
    console.error(
      "Hello, expected an array from the useGetAllUsers hook, but did not receive one."
    );
    return null;
  }

  return (
    <>
      <Card>
        <CardHeader className="px-7">
          <CardTitle>User List</CardTitle>
          <CardDescription>All Users from the database</CardDescription>
        </CardHeader>
        <CardContent>
          <Table>
            <TableCaption>User List</TableCaption>
            <TableHeader>
              <TableRow>
                <TableHead className="w-[100px]">User ID</TableHead>
                <TableHead>First Name</TableHead>
                <TableHead>Last Name</TableHead>
                <TableHead>Email</TableHead>
              </TableRow>
            </TableHeader>
            <TableBody>
              {users.map((user) => (
                <TableRow key={user.id}>
                  <TableCell className="font-medium">{user.id}</TableCell>
                  <TableCell>{user.firstName}</TableCell>
                  <TableCell>{user.lastName}</TableCell>
                  <TableCell>{user.email}</TableCell>
                </TableRow>
              ))}
            </TableBody>
          </Table>
        </CardContent>
      </Card>
    </>
  );
}

export default UserList;
