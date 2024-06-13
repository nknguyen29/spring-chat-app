import { useQuery, useMutation } from "@tanstack/react-query";
import * as api from "@/api/chatrooms";

export const useJoinChatroom = () => {
  return useMutation({
    mutationFn: api.joinChatroom,
    onMutate: ({ userId, roomId }) => {
      console.log("[useJoinChatroom] onMutate: ", userId, roomId);
    },
    onError: (error, variables, context) => {
      console.log("[useJoinChatroom] onError: ", error, variables, context);
    },
    onSettled: (data, error, variables, context) => {
      console.log(
        "[useJoinChatroom] onSettled: ",
        data,
        error,
        variables,
        context
      );
    },
  });
};

export const useLeaveChatroom = (userId, roomId) => {
  return useMutation(api.leaveChatroom(userId, roomId));
};

export const useGetAllChatrooms = () => {
  return useQuery({
    queryKey: ["chatrooms"],
    queryFn: api.getAllChatrooms,
  });
};

export const useGetUserChatrooms = (user) => {
  return useQuery({
    queryKey: ["userChatrooms"],
    queryFn: () => api.getUserChatrooms(user.id),
    enabled: !!user,
  });
};

export const useGetAllUsers = () => {
  return useQuery({
    queryKey: ["users"],
    queryFn: api.getAllUsers,
  });
};
