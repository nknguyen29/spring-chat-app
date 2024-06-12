import { useParams, useNavigate } from "react-router-dom";

import { useState, useEffect } from "react";

import axios from "axios";

import {
  Dialog,
  DialogContent,
  DialogDescription,
  // DialogFooter,
  DialogHeader,
  DialogTitle,
  // DialogTrigger,
} from "@/components/ui/dialog";

export default function App({ user, setUserChatrooms }) {
  const { roomId } = useParams();

  useEffect(() => {
    console.log("Joining room " + roomId);
    axios
      .delete(
        "http://localhost:8080/api/chatrooms/" + roomId + "/users/" + user.id
      )
      .then((res) => {
        console.log("Response from backend : ", res);
      })
      .catch((error) => {
        console.error("Error from backend : ", error.response.data);
      });
  }, [roomId, user.id]);

  const [open, setOpen] = useState(true);

  const navigate = useNavigate();

  useEffect(() => {
    if (!open) {
      navigate("/my-chatrooms");
    }
  }, [open, navigate]);

  return (
    <Dialog open={open} onOpenChange={setOpen}>
      <DialogContent>
        <DialogHeader>
          <DialogTitle>Quiting Room</DialogTitle>
          <DialogDescription>You have quitted room {roomId} !</DialogDescription>
        </DialogHeader>
      </DialogContent>
    </Dialog>
  );
}
