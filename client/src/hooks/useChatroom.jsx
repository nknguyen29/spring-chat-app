import { useQuery, useMutation } from "@tanstack/react-query";
import * as api from "@/api/chatrooms";

// mutation are used directly with useMutation,
// and are not implemented as custom hooks.
// in this file are all the query potentially invalidated by the mutations

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

export const useGetSpecificChatroom = (roomId) => {
  return useQuery({
    queryKey: ["specific-chatrooms"],
    queryFn: () => api.getSpecificChatroom(roomId),
    enabled: !!roomId,
  });
};