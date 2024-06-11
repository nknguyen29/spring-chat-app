import React, { useState } from "react";
import { useNavigate, useParams } from 'react-router-dom';
import axios from "axios";

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


export default function App({ user }) {
  const { roomId } = useParams();
  
  const handleJoin = (event) => {
    // event.preventDefault();
    console.log("Joining room " + roomId);

    // Requete HTTP login au backend spring
    axios.put("http://localhost:8080/api/chatrooms/" + roomId + "/users/" + user.id)
        .then(res => {
            console.log("Response from backend : ", res)
        })
        .catch(error => {
            console.error("Error from backend : ", error)
        });
  }

  // const [open, setOpen] = useState(true);

  // return (
  //   <Dialog open={open} onOpenChange={setOpen}>
  //     <DialogContent>
  //       <DialogHeader>
  //         <DialogTitle>Joining Room</DialogTitle>
  //         <DialogDescription>Join a new room</DialogDescription>
  //       </DialogHeader>
  //       <JoiningRoom />
  //     </DialogContent>
  //   </Dialog>
  // );
  handleJoin();
  return null;
}

// export function JoiningRoom() {
//   const [roomId, setRoomId] = useState("");

//   const navigate = useNavigate();

//   const joinRoom = () => {
//     navigate('/chatroom/' + roomId);
//   };

//   return (
//     <div className="flex w-full max-w-sm items-center space-x-2">
//       <Label>RoomID</Label>
//       <Input type="default" value={roomId} onChange={(event => setRoomId(event.target.value))} />
//       <Button variant="outline" onClick={joinRoom}>Join Room </Button> 
//     </div>
//   );
// }