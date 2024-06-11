import React, { useState } from "react";
import { useNavigate } from 'react-router-dom';

import { Button } from "./ui/button";
import { Input } from "./ui/input";
import { Label } from "./ui/label";


import {
  Dialog,
  DialogContent,
  DialogDescription,
  // DialogFooter,
  DialogHeader,
  DialogTitle,
  // DialogTrigger,
} from "./ui/dialog"


export default function App() {
  const [open, setOpen] = useState(true);
  return (
    <Dialog open={open} onOpenChange={setOpen}>
      <DialogContent>
        <DialogHeader>
          <DialogTitle>Joining Room</DialogTitle>
          <DialogDescription>Join a new room</DialogDescription>
        </DialogHeader>
        <JoiningRoom />
      </DialogContent>
    </Dialog>
  );
}

export function JoiningRoom() {
  // take roomId from the URL of React Router
  const [roomId, setRoomId] = useState("");

//   const stompClient = useStompClient();
  const navigate = useNavigate();

  const joinRoom = () => {
    navigate('/discussion/' + roomId);
  };

  return (
    <div className="flex w-full max-w-sm items-center space-x-2">
      <Label>RoomID</Label>
      <Input type="default" value={roomId} onChange={(event => setRoomId(event.target.value))} />
      <Button variant="outline" onClick={joinRoom}>Join Room </Button> 
    </div>
  );
}