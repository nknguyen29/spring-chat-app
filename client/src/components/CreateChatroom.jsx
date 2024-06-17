import React, { useState, useEffect } from "react";
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";

import { Button } from "@/components/ui/button";
import { Label } from "@/components/ui/label";
import { Input } from "@/components/ui/input";

import { useGetAllUsers } from "@/hooks/useChatroom";

import { useMutation, useQueryClient } from "@tanstack/react-query";
import axios from "axios";
import { toast } from "sonner";

const SimpleForm = ({ user }) => {
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [startDate, setStartDate] = useState(new Date(Date.now()));
  const [validityDuration, setValidityDate] = useState(
    new Date(Date.now() + 7 * 24 * 60 * 60 * 1000)
    // 7 days from now expiration date by default
  );
  const [selectedUsers, setSelectedUsers] = useState([]);

  const queryClient = useQueryClient(); // used to invalidate the query

  const mutation = useMutation({
    // used to send the data to the server
    mutationFn: (payload) => {
      return axios.post(`/api/chatrooms`, payload, {
        headers: {
          Authorization: `Bearer ${sessionStorage.getItem("token")}`,
        },
      });
    },
    onError: (error) => {
      console.log("[CreateChatrooms] onError: ", error);
    },
    onSettled: (data, error) => {
      console.log("[CreateChatrooms] onSettled: ", data, error);
      // invalidate the query to refetch the data
      queryClient.invalidateQueries([
        { queryKey: ["chatrooms"] },
        { queryKey: ["userChatrooms"] },
        { queryKey: ["users"] },
      ]);
    },
  });

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

  const handleSelectionChange = (e) => {
    const options = e.target.options;
    const selectedValues = [];
    for (let i = 0; i < options.length; i++) {
      if (options[i].selected) {
        selectedValues.push(options[i].value);
      }
    }
    setSelectedUsers(selectedValues);
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    if (
      !title ||
      !description ||
      !startDate ||
      !validityDuration ||
      selectedUsers.length === 0
    ) {
      alert("Please fill in all fields and select at least one user.");
      return;
    }
    const chatroom = {
      title: title,
      description: description,
      startDate: startDate.toISOString(),
      validityDuration: validityDuration.toISOString(),
      createdById: user.id,
    };
    const userIds = selectedUsers.map(Number); // convert to numbers

    const newChatroomPayload = {
      chatroom,
      userIds,
    };

    console.log(
      "[CreateChatrooms] Sending this to the server : ",
      newChatroomPayload
    );

    mutation.mutate(newChatroomPayload);

    // Sonner to say that room has been created
    toast("Chatroom has been created !", {
      description: "Let's start discussing !",
      action: {
        label: "Dismiss",
        onClick: () => console.log("Dismissed toast"),
      },
    });
  };

  return (
    <form onSubmit={handleSubmit} className="p-6 rounded-md shadow-lg">
      <h1 className="text-2xl font-bold mb-4">Plan a discussion</h1>
      <div className="mb-4">
        <Label>Title</Label>
        <Input
          type="text"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          required
        />
      </div>
      <div className="mb-4">
        <Label>Description</Label>
        <Input
          type="text"
          value={description}
          onChange={(e) => setDescription(e.target.value)}
          required
        />
      </div>
      <div className="flex justify-between">
        <div className="mb-4 pr-2">
          <Label>Start Date</Label>
          <DatePicker
            className="w-full p-2 border rounded-md"
            selected={startDate}
            onChange={(startDate) => setStartDate(startDate)}
            showTimeSelect
            dateFormat="Pp"
            required
          />
        </div>
        <div className="mb-4 pl-2">
          <Label>Validity Duration</Label>
          <DatePicker
            className="w-full p-2 border rounded-md"
            selected={validityDuration}
            onChange={(validityDuration) => setValidityDate(validityDuration)}
            showTimeSelect
            dateFormat="Pp"
            required
          />
        </div>
      </div>
      <div className="mb-4">
        <Label>Invite Users - Select multiple users by holding Ctrl</Label>
        <select
          multiple
          value={selectedUsers}
          onChange={handleSelectionChange}
          className="w-full p-2 border rounded-md"
          style={{ height: "150px" }} // Added height
          required
        >
          {users.map((user) => (
            <option key={user.id} value={user.id}>
              {user.firstName} {user.lastName} : {user.email}
            </option>
          ))}
        </select>
      </div>
      <Button type="submit">Submit</Button>
    </form>
  );
};

export default SimpleForm;
