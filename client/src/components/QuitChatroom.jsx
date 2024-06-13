import { useMutation, useQueryClient } from "@tanstack/react-query";
import axios from "axios";
import { useParams, useNavigate } from "react-router-dom";
import { useEffect, useRef } from "react";

export default function QuitChatrooms({ user }) {
  const queryClient = useQueryClient();
  const navigate = useNavigate();
  const { roomId } = useParams();
  const isUnmounting = useRef(false);
  const mutation = useMutation({
    mutationFn: () => {
      return axios.delete(`/api/chatrooms/${roomId}/users/${user.id}`);
    },
    onMutate: () => {
      console.log("[QuitChatroom] onMutate");
    },
    onError: (error) => {
      console.log("[QuitChatroom] onError: ", error);
    },
    onSettled: (data, error) => {
      console.log("[QuitChatroom] onSettled: ", data, error);
      // invalidate the query to refetch the data
      queryClient.invalidateQueries([
        { queryKey: ["chatrooms"] },
        { queryKey: ["userChatrooms"] },
        { queryKey: ["users"] },
      ]);
    },
    onSuccess: (data) => {
      console.log("[QuitChatroom] onSuccess: ", data);
    },
  });

  useEffect(() => {
    if (isUnmounting.current) {
      mutation.mutate();
    }
    return () => {
      isUnmounting.current = true;
      navigate("/");
    };
  }, []);

  return null;
}
