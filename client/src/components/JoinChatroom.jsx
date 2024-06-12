import { useParams, useNavigate } from "react-router-dom";

import { useState, useEffect, useRef } from "react";

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
  const isUnmounting = useRef(false); // Add this line
  // The cleanup function (the function returned from within useEffect) is called when the component unmounts,
  // but also when the component re-renders.
  // In this case, when closing the dialog, the component re-renders, causing the cleanup function to run,
  // and the axios request to be made again.
  // to prevent this, we can use a ref isUnmounting to keep track of whether the component is unmounting.

  
  useEffect(() => {
    if (isUnmounting.current) {
      console.log("Joining room " + roomId);
      axios
        .put(
          "http://localhost:8080/api/chatrooms/" + roomId + "/users/" + user.id
        )
        .then((res) => {
          console.log("Response from backend : ", res);
        })
        .catch((error) => {
          console.error("Error from backend : ", error.response.data);
        });
    }
    return () => {
      isUnmounting.current = true;
    };
  }, []);

  const [open, setOpen] = useState(true);

  const navigate = useNavigate();

  useEffect(() => {
    if (!open) {
      navigate("/chatrooms");
    }
  }, [open, navigate]);

  return (
    <Dialog open={open} onOpenChange={setOpen}>
      <DialogContent>
        <DialogHeader>
          <DialogTitle>Joining Room</DialogTitle>
          <DialogDescription>You have joined room {roomId} !</DialogDescription>
        </DialogHeader>
      </DialogContent>
    </Dialog>
  );
}
