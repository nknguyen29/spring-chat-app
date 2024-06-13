import { useMutation, useQueryClient } from "@tanstack/react-query";
import axios from "axios";
import { useParams, useNavigate } from "react-router-dom";
import { useEffect, useRef } from "react";

export default function JoinChatrooms({ user }) {
  const queryClient = useQueryClient();
  const navigate = useNavigate();
  const { roomId } = useParams();
  const isUnmounting = useRef(false);
  const mutation = useMutation({
    mutationFn: () => {
      return axios.put(`/api/chatrooms/${roomId}/users/${user.id}`);
    },
    onMutate: () => {
      console.log("[JoinChatroom] onMutate");
    },
    onError: (error) => {
      console.log("[JoinChatroom] onError: ", error);
    },
    onSettled: (data, error) => {
      console.log("[JoinChatroom] onSettled: ", data, error);
      // invalidate the query to refetch the data
      queryClient.invalidateQueries([
        { queryKey: ["chatrooms"] },
        { queryKey: ["userChatrooms"] },
      ]);
    },
    onSuccess: (data) => {
      console.log("[JoinChatroom] onSuccess: ", data);
    },
  });

  useEffect(() => {
    if (isUnmounting.current) {
      mutation.mutate();
    }
    return () => {
      isUnmounting.current = true;
      navigate("/chatrooms");
    };
  }, []);

  return null;
}
